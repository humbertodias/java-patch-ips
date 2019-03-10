package jips;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Controller {

    private View view;

    public Controller(View view) {
        this.view = view;

        view.btnPatch.addActionListener(this::BtnPatchActionPerformed);
        view.btnCancel.addActionListener(this::BtnCancelActionPerformed);

        view.btnBrowseFile.addActionListener(this::BtnBrowseFileActionPerformed);
        view.btnBrowsePatch.addActionListener(this::BtnBrowsePatchActionPerformed);
    }

    public void run() {
        view.setVisible(true);
    }


    private void BtnCancelActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void BtnPatchActionPerformed(ActionEvent evt) {
        try {
            final IpsPatcher ips = new IpsPatcher(view.cmbPatch.getSelectedItem().toString(), view.cmbFile.getSelectedItem().toString(), view.chkBackup.isSelected());
            ips.patch();
            JOptionPane.showMessageDialog(view, "Patching Complete!\nPatches applied");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void BtnBrowsePatchActionPerformed(ActionEvent evt) {
        final FileNameExtensionFilter filter = new FileNameExtensionFilter("Patch's IPS", "ips");
        Browse(view.cmbPatch, filter);
    }

    private void BtnBrowseFileActionPerformed(ActionEvent evt) {
        final FileNameExtensionFilter filter = new FileNameExtensionFilter("All", "*.*");
        Browse(view.cmbFile, filter);
    }

    public boolean Browse(JComboBox Combo, FileNameExtensionFilter filter) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(view);
        if (returnVal == 0) {
            File SelectedFile = chooser.getSelectedFile();
            Combo.addItem(SelectedFile.getPath());
            return false;
        }
        return false;
    }

}
