import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter {


    Connection conn = null;

    public int connect(String dbfile) {

        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile; //Users/andyni/Desktop/COMP3700Store.db"
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        }

        catch(SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }

        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT * FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mExpirationDate = rs.getString("ExpirationDate");
            product.mPrice = rs.getDouble("Price");
            product.mTaxRate = rs.getDouble("TaxRate");
            product.mQuantity = rs.getDouble("Quantity");
            product.mSupplier = rs.getString("Supplier");
            product.mManufacturedDate = rs.getString("ManufacturedDate");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

    @Override
    public int saveProduct(ProductModel product) {

        try {
            String sql = "INSERT INTO Products(ProductID, Name, ExpirationDate, Price, TaxRate," +
                    " Quantity, Supplier, ManufacturedDate) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        }

        catch (Exception e) {

            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;

        }

        return PRODUCT_SAVED_OK;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT * FROM Customers WHERE CustomerID = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return customer;
    }

    public int saveCustomer(CustomerModel customer) {

        try {
            String sql = "INSERT INTO Customers(CustomerID, Name, Address, Phone) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }

        catch (Exception e) {

            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;

        }

        return CUSTOMER_SAVED_OK;
    }

    @Override
    public PurchaseModel loadPurchase(int id) {
        PurchaseModel purchase = new PurchaseModel();
        return purchase;
    }

    @Override
    public int savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchases VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;

    }
}






