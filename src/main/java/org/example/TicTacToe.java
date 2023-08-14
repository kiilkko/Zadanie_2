package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe extends Game{

    private String name;
    static String[] gameField;
    static String step;

    private Gamer gamer;

    boolean win = false;

    private Scanner scanner;

    @Override
    public String GetName() {
        return name;
    }

    public TicTacToe(Gamer current_gamer, Scanner scanner) {
        this.name = "Крестики-нолики";
        this.gamer = current_gamer;
        this.scanner = scanner;
        gameField = new String[9];
        for (int a = 0; a < 9; a++) {
            gameField[a] = String.valueOf(a + 1);
        }
    }

    public void Play () {

        ArrayList<Integer> pool = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        step = "X";
        String winner = null;

        System.out.println("Начинаем игру Крестики-нолики. Игрок " + gamer.user.getLogin() + " играет крестиками X," +
                " компьютер играет ноликами 0. Желаем удачи!");
        printGamefield();
        System.out.println("X ходит первым. Введите номер ячейки для хода:");

        //игра
        while (winner == null)  {

            int numInput;
            String x ;

            //получаем номер ячейки для хода
            //ход игрока
            if (step.equals("X")) {
                if(scanner.hasNext()){
                    x = scanner.next();
                    numInput = Integer.parseInt(x);
                } else {
                    numInput = 0;
                }
                if (pool.contains(numInput)) {
                    pool.removeIf(s -> s.equals(numInput));
                }
            //ход компьютера
            } else if (step.equals("0")) {
                Random rand = new Random();
                numInput = pool.get(rand.nextInt(pool.size()));
                pool.removeIf(s -> s.equals(numInput));

            } else {
                numInput = 0;
            }

            //проверяем что ячейка соответствует заданным границам
            if (!(numInput > 0 && numInput <= 9)) {
                System.out.println(
                        "Ошибка, ячейки с таким номером не существует, введите другое число:");
                continue;
            }

            //записываем Х или 0 в ячейку
            if (gameField[numInput - 1].equals(String.valueOf(numInput))) {
                gameField[numInput - 1] = step;
                //меняем знак игрока
                if (step.equals("X")) {
                    step = "0";
                } else {
                    step = "X";
                }
                //выводим изменившееся поле и полсчитываем очки
                printGamefield();
                winner = CountPoints();
            } else {
                System.out.println(
                        "Ячейка уже занята, выберите другую:");
            }
        }


        //объявление результатов
        if (winner.equalsIgnoreCase("ничья")) {
            System.out.println(
                    "Это ничья! Спасибо за игру");
        } else {
            System.out.println("" + winner + " победил! Спасибо за игру");
            win = true;
        }

        SaveResult(winner);
    }
    private String CountPoints()
    {
        for (int a = 0; a < 8; a++) {
            String line = null;

            switch (a) {
                case 0:
                    line = gameField[0] + gameField[1] + gameField[2];
                    break;
                case 1:
                    line = gameField[3] + gameField[4] + gameField[5];
                    break;
                case 2:
                    line = gameField[6] + gameField[7] + gameField[8];
                    break;
                case 3:
                    line = gameField[0] + gameField[3] + gameField[6];
                    break;
                case 4:
                    line = gameField[1] + gameField[4] + gameField[7];
                    break;
                case 5:
                    line = gameField[2] + gameField[5] + gameField[8];
                    break;
                case 6:
                    line = gameField[0] + gameField[4] + gameField[8];
                    break;
                case 7:
                    line = gameField[2] + gameField[4] + gameField[6];
                    break;
            }

            if (line.equals("XXX")) {
                return "Игрок";
            } else if (line.equals("000")) {
                return "Компьютер";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(gameField).contains(
                    String.valueOf(a + 1))) {
                break;
            }
            else if (a == 8) {
                return "ничья";
            }
        }

        if (step.equals("X")) {
            System.out.println("Ход игрока, введите номер ячейки для " + step + ":");
        } else if (step.equals("0")) {
            System.out.println("Компьютер ходит:");
        }

        return null;
    }

    // Печатает игровое поле
    /* |---|---|---|
       | 1 | 2 | 3 |
       |-----------|
       | 4 | 5 | 6 |
       |-----------|
       | 7 | 8 | 9 |
       |---|---|---|*/

    private void printGamefield()
    {
        System.out.println("|---|---|---|");
        System.out.println("| " + gameField[0] + " | "
                + gameField[1] + " | " + gameField[2]
                + " |");
        System.out.println("|-----------|");
        System.out.println("| " + gameField[3] + " | "
                + gameField[4] + " | " + gameField[5]
                + " |");
        System.out.println("|-----------|");
        System.out.println("| " + gameField[6] + " | "
                + gameField[7] + " | " + gameField[8]
                + " |");
        System.out.println("|---|---|---|");
    }

    private void SaveResult(String winner) {
        if (winner.equals("ничья")) {
            return;
        }
        if (gamer.PlayedStatistics.containsKey(GetName())) {
            int val = gamer.PlayedStatistics.get(GetName());
            gamer.PlayedStatistics.replace(GetName(), val + 1);
        } else {
            gamer.PlayedStatistics.put(GetName(), 1);
        }

        if (win) {
            if (gamer.WinStatistics.containsKey(GetName())) {
                int val = gamer.WinStatistics.get(GetName());
                gamer.WinStatistics.replace(GetName(), val + 1);
            } else {
                gamer.WinStatistics.put(GetName(), 1);
            }
        }
    }

}
