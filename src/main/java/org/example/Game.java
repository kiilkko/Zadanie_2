package org.example;

public abstract class Game {

    private String name;
    public int[][] gameField;
    private Gamer gamer;
    public String GetName() { return name; }


    public abstract void Play();


}
