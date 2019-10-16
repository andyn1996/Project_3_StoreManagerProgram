import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {

    public JFrame view;


    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField textCustomerID = new JTextField(15);
    public JTextField textName = new JTextField(15);
    public JTextField textAddress = new JTextField(15);
    public JTextField textPhone = new JTextField(15);

    public AddCustomerUI() {

        this.view = new JFrame();

        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

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

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());
        btnCancel.addActionListener(new CancelButtonListener());
    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();

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
                JOptionPane.showMessageDialog(null, "Phone cannot be empty!");
                return;
            }

            customer.mPhone = phone;

            switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
                case SQLiteDataAdapter.CUSTOMER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
                    break;
                default:
                    view.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
            }
        }
    }

    class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "You clicked on the cancel button!");
            view.setVisible(false);
        }
    }


}
