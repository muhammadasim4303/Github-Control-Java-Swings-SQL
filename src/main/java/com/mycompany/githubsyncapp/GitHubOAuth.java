package com.mycompany.githubsyncapp;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GitHubOAuth {
    private static final String CLIENT_ID = ""; //your client id
    private static final String CLIENT_SECRET = ""; //your client secret
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

    public static String getGitHubUsername() {
        try {
            String token = getAccessToken();
            if (token == null) {
                System.out.println("Error: GitHub Access Token is missing!");
                return null;
            }
    
            URL url = new URL("https://api.github.com/user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "token " + token);
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
    
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = br.readLine();
            br.close();
    
            //Extract the username from JSON response
            org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
            return jsonResponse.getString("login");
    
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
