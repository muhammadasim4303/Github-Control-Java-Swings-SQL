package com.mycompany.githubsyncapp;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;

public class GitHandler {
    private Git git;

    public GitHandler(String localRepoPath) {
        try {
            this.git = Git.open(new File(localRepoPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commitChanges(String message) {
        try {
            git.add().addFilepattern(".").call();
            git.commit().setMessage(message).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void pushChanges() {
        try {
            git.push().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
