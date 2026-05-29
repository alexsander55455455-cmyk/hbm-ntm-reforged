package com.hbm.handler;

import com.hbm.Tags;
import com.hbm.main.MainRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HTTPHandler {
    private static final int TIMEOUT_MS = 10_000;

    public static volatile List<String> capsule = new ArrayList<>();
    public static volatile List<String> tipOfTheDay = new ArrayList<>();
    public static volatile boolean newVersion = false;
    public static volatile String versionNumber = "";
    public static volatile String changes = "";

    public static void loadStats() {
        Thread t = new Thread(() -> {
            try {
                loadVersion();
                loadSoyuz();
                loadTips();
            } catch (IOException e) {
                MainRegistry.logger.warn("Version checker failed!", e);
            }
        }, "NTM Version Checker");
        t.setDaemon(true);
        t.start();
    }

    private static BufferedReader openReader(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(TIMEOUT_MS);
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
    }

    private static void loadVersion() throws IOException {
        newVersion = false;
    }

    private static void loadSoyuz() throws IOException {
        URL github = new URL("https://gist.githubusercontent.com/HbmMods/a1cad71d00b6915945a43961d0037a43/raw/soyuz_holo");
        List<String> cap = new ArrayList<>();
        try (BufferedReader in = openReader(github)) {
            String line;
            while ((line = in.readLine()) != null) {
                cap.add(line);
            }
        }
        capsule = cap;
    }

    private static void loadTips() throws IOException {
        URL github = new URL("https://gist.githubusercontent.com/HbmMods/a03c66ba160184e12f43de826b30c096/raw/tip_of_the_day");
        List<String> tips = new ArrayList<>();
        try (BufferedReader in = openReader(github)) {
            String line;
            while ((line = in.readLine()) != null) {
                tips.add(line);
            }
        }
        tipOfTheDay = tips;
    }
}
