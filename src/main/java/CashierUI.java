import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {

    public UserModel user = null;

    public JFrame view;

    public JButton btnAddPurchase = new JButton("Manage Purchase");
    public JButton btnManageCustomer = new JButton("Manage Customers");
    public JButton btnChangeUserInfo = new JButton("Change User Info");

    public CashierUI(UserModel user) {

        this.user = user;
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());

        panelButtons.add(btnAddPurchase);
        panelButtons.add(btnManageCustomer);
        panelButtons.add(btnChangeUserInfo);



        view.getContentPane().add(panelButtons);


        btnAddPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI ap = new ManagePurchaseUI();
                ap.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ui = new ManageCustomerUI();
                ui.run();
            }
        });

        btnChangeUserInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserChangeInfoUI ui = new UserChangeInfoUI(user);
                ui.view.setVisible(true);
            }
        });
    }
}