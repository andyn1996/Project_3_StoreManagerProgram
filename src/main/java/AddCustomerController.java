

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerController {


    public AddCustomerUI view;
    public SQLiteDataAdapter adapter;

    public AddCustomerController(AddCustomerUI view, SQLiteDataAdapter adapter)   {
        this.view = view;
        this.adapter = adapter;

        this.view.btnAdd.addActionListener(new AddButtonListener());
        this.view.btnCancel.addActionListener(new CancelButtonListerner());

    }


    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            CustomerModel customer = new CustomerModel();

            String id = AddCustomerController.this.view.textCustomerID.getText();

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

            String name = AddCustomerController.this.view.textName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }

            customer.mName = name;

            String address = AddCustomerController.this.view.textAddress.getText();
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "Address cannot be empty!");
                return;
            }

            customer.mAddress = address;

            String phone = AddCustomerController.this.view.textPhone.getText();
            if (phone.length() == 0) {
                JOptionPane.showMessageDialog(null, "Phone cannot be empty!");
                return;
            }

            customer.mPhone = phone;

            switch (adapter.saveCustomer(customer)) {
                case SQLiteDataAdapter.CUSTOMER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
                default:
                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
            }
        }
    }


    class CancelButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "Canceled Successfully");
        }
    }

}

