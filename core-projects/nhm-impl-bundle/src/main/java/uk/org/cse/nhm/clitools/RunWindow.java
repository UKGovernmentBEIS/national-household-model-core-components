package uk.org.cse.nhm.clitools;

import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Level;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;

import uk.org.cse.nhm.clitools.bundle.NationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.*;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import java.nio.file.Path;
import java.io.IOException;

public class RunWindow extends JFrame implements Runnable {
    final Path scenario, output;
    Thread executionThread = null;
    final JTextArea log;
    
    public RunWindow(final Path scenario) {
        super("NHM RUN [" + scenario + "]");

        this.scenario = scenario;
        this.output = scenario.toAbsolutePath().getParent().resolve(String.format("%s-%s-output.zip",
                                                                                  scenario.getFileName(),
                                                                                  new SimpleDateFormat("yyyy-MM-dd HH-mm").format(new Date())));

        final JLabel label = new JLabel(String.format("Running %s, output %s", scenario.getFileName(), output.getFileName()));
        add(label, BorderLayout.PAGE_START);
        log = new JTextArea(5, 20);
        add(new JScrollPane(log), BorderLayout.CENTER);
        log.setEditable(false);
        //redirect thread's log to the log somehow
        
        pack();

        executionThread = new Thread(this);
        executionThread.start();
    }

    protected void appendToLogBuffer(final String msg) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    log.append(msg + "\n");
                }
            });
    }

    @Override
    public void run() {
        try {
            appendToLogBuffer("Loading scenario");
            final NationalHouseholdModel model = NationalHouseholdModel.create();

            model.simulate(PathFS.INSTANCE, scenario,
                           new ISimulationCallback<Path>() {
                               @Override
                               public void log(final String source, final String message) {
                                   appendToLogBuffer(String.format("[%s] %s", source, message));
                               }

                               @Override
                               public void progress(final double proportion) {
                                   appendToLogBuffer(String.format("PROGRESS %f", proportion));
                               }
                
                               @Override
                               public void completed(final Path pathToResult) {
                                   appendToLogBuffer("COMPLETE");
                                   // move file to right place
                                   try {
                                       Files.move(pathToResult, output);
                                   } catch (final IOException ex) {
                                       throw new RuntimeException(ex.getMessage(), ex);
                                   }
                               }

                               @Override
                               public void invalid(final IValidationResult<Path> validation) {
                                   for (final IValidationProblem<Path> problem : validation.problems()) {
                                       log("VALIDATION-" + String.valueOf(problem.level()).toUpperCase(),
                                           problem.message());
                                   }
                                   appendToLogBuffer("INVALID");
                               }
                               @Override
                               public void failed() {
                                   appendToLogBuffer("FAILED");
                               }
                
                               @Override
                               public void cancelled() {
                                   appendToLogBuffer("CANCELLED");
                               }
                
                               @Override
                               public boolean shouldCancel() {
                                   return false;
                               }
                           });
            
            appendToLogBuffer("Scenario run finished");
            // print done status, flash icons and so on
            // could add a tray icon for the NHM I guess
        } catch (final Exception ex) {
            // print error in a useful way
            appendToLogBuffer("Error during run!");
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);

            ex.printStackTrace(pw);
            
            appendToLogBuffer(sw.toString());
        }
    }
}


