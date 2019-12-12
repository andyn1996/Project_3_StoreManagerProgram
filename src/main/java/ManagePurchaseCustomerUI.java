import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ManagePurchaseCustomerUI {

    public UserModel user = null;

    public final static double TAX_RATE = 0.095;

    public JFrame view;

    public JButton btnSave = new JButton("Save Purchase");
    //public JButton btnCancel = new JButton("Cancel");
    //public JButton btnLoad = new JButton("Load Purchase");

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    ProductModel product;
    CustomerModel customer;
    PurchaseModel purchase;

    public ManagePurchaseCustomerUI(UserModel user) {
        this.user = user;
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Manage Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());

        //line.add(btnLoad);
        line.add(btnSave);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("PurchaseID "));
        line.add(txtPurchaseID);
        line.add(labDate);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID "));
        line.add(txtCustomerID);
        txtCustomerID.setText(Integer.toString(user.mCustomerID));
        txtCustomerID.setEditable(false);
        line.add(labCustomerName);
        labCustomerName.setText(user.mFullname);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID "));
        line.add(txtProductID);
        line.add(labProductName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity "));
        line.add(txtQuantity);
        line.add(labPrice);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labCost);
        line.add(labTax);
        line.add(labTotalCost);
        view.getContentPane().add(line);

        txtProductID.getDocument().addDocumentListener(new ProductChangeListener());
        txtCustomerID.getDocument().addDocumentListener(new CustomerChangeListener());
        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());

        btnSave.addActionListener(new SaveButtonListener());
        //btnLoad.addActionListener(new LoadButtonListener());
    }

    public void run() {
        purchase = new PurchaseModel();
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText("Date of purchase: " + Calendar.getInstance().getTime());
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private class ProductChangeListener implements DocumentListener {

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
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");

                return;
            }

            labProductName.setText("Product Name: " + product.mName);
            labPrice.setText("Product Price: " + product.mPrice);
            purchase.mPrice = product.mPrice;
        }
    }

//    private class ProductIDFocusListener implements FocusListener {
//        @Override
//        public void focusGained(FocusEvent focusEvent) {
//
//        }
//
//        @Override
//        public void focusLost(FocusEvent focusEvent) {
//            process();
//        }
//
//        private void process() {
//            String s = txtProductID.getText();
//
//            if (s.length() == 0) {
//                labProductName.setText("Product Name: [not specified!]");
//                return;
//            }
//
//            System.out.println("ProductID = " + s);
//
//            try {
//                purchase.mProductID = Integer.parseInt(s);
//
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: Invalid ProductID", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
//
//            if (product == null) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                labProductName.setText("Product Name: ");
//
//                return;
//            }
//
//            labProductName.setText("Product Name: " + product.mName);
//            labPrice.setText("Product Price: " + product.mPrice);
//            purchase.mPrice = product.mPrice;
//        }
//    }

