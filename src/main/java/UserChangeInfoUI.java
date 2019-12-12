import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserChangeInfoUI {

    public JFrame view;


    public JButton btnSave = new JButton("Save User Info");

    public JTextField textName = new JTextField(15);
    public JTextField textPassword = new JTextField(15);
    private UserModel user;

    public UserChangeInfoUI(UserModel userIn) {

        this.view = new JFrame();

        view.setTitle("Change Username/Password");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Full Name", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line1.add(label);
        line1.add(textName);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        label = new JLabel("Password ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line2.add(label);
        line2.add(textPassword);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        btnSave.addActionListener(new SaveButtonListener());
        user = userIn;
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Gson gson = new Gson();

            String name = textName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Full name cannot be empty!");
                return;
            }

            String password = textPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                return;
            }

            user.mFullname = name;
            user.mPassword = password;

            int result = StoreManager.getInstance().getDataAdapter().saveUser(user);

            if (result == IDataAdapter.USER_SAVE_FAILED) {
                JOptionPane.showMessageDialog(null, "User is NOT saved successfully");
            }
            else {
                JOptionPane.showMessageDialog(null, "User is saved successfully");
            }

            /*
            try {
                Socket link = new Socket("localhost", 10007);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.SAVE_USER_INFO;
                msg.data = gson.toJson(user);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "User was not saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "User is SAVED successfully!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        }
    }


}
