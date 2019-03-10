package jips;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JDialog {

    private JTabbedPane tabbedPane1;

    private JPanel panelPatching;
    private JPanel panelFileToPatch;
    protected JComboBox cmbFile;
    protected JButton btnBrowseFile;

    private JPanel panelPatchingFile;
    protected JComboBox cmbPatch;
    protected JButton btnBrowsePatch;

    private JPanel panelOptions;
    protected JCheckBox chkBackup;

    private JPanel panelButtons;
    protected JButton btnPatch;
    protected JButton btnCancel;

    public View() {
        initComponents();
    }

    private void initComponents() {

        setTitle("JIPS 4.0");

        final Image image = getToolkit().getImage(getClass().getResource("/jips.png"));
        setIconImage(image);

        this.tabbedPane1 = new JTabbedPane();
        this.panelPatching = new JPanel();
        this.panelFileToPatch = new JPanel();
        this.cmbFile = new JComboBox();

        this.btnBrowseFile = new JButton("...");
        this.panelPatchingFile = new JPanel();
        this.cmbPatch = new JComboBox();

        this.btnBrowsePatch = new JButton("...");
        this.panelOptions = new JPanel();

        this.chkBackup = new JCheckBox("Backup Copy");

        this.panelButtons = new JPanel();
        this.btnPatch = new JButton("Patch!");
        this.btnCancel = new JButton("Cancel");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }

        });
        this.panelPatching.setLayout(new GridLayout(2, 1));

        this.panelFileToPatch.setLayout(new BoxLayout(this.panelFileToPatch, 0));

        final Dimension maxSize = new Dimension(800, 25);
        final Dimension minSize = new Dimension(180, 25);
        this.panelFileToPatch.setBorder(new TitledBorder(null, "File To Patch", 0, TitledBorder.DEFAULT_POSITION));
        this.cmbFile.setMaximumSize(maxSize);
        this.cmbFile.setMinimumSize(minSize);
        this.panelFileToPatch.add(this.cmbFile);

        this.panelFileToPatch.add(this.btnBrowseFile);

        this.panelPatching.add(this.panelFileToPatch);

        this.panelPatchingFile.setLayout(new BoxLayout(this.panelPatchingFile, 0));

        this.panelPatchingFile.setBorder(new TitledBorder(null, "Patch File", 0, TitledBorder.DEFAULT_POSITION));
        this.cmbPatch.setMaximumSize(maxSize);
        this.cmbPatch.setMinimumSize(minSize);
        this.panelPatchingFile.add(this.cmbPatch);

        this.panelPatchingFile.add(this.btnBrowsePatch);

        this.panelPatching.add(this.panelPatchingFile);

        this.tabbedPane1.addTab("Patching", null, this.panelPatching, "");

        this.panelOptions.setBorder(new TitledBorder(null, "Options:", 0, TitledBorder.DEFAULT_POSITION));

        this.chkBackup.setSelected(true);
        this.panelOptions.add(chkBackup);

        this.tabbedPane1.addTab("Options", null, panelOptions, "");

        getContentPane().add(this.tabbedPane1, "Center");

        this.panelButtons.add(this.btnPatch);
        this.panelButtons.add(this.btnCancel);

        getContentPane().add(this.panelButtons, "South");

        setMinimumSize(new Dimension(300, 225));
        pack();
        this.setLocationRelativeTo(null);
    }

}
