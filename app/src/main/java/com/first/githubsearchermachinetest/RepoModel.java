package com.first.githubsearchermachinetest;

public class RepoModel {
    int id;
    String reponame,repoforks,repostars;

    public RepoModel(int id, String reponame, String repoforks, String repostars) {
        this.id = id;
        this.reponame = reponame;
        this.repoforks = repoforks;
        this.repostars = repostars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponame() {
        return reponame;
    }

    public void setReponame(String reponame) {
        this.reponame = reponame;
    }

    public String getRepoforks() {
        return repoforks;
    }

    public void setRepoforks(String repoforks) {
        this.repoforks = repoforks;
    }

    public String getRepostars() {
        return repostars;
    }

    public void setRepostars(String repostars) {
        this.repostars = repostars;
    }
}
