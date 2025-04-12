/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duyuno
 */
public class OSUtil {

    private static String OS = null;

    private static Font[] fonts = null;

    public static String pathChar;

    static {
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAllFonts();

//        for(Font font : fonts) {
//            System.out.println("Font: " + font.getFontName());
//        }
    }

    public static Font findFont(String name) {
        if (fonts == null) {
            fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getAllFonts();
        }

        for (Font font : fonts) {
            if (font.getFontName().equals(name)) {
                return font;
            }
        }

        return fonts[0];
    }

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isMacOSX() {
        return getOsName().startsWith("Mac OS X");
    }

    public static void setAppTitle(JFrame jframe) {

        try {
            boolean isHasWindow = false;
            boolean isHasNumbis = false;
            String nimbusName = null;
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

//                System.out.println("" + info.getClassName());
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    isHasWindow = true;
                    break;
                } else if ("Mac OS X".equals(info.getName())) {
//                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    isHasNumbis = true;
                    nimbusName = info.getClassName();
                }
            }

            if (!isHasWindow && isHasNumbis && nimbusName != null) {
                javax.swing.UIManager.setLookAndFeel(nimbusName);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        jframe.setTitle("DropShipTools");
        jframe.setName("DropShipTools");

        try {
            ImageIcon icon = new ImageIcon(jframe.getClass().getResource("/Icons/appIcon.png"));

            if (isMacOSX()) {
                jframe.setIconImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            } else {
                jframe.setIconImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            }
        } catch (Exception ex) {

        }

    }

    public static void setAppTitle(JFrame jframe, String title) {

        ImageIcon icon = new ImageIcon(jframe.getClass().getResource("/Icons/appIcon.png"));

        if (isMacOSX()) {
            jframe.setIconImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        } else {
            jframe.setIconImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }

        jframe.setTitle(title);
    }

    public static void openFileOrFolder(String path) {
        System.out.println("openFileOrFolder: " + path);
        try {
            String executeFile = isWindows() ? "explorer.exe" : "open";
//            String folderPath = path + (path.endsWith(Configs.pathChar) ? "" : Configs.pathChar) + "1.txt";
//            System.out.println("Folder path: " + folderPath);
//            Process p = new ProcessBuilder(executeFile, (isWindows() ? "/select," : "/users,") + path).start();
            if (isWindows()) {
                Process p = new ProcessBuilder(executeFile, "/select," + path).start();
            } else {
                Runtime.getRuntime().exec("/usr/bin/open " + path).waitFor();
            }

        } catch (IOException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String GetAddress(String addressType) {
        String address = "";
        InetAddress lanIp = null;
        try {

            String ipAddress = null;
            Enumeration<NetworkInterface> net = null;
            net = NetworkInterface.getNetworkInterfaces();

            while (net.hasMoreElements()) {
                NetworkInterface element = net.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();

                while (addresses != null && addresses.hasMoreElements() && element != null && element.getHardwareAddress() != null && element.getHardwareAddress().length > 0 && !isVMMac(element.getHardwareAddress())) {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address) {

                        if (ip.isSiteLocalAddress()) {
                            ipAddress = ip.getHostAddress();
                            lanIp = InetAddress.getByName(ipAddress);
                        }

                    }

                }
            }

            if (lanIp == null) {
                return null;
            }

            if (addressType.equals("ip")) {

                address = lanIp.toString().replaceAll("^/+", "");

            } else if (addressType.equals("mac")) {

                address = getMacAddress(lanIp);

            } else {

                throw new Exception("Specify \"ip\" or \"mac\"");

            }

        } catch (UnknownHostException ex) {

            ex.printStackTrace();

        } catch (SocketException ex) {

            ex.printStackTrace();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return address;

    }

    private static String getMacAddress(InetAddress ip) {
        String address = null;
        try {

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            address = sb.toString();

        } catch (SocketException ex) {

            ex.printStackTrace();

        }

        return address;
    }

    private static boolean isVMMac(byte[] mac) {
        if (null == mac) {
            return false;
        }
        byte invalidMacs[][] = {
            {0x00, 0x05, 0x69}, //VMWare
            {0x00, 0x1C, 0x14}, //VMWare
            {0x00, 0x0C, 0x29}, //VMWare
            {0x00, 0x50, 0x56}, //VMWare
            {0x08, 0x00, 0x27}, //Virtualbox
            {0x0A, 0x00, 0x27}, //Virtualbox
            {0x00, 0x03, (byte) 0xFF}, //Virtual-PC
            {0x00, 0x15, 0x5D} //Hyper-V
        };

        for (byte[] invalid : invalidMacs) {
            if (invalid[0] == mac[0] && invalid[1] == mac[1] && invalid[2] == mac[2]) {
                return true;
            }
        }

        return false;
    }
}
