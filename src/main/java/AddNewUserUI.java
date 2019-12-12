import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewUserUI {

    public JFrame view;


    public JButton btnSave = new JButton("Save User");

    public JTextField textUsername = new JTextField(15);
    public JTextField textPassword = new JTextField(15);
    public JTextField textFullname = new JTextField(15);
    public JTextField textUsertype = new JTextField(15);
    public JTextField textCustomerID = new JTextField(15);

    public AddNewUserUI() {

        this.view = new JFrame();

        view.setTitle("Add New User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
//        line1.setPreferredSize(new Dimension(200, 50));
        JLabel label = new JLabel("Username", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line1.add(label);
        line1.add(textUsername);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
//        line2.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Password ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line2.add(label);
        line2.add(textPassword);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
//        line3.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Full Name", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line3.add(label);
        line3.add(textFullname);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("User Type ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line4.add(label);
        line4.add(textUsertype);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("CustomerID ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line5.add(label);
        line5.add(textCustomerID);
        line5.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line5);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            Gson gson = new Gson();
            String username = textUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                return;
            }

            String password = textPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                return;
            }

            String fullName = textFullname.getText();
            if (fullName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Full Name cannot be empty!");
                return;
            }

            String userType = textUsertype.getText();
            try {
                user.mUserType = Integer.parseInt(userType);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Usertype is invalid!");
                return;
            }

            if (user.mUserType == 0) {
                String customerID = textCustomerID.getText();
                try {
                    user.mCustomerID = Integer.parseInt(customerID);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                    return;
                }
            }
            else {
                user.mCustomerID = null;
            }

            user.mUsername = username;
            user.mPassword = password;
            user.mFullname = fullName;

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
