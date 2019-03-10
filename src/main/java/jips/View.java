package jips;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class View extends JDialog {

    private JTabbedPane tabbedPane1;

    private JPanel panelPatching;
    private JPanel panelFileToPatch;
    protected JComboBox cmbFile;
    private JButton btnBrowseFile;

    private JPanel panelPatchingFile;
    protected JComboBox cmbPatch;
    private JButton btnBrowsePatch;

    private JPanel panelOptions;
    protected JCheckBox chkBackup;

    private JPanel panelButtons;
    protected JButton btnPatch;
    protected JButton btnCancel;

    public View() {
        initComponents();
    }

    private void initComponents() {
        this.tabbedPane1 = new JTabbedPane();
        this.panelPatching = new JPanel();
        this.panelFileToPatch = new JPanel();
        this.cmbFile = new JComboBox();

        this.btnBrowseFile = new JButton();
        this.panelPatchingFile = new JPanel();
        this.cmbPatch = new JComboBox();

        this.btnBrowsePatch = new JButton();
        this.panelOptions = new JPanel();

        this.chkBackup = new JCheckBox();

        this.panelButtons = new JPanel();
        this.btnPatch = new JButton();
        this.btnCancel = new JButton();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("JIPS 4.0");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }

        });
        this.panelPatching.setLayout(new GridLayout(2, 1));

        this.panelFileToPatch.setLayout(new BoxLayout(this.panelFileToPatch, 0));

        this.panelFileToPatch.setBorder(new TitledBorder(null, "File To Patch:", 0, 0, new Font("Dialog", 1, 12)));
        this.cmbFile.setMaximumSize(new Dimension(800, 26));
        this.cmbFile.setMinimumSize(new Dimension(180, 26));
        this.panelFileToPatch.add(this.cmbFile);

        this.btnBrowseFile.setText("...");
        this.btnBrowseFile.addActionListener(this::BtnBrowseFileActionPerformed);
        this.panelFileToPatch.add(this.btnBrowseFile);

        this.panelPatching.add(this.panelFileToPatch);

        this.panelPatchingFile.setLayout(new BoxLayout(this.panelPatchingFile, 0));

        this.panelPatchingFile.setBorder(new TitledBorder(null, "Patch File:", 0, 2, new Font("Dialog", 1, 12)));
        this.cmbPatch.setMaximumSize(new Dimension(32767, 26));
        this.cmbPatch.setMinimumSize(new Dimension(180, 26));
        this.panelPatchingFile.add(this.cmbPatch);

        this.btnBrowsePatch.setText("...");
        this.btnBrowsePatch.addActionListener(this::BtnBrowsePatchActionPerformed);
        this.panelPatchingFile.add(this.btnBrowsePatch);

        this.panelPatching.add(this.panelPatchingFile);

        this.tabbedPane1.addTab("Patching", null, this.panelPatching, "");

        this.panelOptions.setBorder(new TitledBorder(null, "Options:", 0, 0, new Font("Dialog", 1, 12)));

        this.chkBackup.setSelected(true);
        this.chkBackup.setText("Backup Copy");
        this.panelOptions.add(this.chkBackup);

        this.tabbedPane1.addTab("Options", null, panelOptions, "");

        getContentPane().add(this.tabbedPane1, "Center");

        this.btnPatch.setText("Patch!");
        this.panelButtons.add(this.btnPatch);

        this.btnCancel.setText("Cancel");
        this.panelButtons.add(this.btnCancel);

        getContentPane().add(this.panelButtons, "South");

        setMinimumSize(new Dimension(300, 225));
        pack();
        this.setLocationRelativeTo(null);
    }

    private void BtnBrowsePatchActionPerformed(ActionEvent evt) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Patch's IPS", "ips");
        Browse(this.cmbPatch, filter);
    }

    private void BtnBrowseFileActionPerformed(ActionEvent evt) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All", "*.*");
        Browse(this.cmbFile, filter);
    }


    public boolean Browse(JComboBox Combo, FileNameExtensionFilter filter) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == 0) {
            File SelectedFile = chooser.getSelectedFile();
            Combo.addItem(SelectedFile.getPath());
            return false;
        }
        return false;
    }

}
