package com.mycompany.githubsyncapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class GitHubAPI {
    private static final String GITHUB_API_URL = "https://api.github.com";

    //Keeps authenticateUser() method intact
    public static void authenticateUser() {
        String token = GitHubOAuth.getAccessToken();
        if (token == null) {
            JOptionPane.showMessageDialog(null, "User is not authenticated with GitHub.", "Authentication", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "User is authenticated with GitHub!", "Authentication", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Fetch Public & Private Repositories
    public static List<String> getRepositories(boolean isPrivate) {
        List<String> repoList = new ArrayList<>();
        try {
            String token = GitHubOAuth.getAccessToken();
            if (token == null) {
                JOptionPane.showMessageDialog(null, "Error: GitHub Access Token is missing!", "Error", JOptionPane.ERROR_MESSAGE);
                return repoList;
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(GITHUB_API_URL + "/user/repos"))
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray repos = new JSONArray(response.body());

            for (int i = 0; i < repos.length(); i++) {
                JSONObject repo = repos.getJSONObject(i);
                boolean isRepoPrivate = repo.getBoolean("private");
                if (isRepoPrivate == isPrivate) {
                    repoList.add(repo.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load repositories.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return repoList;
    }

    //Create New Repository
    public static void createRepository(String repoName, boolean isPrivate) {
        try {
            String token = GitHubOAuth.getAccessToken();
            if (token == null) {
                JOptionPane.showMessageDialog(null, "Error: GitHub Access Token is missing!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JSONObject requestBody = new JSONObject();
            requestBody.put("name", repoName);
            requestBody.put("private", isPrivate);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(GITHUB_API_URL + "/user/repos"))
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                JOptionPane.showMessageDialog(null, "Repository '" + repoName + "' created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create repository. Response: " + response.body(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to create repository.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
