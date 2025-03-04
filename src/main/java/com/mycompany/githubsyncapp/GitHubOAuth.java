package com.mycompany.githubsyncapp;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GitHubOAuth {
    private static final String CLIENT_ID = "GITHUB_CLIENT_ID"; 
    private static final String CLIENT_SECRET = "GITHUB_CLIENT_SECRET"; 
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String REDIRECT_URI = "http://localhost:8080/callback"; 
    private static String accessToken = null;

    public static void loginWithGitHub() {
        try {
            String authLink = AUTH_URL + "?client_id=" + CLIENT_ID + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&scope=repo";
            Desktop.getDesktop().browse(new URI(authLink));

            String authCode = JOptionPane.showInputDialog("Enter the GitHub authorization code:");

            if (authCode != null && !authCode.isEmpty()) {
                accessToken = getAccessToken(authCode);
                if (accessToken != null) {
                    JOptionPane.showMessageDialog(null, "GitHub Login Successful!");
                    GitHubAPI.authenticateUser();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to get access token.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "GitHub login failed!");
        }
    }

    public static void authenticateUser() {
        if (accessToken == null) {
            System.out.println("User not authenticated with GitHub.");
        } else {
            System.out.println("User authenticated! Access Token: " + accessToken);
        }
    }

    private static String getAccessToken(String code) throws IOException {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");

        String params = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code + "&redirect_uri=" + REDIRECT_URI;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = br.readLine();
        br.close();

        JsonParser parser = new JsonParser();
        JsonObject jsonResponse = parser.parse(response).getAsJsonObject();
                
        return jsonResponse.has("access_token") ? jsonResponse.get("access_token").getAsString() : null;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
