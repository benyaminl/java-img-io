/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13.imgblob;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/**
 *
 * @author benli
 */
public class FormShowImage extends javax.swing.JFrame {

    /**
     * Creates new form FormShowImage
     */
    public FormShowImage() {
        this.setDefaultLookAndFeelDecorated(true);
        try {
            // Bikin UI kaya Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnShowImage = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Save = new javax.swing.JButton();
        btnSaveToDB = new javax.swing.JButton();
        btnFileDialog = new javax.swing.JButton();
        btnDBDialog = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnShowImage.setText("Open Image");
        btnShowImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowImageActionPerformed(evt);
            }
        });

        jLabel1.setText("Lokasi");

        Save.setText("Save to File");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        btnSaveToDB.setText("Save to DB");
        btnSaveToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveToDBActionPerformed(evt);
            }
        });

        btnFileDialog.setText("Select Picture");
        btnFileDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileDialogActionPerformed(evt);
            }
        });

        btnDBDialog.setText("Open From DB");
        btnDBDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDBDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnShowImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSaveToDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnFileDialog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDBDialog))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowImage)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(Save)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveToDB)
                    .addComponent(btnFileDialog)
                    .addComponent(btnDBDialog))
                .addContainerGap(552, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    ImagePanel img;
    private void btnShowImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowImageActionPerformed
        if (this.img == null) {
            this.img = new ImagePanel();
            this.img.setBounds(10, 80, 
                    this.getSize().width-50, this.getSize().width-50);
            this.getContentPane().add(img);
        }
        this.img.setImage(jTextField1.getText());
//        this.img.loadImage();
//        repaint();
//        this.revalidate();
    }//GEN-LAST:event_btnShowImageActionPerformed
    
    public void loadImgFromDB(int ID) {
        // Ambil sebagai Blob/Bytes
        Object temp = DB.queryObject("SELECT image FROM images WHERE id = ?", 
                new Object[] { ID }).get(0)[0];
        byte[] data = (byte[])temp;
//        JOptionPane.showMessageDialog(null, data.length);
        if (this.img == null) {
            this.img = new ImagePanel();
            this.img.setBounds(10, 80, 
                    this.getSize().width-50, this.getSize().width-50);
            this.getContentPane().add(img);
        }
        // Tampilin di GUI
        this.img.setImage(data);
    }
    
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        JFileChooser d = new JFileChooser(path.toString());
        int result = d.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.img.saveImageToBlob(d.getSelectedFile().toString());
             JOptionPane.showMessageDialog(null,"File tersimpan di "
             + d.getSelectedFile().toString());
        }
    }//GEN-LAST:event_SaveActionPerformed

    private void btnSaveToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveToDBActionPerformed
        String query = "INSERT INTO images(nama, image) VALUES(?, ?)";
        try {
            Blob b = new SerialBlob(this.img.toBlob());
            // Ini bisa jalan, tadi waktu di asistensi lupa buka koneksi alias 
            // DB.init(). SAD!
            DB.insert(query, new Object[] {"coba", b});
            JOptionPane.showMessageDialog(rootPane, "Tersimpan di DB!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        }
    }//GEN-LAST:event_btnSaveToDBActionPerformed

    private void btnFileDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileDialogActionPerformed
        JFileChooser d = new JFileChooser();
        int result = d.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            jTextField1.setText(d.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_btnFileDialogActionPerformed

    private void btnDBDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDBDialogActionPerformed
        FormSelectImgDB f = new FormSelectImgDB();
        f.parent = this;
        f.setVisible(true);
    }//GEN-LAST:event_btnDBDialogActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormShowImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormShowImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormShowImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormShowImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormShowImage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JButton btnDBDialog;
    private javax.swing.JButton btnFileDialog;
    private javax.swing.JButton btnSaveToDB;
    private javax.swing.JButton btnShowImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
