/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13.imgblob;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author benli
 */
public class ImagePanel extends javax.swing.JPanel {
    protected BufferedImage img;
    
    protected String loc;
    /**
     * Creates new form PanelImageShow
     */
    public ImagePanel() {
        initComponents();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = this.getSize();
        g.drawImage(img, 0,0,d.width, d.height, this);
    }
    
    public void setImage(String location) {
        this.loc = location;
        this.loadImage();
    }
    
    public void setImage(byte[] img) {
        ByteArrayInputStream in = new ByteArrayInputStream(img);
        try {
            this.img = ImageIO.read(in);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        this.revalidate();
        this.repaint();
    }
    
    protected void loadImage() {
        try {
            File f = new File(loc);
            // Baca File dari Folder
            this.img = ImageIO.read(f);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        this.revalidate();
        this.repaint();
    }
    
    public byte[] toBlob() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // CHeck ini ada transparansi apa ga, kalau ga ada diset JPG
            if (this.img.getTransparency() == java.awt.Transparency.OPAQUE) {
                ImageIO.write((BufferedImage)this.img, "jpg", baos);
            } else { // Kalau ada maka PNG
                ImageIO.write((BufferedImage)this.img, "png", baos);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
        byte[] data = baos.toByteArray();
        return data;
    }
    
    public void saveImageToBlob(String location) {
        try {
            File f = new File(location);
            FileOutputStream o = new FileOutputStream(f);
            byte[] d = this.toBlob();
            o.write(d, 0, d.length);
            o.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
