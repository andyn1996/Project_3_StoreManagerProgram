import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllPurchaseHistoryUI {

    public JFrame view;
    //public JList purchases;
    public JTable purchaseTable;

    public UserModel user = null;

//    public JButton btnLoad = new JButton("Load Customer");
//    public JButton btnSave = new JButton("Save Customer");
//
//    public JTextField txtCustomerID = new JTextField(20);
//    public JTextField txtName = new JTextField(20);
//    public JTextField txtPhone = new JTextField(20);
//    public JTextField txtAddress = new JTextField(20);


    public AllPurchaseHistoryUI(UserModel user) {
        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("View Purchase History - Manager View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Purchase History of Store");

        title.setFont (title.getFont().deriveFont (24.0f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        view.getContentPane().add(title);

        PurchaseListModel list = StoreManager.getInstance().getDataAdapter().loadAllPurchases();
//        DefaultListModel<String> data = new DefaultListModel<>();
        DefaultTableModel tableData = new DefaultTableModel();
//        String[] columnNames = {"PurchaseID", "ProductID", "Total"};
//        data.addElement(String.format("PurchaseId: %d, ProductId: %d, Total: %8.2f",
//                purchase.mPurchaseID,
//                purchase.mProductID,
//                purchase.mTotal));

        tableData.addColumn("PurchaseID");
        tableData.addColumn("ProductID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Total");

        for (PurchaseModel purchase : list.purchases) {
            System.out.println("For Loop");
            Object[] row = new Object[tableData.getColumnCount()];
            row[0] = purchase.mPurchaseID;
            row[1] = purchase.mProductID;
            ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            row[2] = product.mName;
            row[3] = purchase.mTotal;
            tableData.addRow(row);
        }
//        purchases = new JList(data);

        purchaseTable = new JTable(tableData);

        JScrollPane scrollableList = new JScrollPane(purchaseTable);

        view.getContentPane().add(scrollableList);

    }

    public void run() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}