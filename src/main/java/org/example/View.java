package org.example;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View {

    private Controller controller;
    boolean isLogIn;
    boolean isAdmin;
    public JFrame f;
    public JFrame CRUD;
    public JButton logIn;
    public JButton registration;

    public View(Controller controller) {
        this.controller = controller;
        this.isLogIn = false;
        this.isAdmin = false;
        this.f = new JFrame("Вход/регистрация");
        this.CRUD = new JFrame("Выбор действий");

    }

    private String PasswordToString(char[] pass) {
        String passStr = "";
        for (int i = 0; i < pass.length; i++) {
            passStr += pass[i];
        }
        return passStr;
    }

    public void Initialize() {
        JButton logIn = new JButton("Войти");
        logIn.setBounds(100, 120, 200, 30);
        JButton registration = new JButton("Зарегистрироваться");
        registration.setBounds(100, 180, 200, 30);
        f.add(registration);
        f.add(logIn);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setVisible(true);

        View.RegistrationAL regAL = new View.RegistrationAL();
        registration.addActionListener(regAL);

        View.LogInAL logAL = new View.LogInAL();
        logIn.addActionListener(logAL);

    }

    public void ShowCRUDpanel() {

        JButton info = new JButton("Информация об игроках");
        info.setBounds(100, 80, 200, 30);
        View.InfoAL Ial = new View.InfoAL();
        info.addActionListener(Ial);

        JButton edit = new JButton("Редактировать аккаунт игрока");
        edit.setBounds(100, 140, 200, 30);
        View.EditAL Eal = new View.EditAL();
        info.addActionListener(Eal);

        JButton delete = new JButton("Удалить аккаунт игрока");
        delete.setBounds(100, 200, 200, 30);
        View.DeleteAL Dal = new View.DeleteAL();
        info.addActionListener(Dal);

        JButton logout = new JButton("Выйти");
        logout.setBounds(100, 260, 200, 30);
        View.LogInAL Lal = new View.LogInAL();
        info.addActionListener(Lal);


        CRUD.add(info);
        CRUD.add(edit);
        CRUD.add(delete);
        CRUD.add(logout);
        CRUD.setSize(400, 400);
        CRUD.setLayout(null);
        CRUD.setVisible(true);
        CRUD.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


    }

    public  class RegistrationAL implements ActionListener {

        static JDialog RegWindow;
        static JTextField log;
        static JPasswordField pass;
        static JButton ok;
        static JButton cancel;
        static JLabel l;
        static JLabel p;

        public RegistrationAL() {
            this.RegWindow = new JDialog();

            this.l = new JLabel("Придумайте логин:");
            l.setBounds(100, 30, 240, 20);
            RegWindow.add(l);
            this.log = new JTextField(16);
            log.setBounds(100, 50, 240, 20);
            RegWindow.add(log);

            this.p = new JLabel("Придумайте пароль:");
            p.setBounds(100, 70, 240, 20);
            RegWindow.add(p);
            this.pass = new JPasswordField(16);
            pass.setBounds(100, 90, 240, 20);
            RegWindow.add(pass);

            this.ok = new JButton("Ок");
            ok.setBounds(230, 140, 110, 20);
            RegWindow.add(ok);
            OkReg okReg = new OkReg();
            ok.addActionListener(okReg);

            this.cancel = new JButton("Отмена");
            cancel.setBounds(100, 140, 110, 20);
            RegWindow.add(cancel);
            CancelReg cReg = new CancelReg();
            cancel.addActionListener(cReg);

            RegWindow.setSize(440, 230);
            RegWindow.setLayout(null);
        }

        private class OkReg implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String passStr = PasswordToString (pass.getPassword());
                if (controller.AddGamer(log.getText(), passStr)) {
                    log.setText("");
                    pass.setText("");
                    RegWindow.setVisible(false);

                    JFrame resp = new JFrame("Сообщение");
                    JLabel respText = new JLabel("Спасибо за регистрацию! Теперь вы можете войти, используя ваш логин и пароль");
                    resp.setSize(700, 230);
                    respText.setHorizontalAlignment(JLabel.CENTER);
                    resp.add(respText);
                    resp.setVisible(true);
                }
            }
        }

        private class CancelReg implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                log.setText("");
                pass.setText("");
                RegWindow.setVisible(false);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (s.equals("Зарегистрироваться")) {
                RegWindow.setVisible(true);
            }
        }
    }

    public class LogInAL implements ActionListener {

        static JFrame LogWindow;
        static JTextField log;
        static JPasswordField pass;
        static JButton ok;
        static JButton cancel;
        static JLabel l;
        static JLabel p;

        public LogInAL() {
            this.LogWindow = new JFrame();

            this.l = new JLabel("Введите логин:");
            l.setBounds(100, 30, 240, 20);
            LogWindow.add(l);
            this.log = new JTextField(16);
            log.setBounds(100, 50, 240, 20);
            LogWindow.add(log);

            this.p = new JLabel("Введите пароль:");
            p.setBounds(100, 70, 240, 20);
            LogWindow.add(p);
            this.pass = new JPasswordField(16);
            pass.setBounds(100, 90, 240, 20);
            LogWindow.add(pass);

            this.ok = new JButton("Ок");
            ok.setBounds(230, 140, 110, 20);
            LogWindow.add(ok);
            LogInAL.OkReg okReg = new LogInAL.OkReg();
            ok.addActionListener(okReg);

            this.cancel = new JButton("Отмена");
            cancel.setBounds(100, 140, 110, 20);
            LogWindow.add(cancel);
            LogInAL.CancelReg cReg = new LogInAL.CancelReg();
            cancel.addActionListener(cReg);

            LogWindow.setSize(440, 230);
            LogWindow.setLayout(null);
        }

        private class OkReg implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String passStr = PasswordToString (pass.getPassword());
                String logStr = log.getText();

                if (controller.CheckAndLogIn(log.getText(), passStr)) {

                    isLogIn = true;
                    log.setText("");
                    pass.setText("");
                    LogWindow.setVisible(false);

                    JFrame resp = new JFrame("Сообщение");
                    JLabel respText = new JLabel("Вход выполнен");
                    resp.setSize(400, 230);
                    respText.setHorizontalAlignment(JLabel.CENTER);
                    resp.add(respText);
                    resp.setVisible(true);

                    f.setVisible(false);
                    if (logStr.equals("Admin") && passStr.equals("Admin")) {
                        isAdmin = true;
                        ShowCRUDpanel();
                    } else {
                        //TicTacToe ttt = new TicTacToe()
                    }




                }
            }
        }

        private class CancelReg implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                log.setText("");
                pass.setText("");
                LogWindow.setVisible(false);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (s.equals("Войти")) {
                LogWindow.setVisible(true);
            }
        }
    }

    public class InfoAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    public class EditAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //int id = 0;
            JFrame frame = new JFrame();

            //String id;
            String id = JOptionPane.showInputDialog("Введите id игрока:");
            String log = JOptionPane.showInputDialog("Введите логин игрока:");
            String pass = JOptionPane.showInputDialog("Введите пароль игрока:");
            controller.EditGamerParameter(Integer.parseInt(id), log, pass, );

            final JDialog dialog = new JDialog(frame,
                    "Edit an account",
                    true);
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(
                    JDialog.DO_NOTHING_ON_CLOSE);
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    //setLabel("Thwarted user attempt to close window.");
                }
            });
        }
    }

    public class DeleteAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    public class LogoutAL implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

}



