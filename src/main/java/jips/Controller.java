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

        view.btnPatch.addActionListener(this::btnPatchActionPerformed);
        view.btnCancel.addActionListener(this::btnCancelActionPerformed);

        view.btnBrowseFile.addActionListener(this::btnBrowseFileActionPerformed);
        view.btnBrowsePatch.addActionListener(this::btnBrowsePatchActionPerformed);
    }

    public void run() {
        view.setVisible(true);
    }


    private void btnCancelActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void btnPatchActionPerformed(ActionEvent evt) {
        try {
            final IpsPatcher ips = new IpsPatcher(view.cmbPatch.getSelectedItem().toString(), view.cmbFile.getSelectedItem().toString(), view.chkBackup.isSelected());
            ips.patch();
            JOptionPane.showMessageDialog(view, "Patching Complete!\nPatches applied");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void btnBrowsePatchActionPerformed(ActionEvent evt) {
        final FileNameExtensionFilter filter = new FileNameExtensionFilter("Patch's IPS", "ips");
        browse(view.cmbPatch, filter);
    }

    private void btnBrowseFileActionPerformed(ActionEvent evt) {
        browse(view.cmbFile, null);
    }

    private boolean browse(JComboBox combo, FileNameExtensionFilter filter) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        if (filter != null)
            chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(view);
        if (returnVal == 0) {
            final File selectedFile = chooser.getSelectedFile();
            combo.addItem(selectedFile.getPath());
            return false;
        }
        return false;
    }

}
