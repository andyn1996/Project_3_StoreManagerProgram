import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField textProductID = new JTextField(15);
    public JTextField textName = new JTextField(15);
    public JTextField textExpirationDate = new JTextField(15);
    public JTextField textPrice = new JTextField(15);
    public JTextField textTaxRate= new JTextField(15);
    public JTextField textQuantity = new JTextField(15);
    public JTextField textSupplier = new JTextField(15);
    public JTextField textManufacturedDate = new JTextField(15);

    public AddProductUI() {

        this.view = new JFrame();

        view.setTitle("Add Product");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));


        String[] labels = {"ProductID ", "Name ", "ExpirationDate ", "Price ",
                "TaxRate ", "Quantity ", "Supplier ", "ManufacturedDate "};


        JPanel line1 = new JPanel(new FlowLayout());
//        line1.setPreferredSize(new Dimension(220, 50));
        JLabel label = new JLabel("ProductID", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line1.add(label);
        line1.add(textProductID);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
//        line2.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Name ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line2.add(label);
        line2.add(textName);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
//        line3.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Expiration Date (01-01-1900)", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line3.add(label);
        line3.add(textExpirationDate);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
//        line4.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Price ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line4.add(label);
        line4.add(textPrice);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
//        line5.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Tax Rate ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line5.add(label);
        line5.add(textTaxRate);
        line5.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line5);

        JPanel line6 = new JPanel(new FlowLayout());
//        line6.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Quantity ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line6.add(label);
        line6.add(textQuantity);
        line6.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line6);

        JPanel line7 = new JPanel(new FlowLayout());
//        line7.setPreferredSize(new Dimension(220, 50));
        label = new JLabel("Supplier ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line7.add(label);
        line7.add(textSupplier);
        line7.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line7);

        JPanel line8 = new JPanel(new FlowLayout());
//        line2.setPreferredSize(new Dimension(200, 50));
        label = new JLabel("Manufactured Date (01-01-1900)", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220,50));
        line8.add(label);
        line8.add(textManufacturedDate);
        line8.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line8);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());
        btnCancel.addActionListener(new CancelButtonListener());


        //this.pack();
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);

    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();

            String id = textProductID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }

            try {
                product.mProductID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }

            String name = textName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product name cannot be empty!");
                return;
            }

            product.mName = name;

            String expirationDate = textExpirationDate.getText();
            product.mExpirationDate = expirationDate;

            String price = textPrice.getText();
            try {
                product.mPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }

            String taxRate = textPrice.getText();
            try {
                product.mTaxRate = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tax Rate is invalid!");
                return;
            }

            String quantity = textQuantity.getText();
            try {
                product.mQuantity = Double.parseDouble(quantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            String supplierName = textSupplier.getText();
            if (supplierName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Supplier name cannot be empty!");
                return;
            }

            product.mSupplier = supplierName;

            String manufacturedDate = textManufacturedDate.getText();
            product.mManufacturedDate = manufacturedDate;


            switch (StoreManager.getInstance().getDataAdapter().saveProduct(product)) {
                case SQLiteDataAdapter.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
                    break;
                default:
                    view.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Product added successfully!" + product);
            }
        }
    }

    class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "You click on Cancel button!!!");
            view.setVisible(false);
        }
    }




}