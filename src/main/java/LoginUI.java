import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnLogout = new JButton("Logout");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Login");
        view.setSize(600, 400);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLogin);
        panelButtons.add(btnLogout);

        pane.add(panelButtons);

        btnLogin.addActionListener(new LoginActionListener());

        btnLogout.addActionListener(new LogoutActionListener());

    }

    public static void main(String[] args) {
        int port = 10007;
        LoginUI ui = new LoginUI();
        ui.view.setVisible(true);

    }

    private class LogoutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                link = new Socket("localhost", 10007);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                output.println("LOGOUT");
                output.println(accessToken);
                int res = input.nextInt();
                System.out.println("Sent LOGOUT " + accessToken + " received " + res);

                if (res == 0)
                    JOptionPane.showMessageDialog(null, "Invalid token for logout!");
                else
                    JOptionPane.showMessageDialog(null, "Logout successfully = ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.length() == 0 || password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be null!");
                return;
            }



            Gson gson = new Gson();

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.LOGIN;
            user.mUsername = username;
            user.mPassword = password;
            msg.data = gson.toJson(user);


            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", 10007);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.OPERATION_FAILED)
                JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
            else {
                accessToken = msg.ssid;
                JOptionPane.showMessageDialog(null, "Access granted with access token = " + accessToken);
                view.setVisible(false);


                user = gson.fromJson(msg.data, UserModel.class);
//
//            System.out.println("Logging in with user = " + username);
//
//            user = StoreManager.getInstance().getDataAdapter().loadUser(username);

                if (user != null && user.mPassword == user.mPassword) {
                    System.out.println("User = " + user);
                    if (user.mUserType == UserModel.CUSTOMER) {
                        CustomerUI ui = new CustomerUI(user);
                        System.out.println("CustomerUI = " + ui);
                        ui.view.setVisible(true);
                    }

                    if (user.mUserType == UserModel.MANAGER) {
                        ManagerUI ui = new ManagerUI(user);
                        ui.view.setVisible(true);
                    } else if (user.mUserType == UserModel.CASHIER) {
                        CashierUI ui = new CashierUI(user);
                        System.out.println("CustomerUI = " + ui);
                        ui.view.setVisible(true);
                    } else if (user.mUserType == UserModel.ADMIN) {
                        AdminUI ui = new AdminUI(user);
                        System.out.println("AdminUI = " + ui);
                        ui.view.setVisible(true);
                    }


                }
            }
        }
    }
}
