import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchProductInputUI {

    public JFrame view;


    public JButton btnSearch = new JButton("Search Product");

    public JTextField textName = new JTextField(15);
    public JTextField textMinPrice = new JTextField(15);
    public JTextField textMaxPrice = new JTextField(15);
    UserModel user;

    public SearchProductInputUI(UserModel userIn) {

        user = userIn;

        this.view = new JFrame();

        view.setTitle("Search Products");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
//        line1.setPreferredSize(new Dimension(200, 50));
        JLabel label = new JLabel("Name", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line1.add(label);
        line1.add(textName);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);


        /*
        JPanel line2 = new JPanel(new FlowLayout());
//        line2.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Name ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line2.add(label);
        line2.add(textName);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);
         */

        JPanel line3 = new JPanel(new FlowLayout());
//        line3.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Min Price", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line3.add(label);
        line3.add(textMinPrice);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Max Price ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(120, 50));
        line4.add(label);
        line4.add(textMaxPrice);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnSearch);
        view.getContentPane().add(panelButtons);

        btnSearch.addActionListener(new searchProductListener());
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class searchProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            int minPriceIn = 0;
            int maxPriceIn = 10000;

            Gson gson = new Gson();

            String name = textName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product name cannot be null!");
                return;
            }

            String minPrice = textMinPrice.getText();
            if (minPrice.length() == 0) {
                JOptionPane.showMessageDialog(null, "Min Price cannot be empty!");
                return;
            }

            try {
                minPriceIn = Integer.parseInt(minPrice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Min Price is invalid!");
            }

            String maxPrice = textMaxPrice.getText();
            if (maxPrice.length() == 0) {
                JOptionPane.showMessageDialog(null, "Max Price cannot be empty!");
                return;
            }

            try {
                maxPriceIn = Integer.parseInt(maxPrice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Max Price is invalid!");
            }


            ProductSearchUI screen = new ProductSearchUI(user, name, minPriceIn, maxPriceIn);
            screen.view.setVisible(true);

            /*
            if (result == IDataAdapter.CUSTOMER_SAVE_FAILED) {
                JOptionPane.showMessageDialog(null, "Customer is NOT saved successfully");
            }
            else {
                JOptionPane.showMessageDialog(null, "Customer is saved successfully");
            }
             */
        }
    }


}
