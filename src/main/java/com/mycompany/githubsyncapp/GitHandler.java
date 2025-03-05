package com.mycompany.githubsyncapp;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;

import javax.swing.JOptionPane;

public class GitHandler {

    public static void linkLocalToRemote(String localPath, String repoName) {
        try {
            System.out.println("Initializing Git repository in: " + localPath);
            Git git = Git.init().setDirectory(new File(localPath)).call();

            //Get GitHub username & repo URL
            String username = GitHubOAuth.getGitHubUsername();
            if (username == null) {
                System.out.println("Error: Unable to retrieve GitHub username.");
                return;
            }

            String remoteUrl = "https://github.com/" + username + "/" + repoName + ".git";

            //Add remote origin
            git.remoteAdd().setName("origin").setUri(new org.eclipse.jgit.transport.URIish(remoteUrl)).call();
            System.out.println("Remote repository set to: " + remoteUrl);

            //Add all files to commit
            git.add().addFilepattern(".").call();

            //Commit the changes
            String commit = JOptionPane.showInputDialog("Enter Git Commit:");
            git.commit().setMessage(commit).call();
            System.out.println("Files committed successfully.");

            //Push to GitHub using OAuth token
            String token = GitHubOAuth.getAccessToken();
            if (token == null) {
                System.out.println("Error: GitHub Access Token is missing.");
                return;
            }

            git.push()
                .setRemote("origin")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""))
                .call();
            System.out.println("Files pushed successfully to: " + remoteUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
