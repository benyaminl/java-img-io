/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13.imgblob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Blob;

/**
 *
 * @author benli
 */
public class DB {
    private static Connection c;
    
    public static void init() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/laravel_blog";
            // Ini adalah open connection!
            Connection c = DriverManager
                    .getConnection(url,"root", "");
            DB.c = c;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    
    public static ArrayList<String[]> query(String sql) {
        return query(sql, new Object[]{});
    } 
    
    public static ArrayList<String[]> query(String sql, 
            Object[] data) {
        ArrayList<String[]> resultData = new ArrayList<>();

        try {
            // Contoh Prepared Statement
            // insert into posts(title, body) VALUES(?, ?)
            // new Object[] {"Judul","asdasd"} 
            PreparedStatement s = DB.c.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                s.setObject(i+1, data[i]);
            }
            ResultSet result = s.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int totalColumn = md.getColumnCount();

            while(result.next()) {// Ambil baris selanjutnya!
                // Create new Array List temp to add to Global ArrayList
                String[] temp = new String[totalColumn];
                for (int i = 1; i <= totalColumn; i++) {// Isikan semua 
                    // Kolom ke dalam temp 
                    temp[i-1] = String.valueOf(result.getObject(i));
                }
                resultData.add(temp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
        return resultData;
    }
    
    public static ArrayList<Object[]> queryObject(String sql, 
            Object[] data) {
        ArrayList<Object[]> resultData = new ArrayList<>();

        try {
            // Contoh Prepared Statement
            // insert into posts(title, body) VALUES(?, ?)
            // new Object[] {"Judul","asdasd"} 
            PreparedStatement s = DB.c.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                s.setObject(i+1, data[i]);
            }
            ResultSet result = s.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int totalColumn = md.getColumnCount();

            while(result.next()) {// Ambil baris selanjutnya!
                // Create new Array List temp to add to Global ArrayList
                Object[] temp = new Object[totalColumn];
                for (int i = 1; i <= totalColumn; i++) {// Isikan semua 
                    // Kolom ke dalam temp 
                    temp[i-1] = result.getObject(i);
                }
                resultData.add(temp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
        return resultData;
    }
    
    public static ArrayList<Object[]> queryObject(String sql) {
        return queryObject(sql, new Object[] {});
    }
    
    public static boolean insert(String sql, Object[] data) {
        boolean check = false;
        
        try {
            PreparedStatement s = DB.c.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
//                if (data[i] instanceof Blob) {
//                    s.setBlob(i+1, (Blob)data[i]);
//                } else {
                s.setObject(i+1, data[i]);
//                }
            }
            s.execute();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
        check = true;
        return check;
    }
    
    public static int update(String sql, Object[] data) {
        int affectedRow = 0;
        try {
            PreparedStatement s = DB.c.prepareStatement(sql);
            for (int i = 0; i < data.length; i++) {
                s.setObject(i+1, data[i]);
            }
            // https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html#executeUpdate--
            affectedRow = s.executeUpdate();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }        
        return affectedRow;
    }
    
    public static int delete(String sql, Object[] data) {
        return DB.update(sql, data);
    }
    
    public static void close() {
        try {
            DB.c.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());    
        }
    }
}
