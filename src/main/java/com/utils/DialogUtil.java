/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author duyuno
 */
public class DialogUtil {

    public static void showInfoMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public static void showWarningMessage(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showErrorMessage(JFrame frame, String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
