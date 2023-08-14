package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Controller implements Printable{
    private HashSet<Gamer> Gamers;
    private HashSet<String> Games;
    private HashMap<String, Integer> LoginPasswordList;  //ключ - логин+пароль, значение - id; для авторизации
    private HashMap<Integer, Gamer> GamersList; //ключ - id, значение - геймер
    public Gamer currentGamer;  //авторизованный геймер
    private static int usercount = 0;
    private String path;  //путь до файла с сериализованными объектами\
    private Scanner scanner;


    public Controller(String path, Scanner sc) {
        this.path = path;
        this.scanner = sc;
        this.Gamers = new HashSet<>();
        GamersDeserialization(this.Gamers);
        this.GamersList = new HashMap<>();
        FillGamersList();
        this.LoginPasswordList = new HashMap<>();
        this.usercount = Gamers.size();

        this.Games = new HashSet<>();
        Games.add("Крестики-нолики");
        AddAdmin();

        this.currentGamer = new Gamer();
    }

    private int GenerateUserId() {
        usercount++;
        return usercount;
    }

    private void FillGamersList(){
        while (Gamers.iterator().hasNext()){
            Gamer gamer = Gamers.iterator().next();
            int id = gamer.user.getId();
            GamersList.put(id, gamer);
        }
    }
     private void GamersSerialization(){
         try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
         {
             oos.writeObject(Gamers);
             System.out.println("Файл успешно записан");
         }
         catch(Exception ex){

             System.out.println(ex.getMessage());
         }
     }

    private void GamersDeserialization(HashSet<Gamer> Gamers){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)))
        {
            Gamers=((HashSet<Gamer>)ois.readObject());
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    public boolean CheckAndLogIn (String logInput,String passInput) {
//        String logInput;
//        String passInput;
//        System.out.println("Введите логин:");
//        logInput = scanner.next();
//        System.out.println("Введите пароль:");
//        passInput = scanner.next();

        String loginPassword = logInput + passInput;
        if (LoginPasswordList.containsKey(loginPassword)) {
            int id = LoginPasswordList.get(loginPassword);
            currentGamer = GamersList.get(id);
            System.out.println("С возвращением!");
            return true;
        } else {
            System.out.println("Такого пользователя не существует. Проверьте правильность ввода логина и пароля.");
        }
        return false;
    }


    public void LogOut() {
        this.currentGamer = new Gamer();
    }

//------------------------------------------------------------------------------------------
//Create
    public boolean AddGamer(String login, String password) {
        int id = GenerateUserId();
        Gamer gamer = new Gamer(login, password, id);
        Gamers.add(gamer);
        GamersList.put(gamer.user.getId(), gamer);
        LoginPasswordList.put(login+password, id);
        GamersSerialization();
        System.out.println("Игрок успешно добавлен" );
        return true;
    }
    public boolean AddAdmin() {
        Gamer admin = new Gamer("Admin", "Admin", 0);
        Gamers.add(admin);
        GamersList.put(admin.user.getId(), admin);
        LoginPasswordList.put("AdminAdmin", 0);
        GamersSerialization();
        return true;
    }

//------------------------------------------------------------------------------------------
//Update

    public void EditGamerParameter(int id, String login, String password, String newParameter, int paramNb) {

        if (GamersList.containsKey(id)) {
            Gamer gamer = GamersList.get(id);
            if (gamer.user.getLogin().equals(login) &&
                    gamer.user.getPassword().equals(password)) {
                switch (paramNb) {
                    case 1:  //логин
                        gamer.user.setLogin(newParameter);
                        break;
                    case 2:   //пароль
                        gamer.user.setPassword(newParameter);
                        break;
                    default:
                        System.out.println("Может быть задан только параметр 1 или 2! Проверьте правильность ввода параметра." );
                        return;
                }
                //перезаписываем информацию
                GamersList.replace(id, gamer);
                try {
                    Gamers.removeIf(x ->x.user.getId()==id);
                    Gamers.add(gamer);
                } catch (Exception e) {
                    System.out.println("Ошибка обработки id!" );
                }

            } else {
                System.out.println("Id не обнаружен. Проверьте правильность ввода id." );
                return;
            }
        } else {
            System.out.println("Логин и/или пароль неверны. Проверьте правильность ввода логина и пароля." );
            return;
        }
        System.out.println("Параметр игрока успешно изменен." );
        GamersSerialization();
    }


//------------------------------------------------------------------------------------------
//Read
    public void PrintGamerInfo (int gamerId) {
        Gamer gamer = GamersList.get(gamerId);
        gamer.user.toString();
        PrintStatistics(gamerId);
    }

    public void PrintAllGamersInfo() {
        for (Map.Entry<Integer, Gamer> item : GamersList.entrySet()) {
            System.out.println(item.getValue().user.toString());
            PrintStatistics(item.getKey());
        }
    }

    private void PrintStatistics(int gamerId) {
        Gamer gamer = GamersList.get(gamerId);
        if(gamer==null) {
            return;
        }
        System.out.println("Статистика по игроку с id " + gamerId + ":");
        if (!gamer.PlayedStatistics.isEmpty()) {
            for (Map.Entry<String, Integer> item : gamer.PlayedStatistics.entrySet()) {
                System.out.println("Сыграно игр:");
                System.out.println(item.getKey().toString() + " " + item.getValue());
            }
        } else {
            System.out.println("Нет сыгранных игр");
        }
        if (!gamer.WinStatistics.isEmpty()) {
            for (Map.Entry<String, Integer> item : gamer.WinStatistics.entrySet()) {
                System.out.println("Выиграно игр:");
                System.out.println(item.getKey().toString() + " " + item.getValue());
            }
        }   else {
            System.out.println("Нет выигранных игр");
        }

    }

    public void PrintGamersList() {
        System.out.println("Список игроков: ");
        for (Map.Entry<Integer, Gamer> item : GamersList.entrySet()) {
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }

    public void PrintGamesList() {
        System.out.println("Доступны следующие игры: ");
        while (Games.iterator().hasNext()) {
            System.out.println( Games.iterator().next());
        }
    }

    public void PrintGameRules(String gameName) {
        System.out.println("Правила игры: ");
        switch (gameName) {
            case "Крестики-нолики":
                System.out.println("Игроки по очереди ставят крестик или нолик в ячейки игрового поля. " +
                        "Выигрывает тот, у кого первого получится занять своими значками всю горизонталь, вертикаль или диагональ.");
                break;
            default:
                System.out.println("Такой игры нет в списке. Проверьте правильность ввода названия игры." );
                break;

        }
    }


//------------------------------------------------------------------------------------------
//Delete

    public boolean DeleteGamer(int id) {
        if (GamersList.containsKey(id)) {
            GamersList.remove(id);
            try {
                Gamers.removeIf(x ->x.user.getId()==id);
                GamersSerialization();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка обработки id!" );
            }
        }
        return false;

    }
}