//    private class CustomerIDFocusListener implements FocusListener {
//        @Override
//        public void focusGained(FocusEvent focusEvent) {
//
//        }
//
//        @Override
//        public void focusLost(FocusEvent focusEvent) {
//            process();
//        }
//
//        private void process() {
//            String s = txtCustomerID.getText();
//
//            if (s.length() == 0) {
//                labCustomerName.setText("Customer Name: [not specified!]");
//                return;
//            }
//
//            System.out.println("CustomerID = " + s);
//
//            try {
//                purchase.mCustomerID = Integer.parseInt(s);
//
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: Invalid CustomerID", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);
//
//            if (customer == null) {
//                JOptionPane.showMessageDialog(null,
//                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
//                        JOptionPane.ERROR_MESSAGE);
//                labCustomerName.setText("Customer Name: ");
//
//                return;
//            }
//
//            labCustomerName.setText("Customer Name: " + customer.mName);
//
//        }
//
//    }

    private class CustomerChangeListener implements DocumentListener {

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
            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");

                return;
            }

            labCustomerName.setText("Customer Name: " + customer.mName);

        }
    }

    private class QuantityChangeListener implements DocumentListener {

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
            String s = txtQuantity.getText();

            if (s.length() == 0) {
                labCost.setText("No Quantity");
                labTax.setText("No Quantity");
                labTotalCost.setText("No Quantity");
                return;
            }

            System.out.println("Quantity = " + s);

            try {
                purchase.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid Quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please Enter a Valid Quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (product.mQuantity < purchase.mQuantity) {
                JOptionPane.showMessageDialog(null,
                        "Error: Store does not have " + purchase.mQuantity + " of this item in store!", "Error Message",
                        JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            DecimalFormat df = new DecimalFormat("#.00");

            purchase.mCost = round(product.mPrice * purchase.mQuantity,2);
            purchase.mTax = round(product.mPrice * purchase.mQuantity * TAX_RATE, 2);
            purchase.mTotal = round(purchase.mCost + purchase.mTax, 2);

            labCost.setText("Cost: $" + df.format(purchase.mCost));
            labTax.setText("Tax: $" + df.format(purchase.mTax));
            labTotalCost.setText("Total Cost: $" + df.format(purchase.mTotal));


        }
    }

    private void outputReceiptToFile() throws FileNotFoundException {
        TextReceiptBuilder text = new TextReceiptBuilder();
        text.appendHeader("Welcome to Bob's Store\n");
        text.appendCustomer(customer);
        text.appendProduct(product);
        text.appendPurchase(purchase);
        text.appendFooter("Thank You For Shopping With Us");
        try (PrintWriter out = new PrintWriter("Store Manager Receipt On" + purchase.mPurchaseID)) {
            out.println(text.sb);
        }
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            purchase = StoreManager.getInstance().getDataAdapter().loadPurchase(purchase.mPurchaseID);

            if (purchase == null) {
                JOptionPane.showMessageDialog(null, "Purchase does not exists!");
            } else {
                txtProductID.setText(Integer.toString(purchase.mProductID));
                txtCustomerID.setText(Integer.toString(purchase.mCustomerID));
                txtQuantity.setText(Double.toString(purchase.mQuantity));
            }
        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String id = txtPurchaseID.getText();
            purchase.mCustomerID = user.mCustomerID;


            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }


            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
                case SQLiteDataAdapter.PURCHASE_SAVE_FAILED:
                    JOptionPane.showMessageDialog(null, "Purchase NOT added successfully!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Purchase added successfully!");
                    try {
                        outputReceiptToFile();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

//    class AddButtonListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//
//            String id = txtPurchaseID.getText();
//
//            if (id.length() == 0) {
//                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
//                return;
//            }
//
//            try {
//                purchase.mPurchaseID = Integer.parseInt(id);
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
//                return;
//            }
//
//
//            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
//                case SQLiteDataAdapter.PURCHASE_SAVE_FAILED:
//                    JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate Purchase ID!");
//                    break;
//                default:
//                    view.setVisible(false);
//                    JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
//                    try {
//                        outputReceiptToFile();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }


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
//}
//    class LoadButtonListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            PurchaseModel purchase = new PurchaseModel();
//            Gson gson = new Gson();
//            String id = txtPurchaseID.getText();
//
//            if (id.length() == 0) {
//                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
//                return;
//            }
//
//            try {
//                purchase.mPurchaseID = Integer.parseInt(id);
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(null, "Purchase ID is invalid");
//                return;
//            }
//
//            try {
//                Socket link = new Socket("localhost", 10007);
//                Scanner input = new Scanner(link.getInputStream());
//                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
//
//                MessageModel msg = new MessageModel();
//                msg.code = MessageModel.GET_PURCHASE;
//                msg.data = Integer.toString(purchase.mPurchaseID);
//                output.println(gson.toJson(msg)); // Send to Server
//
//                msg = gson.fromJson(input.nextLine(), MessageModel.class);
//
//                if (msg.code == MessageModel.OPERATION_FAILED) {
//                    JOptionPane.showMessageDialog(null, "Purchase Does Not Exists!");
//                } else {
//                    purchase = gson.fromJson(msg.data, PurchaseModel.class);
//                    txtProductID.setText(Integer.toString(purchase.mProductID));
//                    txtCustomerID.setText(Integer.toString(purchase.mCustomerID));
//                    txtQuantity.setText(Double.toString(purchase.mQuantity));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

