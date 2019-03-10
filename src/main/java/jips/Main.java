package jips;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JDialog {
    boolean patchzipped;
    boolean filezipped;
    private JPanel jPanel8;
    private JPanel jPanel7;
    private JPanel jPanel6;
    private JPanel panelButtons;
    private JPanel jPanel4;
    private JPanel jPanel3;
    private JPanel jPanel2;
    private JPanel jPanel1;
    private JComboBox CmbFile;
    private JButton BtnPatch;

    public Main(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private JCheckBox chkBackup;
    private JPanel jPanel11;
    private JPanel jPanel10;
    private JButton BtnCancel;
    private JTabbedPane jTabbedPane1;


    private void initComponents() {
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.CmbFile = new JComboBox();

        this.btnBrowseFile = new JButton();
        this.jPanel4 = new JPanel();
        this.cmbPatch = new JComboBox();

        this.btnBrowsePatch = new JButton();
        this.jPanel2 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jPanel11 = new JPanel();

        this.chkBackup = new JCheckBox();
        this.jPanel7 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jPanel10 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();

        this.panelButtons = new JPanel();
        this.BtnPatch = new JButton();
        this.BtnCancel = new JButton();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("JIPS 4.0");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Main.this.closeDialog(evt);
            }

        });
        this.jPanel1.setLayout(new GridLayout(2, 1));

        this.jPanel3.setLayout(new BoxLayout(this.jPanel3, 0));

        this.jPanel3.setBorder(new TitledBorder(null, "File To Patch:", 0, 0, new Font("Dialog", 1, 12)));
        this.CmbFile.setMaximumSize(new Dimension(800, 26));
        this.CmbFile.setMinimumSize(new Dimension(180, 26));
        this.jPanel3.add(this.CmbFile);

        this.btnBrowseFile.setText("...");
        this.btnBrowseFile.addActionListener(evt -> Main.this.BtnBrowseFileActionPerformed(evt));
        this.jPanel3.add(this.btnBrowseFile);

        this.jPanel1.add(this.jPanel3);

        this.jPanel4.setLayout(new BoxLayout(this.jPanel4, 0));

        this.jPanel4.setBorder(new TitledBorder(null, "Patch File:", 0, 2, new Font("Dialog", 1, 12)));
        this.cmbPatch.setFont(new Font("Dialog", 0, 12));
        this.cmbPatch.setMaximumSize(new Dimension(32767, 26));
        this.cmbPatch.setMinimumSize(new Dimension(180, 26));
        this.jPanel4.add(this.cmbPatch);

        this.btnBrowsePatch.setText("...");
        this.btnBrowsePatch.addActionListener(evt -> Main.this.BtnBrowsePatchActionPerformed(evt));
        this.jPanel4.add(this.btnBrowsePatch);

        this.jPanel1.add(this.jPanel4);

        this.jTabbedPane1.addTab("Patching", null, this.jPanel1, "");

        this.jPanel2.setLayout(new BoxLayout(this.jPanel2, 0));

        this.jPanel6.setBorder(new TitledBorder(null, "Options:", 0, 0, new Font("Dialog", 1, 12)));
        this.jPanel11.setLayout(new GridLayout(2, 1));

        this.chkBackup.setSelected(true);
        this.chkBackup.setText("Backup Copy");
        this.jPanel11.add(this.chkBackup);

        this.jPanel6.add(this.jPanel11);

        this.jPanel2.add(this.jPanel6);

        this.jTabbedPane1.addTab("Options", null, this.jPanel2, "");

        this.jPanel7.setLayout(new BorderLayout());

        this.jPanel10.setLayout(new GridLayout(3, 1));

        this.jPanel8.add(this.jPanel10);

        this.jPanel7.add(this.jPanel8, "Center");

        getContentPane().add(this.jTabbedPane1, "Center");

        this.BtnPatch.setText("Patch!");
        this.BtnPatch.addActionListener(evt -> Main.this.BtnPatchActionPerformed(evt));
        this.panelButtons.add(this.BtnPatch);

        this.BtnCancel.setText("Cancel");
        this.BtnCancel.addActionListener(evt -> Main.this.BtnCancelActionPerformed(evt));
        this.panelButtons.add(this.BtnCancel);

        getContentPane().add(this.panelButtons, "South");

        setMinimumSize(new Dimension(300, 225));
        pack();
        this.setLocationRelativeTo(null);
    }

    private void BtnBrowsePatchActionPerformed(ActionEvent evt) {
        try {
            this.patchzipped = Browse(this.cmbPatch);
        } catch (Exception e) {
        }
    }

    private void BtnBrowseFileActionPerformed(ActionEvent evt) {
        try {
            this.filezipped = Browse(this.CmbFile);
        } catch (Exception e) {
        }
    }

    private void BtnCancelActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void BtnPatchActionPerformed(ActionEvent evt) {
        try {
            IpsPatcher ips = new IpsPatcher(this.cmbPatch.getSelectedItem().toString(), this.CmbFile.getSelectedItem().toString(), this.chkBackup.isSelected());
            ips.patch();
            JOptionPane.showMessageDialog(this, "Patching Complete!\nPatches applied");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    private JComboBox cmbPatch;
    private JLabel jLabel3;
    private JLabel jLabel2;

    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new Main(frame, true).setVisible(true);
    }

    private JLabel jLabel1;


    private JButton btnBrowsePatch;
    private JButton btnBrowseFile;

    public boolean Browse(JComboBox Combo) {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == 0) {
            File SelectedFile = chooser.getSelectedFile();
            Combo.addItem(SelectedFile.getPath());
            return false;
        }
        return false;
    }
}