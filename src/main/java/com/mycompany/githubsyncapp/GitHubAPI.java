package com.mycompany.githubsyncapp;

import java.awt.Desktop;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class GitHubAPI {
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/callback"; 
    private static String accessToken = "";

    public static void authenticateUser() throws Exception {
        String authUrl = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID +
                         "&redirect_uri=" + REDIRECT_URI + "&scope=repo";

        // Open default browser for user login
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(authUrl));
        } else {
            System.out.println("Please open the following URL manually in a browser: " + authUrl);
        }

        // Wait for user to enter the auth code
        System.out.print("Enter the authorization code from GitHub: ");
        Scanner scanner = new Scanner(System.in);
        String authCode = scanner.nextLine();

        // Exchange the auth code for an access token
        exchangeAuthCodeForToken(authCode);
    }

    private static void exchangeAuthCodeForToken(String authCode) throws Exception {
        String tokenUrl = "https://github.com/login/oauth/access_token";
        String params = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + authCode;

        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();

        // Extract token
        accessToken = response.split("\"access_token\":\"")[1].split("\"")[0];
        System.out.println("Access Token: " + accessToken);
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
