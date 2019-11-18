import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ManageCustomerUI {

    public JFrame view;


    public JButton btnLoad = new JButton("Load Customer");
    public JButton btnSave = new JButton("Save Customer");

    public JTextField textCustomerID = new JTextField(15);
    public JTextField textName = new JTextField(15);
    public JTextField textAddress = new JTextField(15);
    public JTextField textPhone = new JTextField(15);

    public ManageCustomerUI() {

        this.view = new JFrame();

        view.setTitle("Manage Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
//        line1.setPreferredSize(new Dimension(200, 50));
        JLabel label = new JLabel("CustomerID", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line1.add(label);
        line1.add(textCustomerID);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
//        line2.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Name ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line2.add(label);
        line2.add(textName);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
//        line3.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Address", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line3.add(label);
        line3.add(textAddress);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Phone ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line4.add(label);
        line4.add(textPhone);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            CustomerModel customer = new CustomerModel();
            Gson gson = new Gson();
            String id = textCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                customer.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(customer.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null, "Product NOT exists!");
            } else {
                    textName.setText(customer.mName);
                    textAddress.setText(customer.mAddress);
                    textPhone.setText(customer.mPhone);
            }


//            try {
//                Socket link = new Socket("localhost", 10007);
//                Scanner input = new Scanner(link.getInputStream());
//                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
//
//                MessageModel msg = new MessageModel();
//                msg.code = MessageModel.GET_CUSTOMER;
//                msg.data = Integer.toString(customer.mCustomerID);
//                output.println(gson.toJson(msg)); // Send to Server
//
//                msg = gson.fromJson(input.nextLine(), MessageModel.class);
//
//                if (msg.code == MessageModel.OPERATION_FAILED) {
//                    JOptionPane.showMessageDialog(null, "Customer Does Not Exists!");
//                } else {
//                    customer = gson.fromJson(msg.data, CustomerModel.class);
//                    textName.setText(customer.mName);
//                    textAddress.setText(customer.mAddress);
//                    textPhone.setText(customer.mPhone);
//                }
//            String name = textName.getText();
//            if (name.length() == 0) {
//                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
//                return;
//            }
//
//            customer.mName = name;
//
//            String address = textAddress.getText();
//            if (address.length() == 0) {
//                JOptionPane.showMessageDialog(null, "Address cannot be empty!");
//                return;
//            }
//
//            customer.mAddress = address;
//
//            String phone = textPhone.getText();
//            if (phone.length() == 0) {
//                JOptionPane.showMessageDialog(null, "Phone cannot be empty!");
//                return;
//            }
//
//            customer.mPhone = phone;
//
//            switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
//                case SQLiteDataAdapter.CUSTOMER_DUPLICATE_ERROR:
//                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
//                    break;
//                default:
//                    view.setVisible(false);
//                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
//            }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }


    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            Gson gson = new Gson();
            String id = textCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                customer.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer ID is invalid!");
            }

            String name = textName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }

            customer.mName = name;

            String address = textAddress.getText();
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "Address cannot be empty!");
                return;
            }

            customer.mAddress = address;

            String phone = textPhone.getText();
            if (phone.length() == 0) {
                JOptionPane.showMessageDialog(null, "Phone Number cannot be empty!");
                return;
            }
            customer.mPhone = phone;

            try {
                Socket link = new Socket("localhost", 10007);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customer);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer was not saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Customer is SAVED successfully!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
