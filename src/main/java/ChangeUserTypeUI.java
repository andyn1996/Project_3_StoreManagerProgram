import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUserTypeUI {

    public JFrame view;

    public JButton btnSave = new JButton("Save User");
    public JButton btnLoad = new JButton("Load User");

    public JTextField textUsername = new JTextField(15);
    public JTextField textUsertype = new JTextField(15);
    public JTextField textCustomerID = new JTextField(15);

    JPanel line5 = new JPanel(new FlowLayout());


    //public JLabel labUserType = new JLabel("User Type: ");


    UserModel userLoaded;

    public ChangeUserTypeUI() {

        this.view = new JFrame();

        view.setTitle("Change User Type");
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

        JPanel line4 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("User Type ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line4.add(label);
        line4.add(textUsertype);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        label = new JLabel("CustomerID ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line5.add(label);
        line5.add(textCustomerID);
        line5.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line5);
        //line5.setVisible(false);

        textUsertype.getDocument().addDocumentListener(new UserTypeChangeListener());

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        btnSave.addActionListener(new SaveButtonListener());
        btnLoad.addActionListener(new LoadButtonListener());
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private class UserTypeChangeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            process();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            process();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        private void process() {

            if (textUsertype.getText().equals("0")) {
                line5.setVisible(true);
            }
            else {
                line5.setVisible(false);
            }
        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String username = textUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                return;
            }

            String userType = textUsertype.getText();
            try {
                userLoaded.mUserType = Integer.parseInt(userType);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Usertype is invalid!");
                return;
            }

            if (userLoaded.mUserType == 0) {
                String customerID = textCustomerID.getText();
                try {
                    userLoaded.mCustomerID = Integer.parseInt(customerID);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                    return;
                }
            }
            else {
                userLoaded.mCustomerID = null;
            }

            userLoaded.mUsername = username;

            int result = StoreManager.getInstance().getDataAdapter().saveUser(userLoaded);

            if (result == IDataAdapter.USER_SAVE_FAILED) {
                JOptionPane.showMessageDialog(null, "User is NOT saved successfully");
            }
            else {
                JOptionPane.showMessageDialog(null, "User is saved successfully");
            }

        }
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            userLoaded = new UserModel();
            Gson gson = new Gson();
            String username = textUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }

            System.out.println(username);
            userLoaded = StoreManager.getInstance().getDataAdapter().loadUser(username);

            if (userLoaded == null) {
                JOptionPane.showMessageDialog(null, "User does NOT exists!");
            } else {
                textUsername.setText(userLoaded.mUsername);
                textUsertype.setText(Integer.toString(userLoaded.mUserType));
                if (userLoaded.mCustomerID == null) {
                    textCustomerID.setText("null");
                }
                else {
                    textCustomerID.setText(Integer.toString(userLoaded.mCustomerID));
                }
            }

            // do client/server


//            try {
//                Socket link = new Socket("localhost", 10007);
//                Scanner input = new Scanner(link.getInputStream());
//                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
//
//                MessageModel msg = new MessageModel();
//                msg.code = MessageModel.GET_PRODUCT;
//                msg.data = Integer.toString(product.mProductID);
//                output.println(gson.toJson(msg)); // Send to Server
//
//                msg = gson.fromJson(input.nextLine(), MessageModel.class);
//
//                if (msg.code == MessageModel.OPERATION_FAILED) {
//                    JOptionPane.showMessageDialog(null,"Product Does Not Exists!");
//                }
//                else {
//                    product = gson.fromJson(msg.data, ProductModel.class);
//                    textName.setText(product.mName);
//                    textExpirationDate.setText(product.mExpirationDate);
//                    textPrice.setText(Double.toString(product.mPrice));
//                    textTaxRate.setText(Double.toString(product.mTaxRate));
//                    textQuantity.setText(Double.toString(product.mQuantity));
//                    textSupplier.setText(product.mSupplier);
//                    textManufacturedDate.setText(product.mManufacturedDate);
//                }


//                output.println("GET");
//                output.println(product.mProductID);
//
//                product.mName = input.nextLine();
//
//                if (product.mName.equals("null")) {
//                    JOptionPane.showMessageDialog(null, "Product NOT exists!");
//                    return;
//                }
//
//                textName.setText(product.mName);
//
//                product.mExpirationDate = input.nextLine();
//                textExpirationDate.setText(product.mExpirationDate);
//
//                product.mPrice = input.nextDouble();
//                textPrice.setText(Double.toString(product.mPrice));
//
//                product.mTaxRate = input.nextDouble();
//                textTaxRate.setText(Double.toString(product.mTaxRate));
//
//                product.mQuantity = input.nextDouble();
//                textQuantity.setText(Double.toString(product.mQuantity));
//
//                input.nextLine();
//
//                product.mSupplier = input.nextLine();
//                textSupplier.setText(product.mSupplier);
//
//                product.mManufacturedDate = input.nextLine();
//                textManufacturedDate.setText(product.mManufacturedDate);

//        } catch(
//        Exception e)
//
//        {
//            e.printStackTrace();
//        }
        }
    }


}
