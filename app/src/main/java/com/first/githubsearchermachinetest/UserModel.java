package com.first.githubsearchermachinetest;

public class UserModel
{
    int id;
    String login,repos_url,avatar_url;

    public UserModel(int id, String login, String repos_url,String avatar_url) {
        this.id = id;
        this.login = login;
        this.repos_url = repos_url;
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
