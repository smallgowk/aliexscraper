/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.phanduy.aliexscrap.utils.OSUtil.isWindows;

/**
 *
 * @author PhanDuy
 */
public class ComputerIdentifier {

    public static String diskSerial;

    private static final Charset WIN_CONSOLE_CHARSET =
            // CP850/CP437 là phổ biến trên Windows console, nhưng nhiều máy dùng 65001 (UTF-8).
            // Ta thử ưu tiên default, nếu fail thì vẫn đọc được chuỗi ASCII số/ chữ.
            Charset.defaultCharset();


//    public static String getDiskSerialNumberForWindow() throws IOException, InterruptedException {
//
//        if (diskSerial != null) {
//            return diskSerial;
//        }
//
//        String sc = "cmd /c" + "wmic diskdrive get serialnumber";
//
//        Process p = Runtime.getRuntime().exec(sc);
//        p.waitFor();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//        String line;
//        StringBuilder sb = new StringBuilder();
//
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//
//        diskSerial = sb.substring(sb.toString().lastIndexOf("r") + 1).trim();
//
//        return diskSerial;
//    }

    public static String getDiskSerialNumberForWindow() throws Exception {

        if (diskSerial != null) {
            return diskSerial;
        }

        diskSerial = getFirstWindowsDiskSerial();

        return diskSerial;
    }

    public static String getFirstWindowsDiskSerial() throws Exception {
        List<String> all = getWindowsDiskSerials(Duration.ofSeconds(8));
        for (String s : all) {
            if (s != null) {
                String t = s.trim();
                if (!t.isEmpty() && !"To be filled by O.E.M.".equalsIgnoreCase(t)) {
                    return t;
                }
            }
        }
        return "";
    }

    /** Trả về danh sách serial của tất cả ổ, đã lọc rỗng. */
    public static List<String> getWindowsDiskSerials(Duration timeout) throws Exception {
        if (!isWindows()) return List.of();

        // 1) Thử PowerShell + CIM (ổn định trên Win10/11)
        List<String> ps = tryPowerShellSerials(timeout);
        if (!ps.isEmpty()) return ps;

        // 2) Fallback WMIC (nhiều máy Win11 không còn wmic)
        List<String> wmic = tryWmicSerials(timeout);
        if (!wmic.isEmpty()) return wmic;

        return List.of();
    }

    // ----- Implementations -----

    private static List<String> tryPowerShellSerials(Duration timeout) throws IOException, InterruptedException, TimeoutException {
        // Cách 1: Win32_PhysicalMedia thường trả SerialNumber thô
        String ps1 = "[Console]::OutputEncoding=[Text.Encoding]::UTF8; " +
                "(Get-CimInstance Win32_PhysicalMedia).SerialNumber";
        List<String> out1 = runPowerShell(ps1, timeout);
        List<String> cleaned1 = cleanSerialLines(out1);
        if (!cleaned1.isEmpty()) return cleaned1;

        String ps2 = "[Console]::OutputEncoding=[Text.Encoding]::UTF8; " +
                "(Get-PhysicalDisk).SerialNumber";
        List<String> out2 = runPowerShell(ps2, timeout);
        List<String> cleaned2 = cleanSerialLines(out2);
        if (!cleaned2.isEmpty()) return cleaned2;

        String ps3 = "[Console]::OutputEncoding=[Text.Encoding]::UTF8; " +
                "(Get-CimInstance Win32_DiskDrive).SerialNumber";
        List<String> out3 = runPowerShell(ps3, timeout);
        return cleanSerialLines(out3);

    }

    private static List<String> tryWmicSerials(Duration timeout) throws Exception {
        List<String> a = runProcess(
                new String[]{"cmd", "/c", "wmic path win32_physicalmedia get SerialNumber"},
                timeout,
                Charset.defaultCharset()
        );
        List<String> cleanedA = cleanSerialLines(a);
        if (!cleanedA.isEmpty()) return cleanedA;

        List<String> b = runProcess(
                new String[]{"cmd", "/c", "wmic diskdrive get serialnumber"},
                timeout,
                Charset.defaultCharset()
        );
        return cleanSerialLines(b);
    }

