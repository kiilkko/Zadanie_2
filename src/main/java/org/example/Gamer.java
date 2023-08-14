package org.example;

import java.io.Serializable;
import java.util.HashMap;

public class Gamer implements Serializable {

    public User user;
    public HashMap<String, Integer> WinStatistics; //выигранные игры
    public HashMap<String, Integer> PlayedStatistics;  //сыгранные игры

    public Gamer() {
        this.user = new User("", "", 0);
        this.WinStatistics = new HashMap<>();
        this.PlayedStatistics = new HashMap<>();
    }

    public Gamer(String login, String password, int id) {
        this.user = new User(login, password, id);
        this.WinStatistics = new HashMap<>();
        this.PlayedStatistics = new HashMap<>();
    }


}
