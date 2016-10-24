package uk.org.cse.nhm.clitools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;

import org.fife.ui.rsyntaxtextarea.FileLocation;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;
import org.fife.ui.rtextarea.RTextScrollPane;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;

import com.google.common.base.CharMatcher;
import com.larkery.jasb.sexp.BetterPrinter;

public class GUI extends JFrame {
    public static void open(final Path p) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    final GUI g = new GUI(p);
                    g.setVisible(true);
                }
            });
    }
    
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    final JFileChooser fc = new JFileChooser();
                    if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
                        final File top = fc.getSelectedFile();
                        final GUI g = new GUI(top.toPath());
                        g.setVisible(true);
                    }
                }
            });
    }

    protected final Path mainScenario;
    
    public GUI(final Path file) {
        super("NHM [" + file + "]");

        this.mainScenario = file;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addControls();
        
        pack();

        //
        //w.show();
    }

    static JMenuItem menuItem(final String name,
                              int keyMask,
                              final ActionListener l
                              ) {
        final JMenuItem nm = new JMenuItem(name, keyMask);
        nm.setActionCommand(CharMatcher.JAVA_LETTER.retainFrom(name).toLowerCase());
        nm.setAccelerator(KeyStroke.getKeyStroke(keyMask, Event.CTRL_MASK));
        nm.addActionListener(l);
        return nm;
    }
    
    private void addControls() {
        final JMenuBar menu = new JMenuBar();
        final JMenu file = new JMenu("File");
        final JMenu nhm = new JMenu("Model");

        final EditorPane editor = new EditorPane(this.mainScenario);

        final ValidationLoop m = new ValidationLoop();
        m.setScenario(this.mainScenario);

        final JTable table = new JTable(m);
        
        final ListSelectionListener listListener = new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                final int[] rows = table.getSelectedRows();
                if (rows.length > 0) {
                    final int selection = table.convertRowIndexToModel(rows[0]);
                    final String fn = String.valueOf(m.getValueAt(selection, 0));
                    final String ln = String.valueOf(m.getValueAt(selection, 1));
                    editor.go(fn, ln);
                }
            }
        };
        
        final ActionListener l = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    switch (e.getActionCommand()) {
                    case "new":
                        editor.newFile();
                        break;
                    case "open":
                        editor.open();
                        break;
                    case "save":
                        editor.save();
                        break;
                    case "close":
                        editor.close();
                        break;
                    case "quit":
                        GUI.this.dispatchEvent(new WindowEvent(GUI.this, WindowEvent.WINDOW_CLOSING));
                        return;
                    case "run":
                        new RunWindow(GUI.this.mainScenario).show();
                        break;
                    case "expand":
                        new ExpandWindow(GUI.this.mainScenario).show();
                        break;
                    default:
                        System.out.println("Unknown command " + e.getActionCommand());
                    }
                }
            };

        menu.add(file);
        menu.add(nhm);

        file.add(menuItem("New", KeyEvent.VK_N, l));
        file.add(menuItem("Open", KeyEvent.VK_O, l));
        file.add(menuItem("Save", KeyEvent.VK_S, l));
        file.add(menuItem("Close", KeyEvent.VK_W, l));
        file.add(menuItem("Quit", KeyEvent.VK_Q, l));
        nhm.add(menuItem("Run", KeyEvent.VK_R, l));
        nhm.add(menuItem("Expand", KeyEvent.VK_E, l));
        
        setJMenuBar(menu);
        
        setLayout(new BorderLayout(0, 0));


        table.setRowSorter(new TableRowSorter<>(m));
        final JScrollPane scroll = new JScrollPane(table);
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(listListener);
        
        final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                                editor, scroll);
                
        add(split, BorderLayout.CENTER);

        // TODO add expander
        // TODO add run button
        // TODO could the error locations be useful somehow; error locations mod N with a control for N
    }

    static class EditorPane extends JTabbedPane implements DocumentListener {
        private final Path scenario;
        
        public EditorPane(final Path scenario) {
            this.scenario = scenario;

            try {
                addTab(this.scenario.getFileName().toString(),
                       editor(this.scenario));
            } catch (IOException ex) {}

            setPreferredSize(new Dimension(1000, 600));
        }

        private NHMEditor editor(final Path path) throws IOException {
            final NHMEditor e = new NHMEditor(path);
            e.getDocument().addDocumentListener(this);
            return e;
        }

        public void insertUpdate(DocumentEvent e) {for (int i = 0; i<getTabCount(); i++) retitle(i);}
        public void removeUpdate(DocumentEvent e) {for (int i = 0; i<getTabCount(); i++) retitle(i);}
        public void changedUpdate(DocumentEvent e) {}
                
        public void save() {
            final Component c = this.getSelectedComponent();
            if (c instanceof NHMEditor) {
                ((NHMEditor) c).save();
                retitle(getSelectedIndex());
            }
        }

        public void retitle(final int index) {
            final Component c = this.getComponentAt(index);
            if (c instanceof NHMEditor) {
                final NHMEditor e = (NHMEditor) c;
                final String title = (e.isDirty() ? " [*]" : "") + e.getFile().getFileName();
                setTitleAt(index, title);
            }
        }

        public void newFile() {
            try {
                addTab("new file",
                       new NHMEditor(this.scenario.getParent().resolve("new file")));
                setSelectedIndex(getTabCount() - 1);
            } catch (final IOException e) {}
        }

        public void go(final String file, final String line) {
            final Path path = scenario.toAbsolutePath().getParent().resolve(file);
            for (int i = 0; i<getTabCount(); i++) {
                final Component c = getComponentAt(i);
                if (c instanceof NHMEditor) {
                    if (((NHMEditor) c).getFile().equals(path)) {
                        setSelectedIndex(i);
                        ((NHMEditor) c).gotoLine(Integer.parseInt(line));
                    }
                }
            }
        }
        
        public void open() {
            final JFileChooser fc = new JFileChooser(scenario.toAbsolutePath().getParent().toFile());
            if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
                final Path subFile = fc.getSelectedFile().toPath();
                try {
                    addTab(subFile.getFileName().toString(),
                           editor(subFile));
                    setSelectedIndex(getTabCount() - 1);
                } catch (final IOException ex) {}
            }
        }

        public void close() {
            if (getSelectedIndex() > 0) {
                final Component c = getComponentAt(getSelectedIndex());
                if (c instanceof NHMEditor) {
                    final NHMEditor n = (NHMEditor) c;
                    if (n.isDirty()) {
                        final String[] options = { "Save changes", "Discard changes", "Cancel"};
                        final int rc =
                            JOptionPane.showOptionDialog(null,
                                                         "Save changes to " + n.getFile().getFileName() +"?",
                                                         "Save changes to "+ n.getFile().getFileName() +"?",
                                                         JOptionPane.WARNING_MESSAGE,
                                                         0, null,
                                                         options,
                                                         options[0]);
                        switch (rc) {
                        case 0:
                            n.save();
                            break;
                        case 1:
                            break;
                        default:
                            return;
                        }
                    }
                }
                removeTabAt(getSelectedIndex());
            }
        }
    }

    static class NHMEditor extends RTextScrollPane {
        private final TextEditorPane pane;
        
        public NHMEditor(final Path scenario) throws IOException {
            super(new TextEditorPane(TextEditorPane.INSERT_MODE,
                                     true,
                                     FileLocation.create(scenario.toFile()),
                                     "UTF8"));
            
            pane = (TextEditorPane) getTextArea();
            pane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
            pane.setAntiAliasingEnabled(true);
            pane.setAutoIndentEnabled(true);
            pane.setCodeFoldingEnabled(true);
            setFoldIndicatorEnabled(true);
            pane.setEncoding("UTF8");
        }

        public Document getDocument() { return pane.getDocument(); }
        
        public void gotoLine(final int line) {
            final String textContent = pane.getText();
            int currentLine = 1;
            int currentSelection = 0;
            final String seperator = "\n";
            int seperatorLength = 1;
            while (currentLine < line) {
                int next = textContent.indexOf(seperator,currentSelection);
                if (next > -1) {
                    currentSelection = next + seperatorLength;
                    currentLine++;
                } else {
                    // set to the end of doc
                    currentSelection = textContent.length();
                    currentLine= line; // exits loop
                }
            }
            
            pane.setCaretPosition(currentSelection);
        }

        public boolean isDirty() {
            return pane.isDirty();
        }
        
        public Path getFile() {
            return Paths.get(pane.getFileFullPath());
        }

        public void save() {
            if (pane.isLocalAndExists()) {
                try {pane.save();} catch (final IOException ex) {}
            } else if (pane.isLocal()) {
                // adjust file location
                final JFileChooser fc = new JFileChooser(new File(pane.getFileFullPath()).getParent());
                if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)) {
                    final File top = fc.getSelectedFile();
                    try {
                        pane.saveAs(FileLocation.create(top)); // TODO rename the tab containing this editor
                    } catch (final IOException ex) {
                        
                    }
                }                
            }
        }
    }

    static class ExpandWindow extends JFrame {
        public ExpandWindow(final Path scenario) {
            super("Expanded form of " + scenario);

            final RSyntaxTextArea box = new RSyntaxTextArea(20, 60);
            box.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
            box.setCodeFoldingEnabled(true);
            box.setEditable(false);
            
            final RTextScrollPane sp = new RTextScrollPane(box);
            sp.setFoldIndicatorEnabled(true);
            add(sp, BorderLayout.CENTER);
            
            pack();

            final Thread go = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Path path = scenario;
                        final Path base = path.toAbsolutePath().getParent();
                        final IScenarioSnapshot snapshot = Util.loadSnapshot(path, base);
                        final String expandedText = BetterPrinter.print(snapshot, 1);
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                @Override public void run() {
                                    box.setText(expandedText);
                                }
                            });
                    }
                });
            go.start();
        }
    }
}


