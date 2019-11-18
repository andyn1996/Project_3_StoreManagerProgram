import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageProductUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load Product");
    public JButton btnSave = new JButton("Save Product");

    public JTextField textProductID = new JTextField(15);
    public JTextField textName = new JTextField(15);
    public JTextField textExpirationDate = new JTextField(15);
    public JTextField textPrice = new JTextField(15);
    public JTextField textTaxRate = new JTextField(15);
    public JTextField textQuantity = new JTextField(15);
    public JTextField textSupplier = new JTextField(15);
    public JTextField textManufacturedDate = new JTextField(15);


    public ManageProductUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Manage Products");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        JLabel label = new JLabel("ProductID", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line1.add(label);
        line1.add(textProductID);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        label = new JLabel("Name ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line2.add(label);
        line2.add(textName);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        label = new JLabel("Expiration Date (01-01-1900)", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line3.add(label);
        line3.add(textExpirationDate);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        label = new JLabel("Price ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line4.add(label);
        line4.add(textPrice);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        label = new JLabel("Tax Rate ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line5.add(label);
        line5.add(textTaxRate);
        line5.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line5);

        JPanel line6 = new JPanel(new FlowLayout());
        label = new JLabel("Quantity ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line6.add(label);
        line6.add(textQuantity);
        line6.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line6);

        JPanel line7 = new JPanel(new FlowLayout());
        label = new JLabel("Supplier ", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line7.add(label);
        line7.add(textSupplier);
        line7.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line7);

        JPanel line8 = new JPanel(new FlowLayout());
        label = new JLabel("Manufactured Date (01-01-1900)", JLabel.TRAILING);
        label.setPreferredSize(new Dimension(220, 50));
        line8.add(label);
        line8.add(textManufacturedDate);
        line8.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.getContentPane().add(line8);


        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            Gson gson = new Gson();
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

            product = StoreManager.getInstance().getDataAdapter().loadProduct(product.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null, "Product NOT exists!");
            } else {
                textName.setText(product.mName);
                textExpirationDate.setText(product.mExpirationDate);
                textPrice.setText(Double.toString(product.mPrice));
                textTaxRate.setText(Double.toString(product.mTaxRate));
                textQuantity.setText(Double.toString(product.mQuantity));
                textSupplier.setText(product.mSupplier);
                textManufacturedDate.setText(product.mManufacturedDate);
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

class SaveButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ProductModel product = new ProductModel();
        Gson gson = new Gson();
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

        String taxRate = textTaxRate.getText();
        try {
            product.mTaxRate = Double.parseDouble(taxRate);
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

        int result = StoreManager.getInstance().getDataAdapter().saveProduct(product);

        if (result == IDataAdapter.PRODUCT_SAVE_FAILED) {
            JOptionPane.showMessageDialog(null, "Product is NOT saved successfully");
        }
        else {
            JOptionPane.showMessageDialog(null, "Product is saved successfully");
        }

        // all product info is ready! Send to Server!
//
//        try {
//            Socket link = new Socket("localhost", 10007);
//            Scanner input = new Scanner(link.getInputStream());
//            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
//
//            MessageModel msg = new MessageModel();
//            msg.code = MessageModel.PUT_PRODUCT;
//            msg.data = gson.toJson(product);
//            output.println(gson.toJson(msg));
//
//            msg = gson.fromJson(input.nextLine(), MessageModel.class);
//
//            if (msg.code == MessageModel.OPERATION_FAILED) {
//                JOptionPane.showMessageDialog(null, "Product did not save successfully");
//            } else {
//                JOptionPane.showMessageDialog(null, "Product is successfully saved!");
//            }
//
//
//                 Old way to send product information to server
//                output.println("PUT");
//                output.println(product.mProductID);
//                output.println(product.mName);
//                output.println(product.mExpirationDate);
//                output.println(product.mPrice);
//                output.println(product.mTaxRate);
//                output.println(product.mQuantity);
//                output.println(product.mSupplier);
//                output.println(product.mManufacturedDate);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
}
