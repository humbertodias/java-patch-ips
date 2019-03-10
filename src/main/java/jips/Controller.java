package jips;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Controller {

    private View view;

    public Controller(View view) {
        this.view = view;
        view.btnPatch.addActionListener(this::BtnPatchActionPerformed);
        view.btnCancel.addActionListener(this::BtnCancelActionPerformed);
    }

    public void run() {
        view.setVisible(true);
    }


    private void BtnCancelActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void BtnPatchActionPerformed(ActionEvent evt) {
        try {
            IpsPatcher ips = new IpsPatcher(view.cmbPatch.getSelectedItem().toString(), view.cmbFile.getSelectedItem().toString(), view.chkBackup.isSelected());
            ips.patch();
            JOptionPane.showMessageDialog(view, "Patching Complete!\nPatches applied");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

}
