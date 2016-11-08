package uk.org.cse.nhm.clitools;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.JasbErrorException;


public class ValidationLoop extends AbstractTableModel implements Runnable {
    private int generation = 0;
    private final Thread myThread;
    private Path scenario;
    private String[][] data = new String[0][0];
    
    public ValidationLoop() {
        myThread = new Thread(this);
        myThread.start();
    }

    public void setScenario(final Path scenario) {
        this.scenario = scenario;
        trigger();
    }

    private void trigger() {
        generation++;
    }

    private void send(final String[][] outputs) {
        this.data = outputs;
        fireTableDataChanged();
    }

    public int getRowCount() {
        return this.data.length;
    }

    public int getColumnCount() {
        return 4;
    }
    
    public String getColumnName(final int i) {
        switch (i) {
        case 0:return "File";
        case 1:return "Line";
        case 2:return "Type";
        case 3:return "Message";
        default: return "";
        }
    }

    public Object getValueAt(final int row, final int col) {
        try {
            return this.data[row][col];
        } catch (Throwable th) {
            return "";
        }
    }

    @Override
    public void run() {
        final Validator validator = Validator.create();

        String[][] outputs = new String[0][0];
        String[][] lastOutputs = outputs;
        int runGeneration = 0;
        final WatchService watcher;
        
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (final IOException ie) {
            throw new RuntimeException(ie);
        }
        
        final Map<Path, WatchKey> watches = new HashMap<>();
        final Map<WatchKey, Path> watches_ = new HashMap<>();
        final Set<Path> filesOfInterest = new HashSet<>();
        
        while (true) {
            try {
                if (runGeneration < generation) {
                    System.out.println("Validating " + scenario);
                    runGeneration = generation;

                    // run validation here

                    try {
                        final Path path = scenario;
                        final Path base = path.toAbsolutePath().getParent();
                        final IScenarioSnapshot snapshot = Util.loadSnapshot(path, base);

                        final Set<Path> filePaths = new HashSet<>();
                        filesOfInterest.clear();
                        for (final String s : snapshot.contents().getComponents().keySet()) {
                            filesOfInterest.add(Paths.get(s).toAbsolutePath());
                            filePaths.add(Paths.get(s).toAbsolutePath().getParent());
                        }

                        for (final Path p : ImmutableSet.copyOf(Sets.difference(watches.keySet(), filePaths))) {
                            System.out.println("Unwatching " + p);
                            final WatchKey key = watches.get(p);
                            watches_.remove(key);
                            watches.remove(p);
                            key.cancel();
                        }

                        for (final Path p : ImmutableSet.copyOf(Sets.difference(filePaths, watches.keySet()))) {
                            final WatchKey key = p.register(watcher,
                                                            StandardWatchEventKinds.ENTRY_MODIFY,
                                                            StandardWatchEventKinds.ENTRY_CREATE,
                                                            StandardWatchEventKinds.ENTRY_DELETE);
                            System.out.println("Watching " + p);
                            watches.put(p, key);
                            watches_.put(key, p);
                        }
                        
                        // at this point, we can setup some watches
                        
                        final List<? extends IError> problems = validator.validate(snapshot);
                        outputs = new String[problems.size()][];

                        int i = 0;
                        for (final IError p : problems) {
                            String errpath = "unknown";
                            try {
                                final Path probPath = Paths.get(p.getLocation().sourceLocation.name);
                                final Path relPath = base.relativize(probPath);
                                errpath = String.valueOf(relPath);
                            } catch (Exception e) {
                            }
                            final String[] row = new String [] {
                                errpath,
                                String.valueOf(p.getLocation().sourceLocation.line),
                                String.valueOf(p.getType()),
                                p.getMessage()
                            };
                            outputs[i++]=row;
                        }
                    } catch (final JasbErrorException jee) {
                        outputs = new String[jee.getErrors().size()][];
                        int i = 0;
                        for (final IError e : jee.getErrors()) {
                            outputs[i++] = new String [] {
                                "?",
                                "?",
                                "Error",
                                e.getMessage()
                            };
                        }
                    }
                }
                
                if (runGeneration == generation) {
                    if (lastOutputs != outputs) {
                        send(outputs);
                        lastOutputs = outputs;
                    }
                    final WatchKey key = watcher.poll(10, TimeUnit.SECONDS);
                    if (key != null) {
                        final List<WatchEvent<?>> events = key.pollEvents();
                        for (final WatchEvent<?> e : events) {
                            final Path d = watches_.get(key);
                            final Path p = (Path) e.context();
                            System.out.println("Event from " + p);
                            if (filesOfInterest.contains(d.resolve(p))) {
                                generation++;
                                while (watcher.poll() != null);
                            }
                        }
                        key.reset();
                    }
                }
            } catch (final InterruptedException ie) {
            } catch (final Exception e) {
                e.printStackTrace();
                generation++; // try again?
            }
        }
    }
}