    private static List<String> runProcess(String[] cmd, Duration timeout, Charset charset) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<List<String>> future = ex.submit(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), charset))) {
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) lines.add(line);
                return lines;
            }
        });

        boolean finished = p.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            p.destroyForcibly();
            ex.shutdownNow();
            throw new TimeoutException("Command timed out");
        }
        try {
            List<String> out = future.get(1, TimeUnit.SECONDS);
            ex.shutdown();
            return out;
        } catch (ExecutionException | TimeoutException e) {
            ex.shutdownNow();
            throw new IOException("Failed reading process output", e);
        }
    }


    private static List<String> runPowerShell(String psCommand, Duration timeout) throws IOException, InterruptedException, TimeoutException {
        // -NoProfile để chạy gọn nhẹ, -Command "..." để thực thi lệnh
        String full = "powershell -NoProfile -ExecutionPolicy Bypass -Command " + psCommand;
        // Chạy qua cmd /c để tương thích môi trường PATH
        return runCommand(new String[]{"cmd", "/c", full}, timeout);
    }

    private static List<String> runCommand(String[] command, Duration timeout) throws IOException, InterruptedException, TimeoutException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        // Đọc output async để tránh deadlock nếu buffer đầy
        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<List<String>> future = ex.submit(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), WIN_CONSOLE_CHARSET))) {
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                return lines;
            }
        });

        boolean finished = p.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            p.destroyForcibly();
            ex.shutdownNow();
            throw new TimeoutException("Command timed out");
        }
        try {
            List<String> out = future.get(1, TimeUnit.SECONDS);
            ex.shutdown();
            return out;
        } catch (ExecutionException | TimeoutException e) {
            ex.shutdownNow();
            throw new IOException("Failed reading process output", e);
        }
    }

    private static List<String> cleanSerialLines(List<String> lines) {
        List<String> res = new ArrayList<>();
        for (String raw : lines) {
            if (raw == null) continue;
            String s = raw.trim();
            if (s.isEmpty()) continue;
            // Loại header phổ biến
            if (s.equalsIgnoreCase("SerialNumber") || s.equalsIgnoreCase("Serial Number")) continue;
            if (s.equalsIgnoreCase("To be filled by O.E.M.")) continue;
            res.add(s);
        }
        return res;

    }

    private static boolean equalsIgnoreCaseTrim(String a, String b) {
        return a != null && b != null && a.trim().equalsIgnoreCase(b.trim());
    }

    private static List<String> cleanWmicOutput(List<String> lines, String header) {
        // WMIC thường in header + các dòng serial. Ta bỏ header, khoảng trắng.
        return cleanSerialLines(lines);
    }



    public static String getDiskSerialNumberForLinux() throws IOException, InterruptedException {
        String sc = "/sbin/udevadm info --query=property --name=sda"; // get HDD parameters as non root user
        String[] scargs = {"/bin/sh", "-c", sc};

        Process p = Runtime.getRuntime().exec(scargs);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (line.indexOf("ID_SERIAL_SHORT") != -1) { // look for ID_SERIAL_SHORT or ID_SERIAL
                sb.append(line);
            }
        }

        return sb.toString().substring(sb.toString().indexOf("=") + 1);
    }

    public static String getDiskSerialNumberForMacos() throws IOException, InterruptedException {

        if (diskSerial != null) {
            return diskSerial;
        }
        
        String sc = "ioreg -rd1 -w0 -c AppleAHCIDiskDriver | grep Serial"; // get HDD parameters as non root user
        Process p = Runtime.getRuntime().exec(sc);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        
        
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Serial Number")) {
                String[] parts = line.split(Pattern.quote("="));
                diskSerial = parts[1].replaceAll(Pattern.quote("\""), "").trim();
                System.out.println("" + diskSerial);
                return diskSerial;
            }

        }
        return null;
    }

    public static String getDiskSerialNumber() {
        if (isWindows()) {
            try {
                return getDiskSerialNumberForWindow();
            } catch (Exception ex) {
                Logger.getLogger(ComputerIdentifier.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        if (OSUtil.isMacOSX()) {
            try {
                return getDiskSerialNumberForMacos();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ComputerIdentifier.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        try {
            return getDiskSerialNumberForLinux();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ComputerIdentifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            return env.get("HOSTNAME");
        } else {
            return "Unknown Computer";
        }
    }

//    public static void main(String[] arguments) {
//        String identifier = generateLicenseKey();
//        System.out.println(identifier);
//    }
}
