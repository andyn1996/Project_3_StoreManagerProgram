import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI {

    public UserModel user = null;

    public JFrame view;

    public JButton btnMakePurchase = new JButton("Make a Purchase");
    public JButton btnCancelPurchase = new JButton("Cancel a Purchase");
    public JButton btnViewPurchases = new JButton("View Purchase History");
    public JButton btnSearchProduct = new JButton("Search Product");
    public JButton btnChangeUserInfo = new JButton("Change User Info");

    public CustomerUI(UserModel user) {

        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont(title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Fullname: " + user.mFullname));
        panelUser.add(new JLabel("Username: " + user.mUsername));

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnMakePurchase);
        panelButtons.add(btnCancelPurchase);
        panelButtons.add(btnSearchProduct);

        JPanel panelButtons2 = new JPanel(new FlowLayout());
        panelButtons2.add(btnChangeUserInfo);
        panelButtons2.add(btnViewPurchases);

        view.getContentPane().add(panelButtons);
        view.getContentPane().add(panelButtons2);


        btnViewPurchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PurchaseHistoryUI ui = new PurchaseHistoryUI(user);
                ui.view.setVisible(true);
            }
        });

        btnMakePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseCustomerUI ui = new ManagePurchaseCustomerUI(user);
                ui.run();
            }
        });

        btnSearchProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SearchProductInputUI ui = new SearchProductInputUI(user);
                ui.view.setVisible(true);
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