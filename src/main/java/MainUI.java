import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {

    public JFrame view;

    public JButton btnManageProduct = new JButton("Manage Product");
    public JButton btnManagegCustomer = new JButton("Manage Customer");
    public JButton btnAddPurchase = new JButton("Manage Purchase");

//    public JButton btnUpdateProduct = new JButton("Update Product Information");

    public MainUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel titleLayout = new JPanel(new FlowLayout());
        JLabel title = new JLabel("Store Management System");
        title.setSize(50,50);
        title.setFont (title.getFont ().deriveFont (34.0f));
        titleLayout.add(title);
        view.getContentPane().add(titleLayout);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnManageProduct);
        panelButtons.add(btnManagegCustomer);
        panelButtons.add(btnAddPurchase);
//        panelButtons.add(btnUpdateProduct);
        view.getContentPane().add(panelButtons);


        btnManageProduct.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageProductUI ap = new ManageProductUI();
                ap.run();
            }
        });
        btnManagegCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ac = new ManageCustomerUI();
                ac.run();
            }
        });

        btnAddPurchase.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI ap = new ManagePurchaseUI();
                ap.run();
            }
        });

//        btnUpdateProduct.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                ManageProductUI ui = new ManageProductUI();
//                ui.run();
//            }
//        });
    }
}
