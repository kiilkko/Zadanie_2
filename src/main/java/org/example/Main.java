package org.example;

import java.util.Scanner;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Controller controller = new Controller("C:\\Users\\aziph\\IdeaProjects\\Zadanie_1\\gamers.txt", in);
        View view = new View(controller);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.Initialize();
            }
        });


//        controller.AddGamer("first", "password1");
//        controller.AddGamer("second", "password2");
//        controller.AddGamer("third", "password3");
//        controller.AddGamer("fourth", "password4");
//        controller.AddGamer("fifth", "password5");
//
//        controller.PrintGamersList();

//        for(int i = 0; i<1; i++){
//            if (controller.CheckAndLogIn()) {
//                TicTacToe ttt = new TicTacToe(controller.currentGamer, in);
//                ttt.Play();
//                controller.LogOut();
//            }
//        }

   //     controller.PrintAllGamersInfo();
    }
}
