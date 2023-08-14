package org.example;


import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private String password;
    public int id;


    public User (String login, String password, int id) {
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLogin (String login) {
        this.login = login;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getLogin () {
        return login;
    }

    public String getPassword () {
        return password;
    }

    @Override
    public String toString () {
        return "User{" +
                "login=" + this.login + "/," +
                "password=" + this.password + "/," +
                "id=" + this.id + "}";
    }
}

