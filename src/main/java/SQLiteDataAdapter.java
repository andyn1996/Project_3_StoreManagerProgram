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
            Statement stmt = conn.createStatement();
            ProductModel p = loadProduct(product.mProductID);
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Products WHERE ProductID = " + product.mProductID);
            }
            String sql = "INSERT INTO Products(ProductID, Name, ExpirationDate, Price, TaxRate," +
                    " Quantity, Supplier, ManufacturedDate) VALUES " + product;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed")) {
                return PRODUCT_SAVE_FAILED;
            }
        }

        return PRODUCT_SAVE_OK;
    }

    @Override
    public PurchaseHistoryModel loadPurchaseHistory(int id) {
        PurchaseHistoryModel res = new PurchaseHistoryModel();
        try {
            String sql = "SELECT * FROM Purchases WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("PurchaseID");
                purchase.mProductID = rs.getInt("ProductID");
                purchase.mPrice = rs.getDouble("Price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mCost = rs.getDouble("Cost");
                purchase.mTax = rs.getDouble("Tax");
                purchase.mTotal = rs.getDouble("Total");
                purchase.mDate = rs.getString("Date");

                res.purchases.add(purchase);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
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
            Statement stmt = conn.createStatement();
            CustomerModel c = loadCustomer(customer.mCustomerID);

            if (c != null) {
                stmt.executeUpdate("DELETE FROM Customers WHERE CustomerID = " + customer.mCustomerID);
            }

            String sql = "INSERT INTO Customers(CustomerID, Name, Address, Phone) VALUES " + customer;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        }
        catch (Exception e) {

            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed")) {
                return CUSTOMER_SAVE_FAILED;
            }
        }

        return CUSTOMER_SAVE_OK;
    }

    @Override
    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = new PurchaseModel();

        try {
            String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + purchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            purchase.mPurchaseID = rs.getInt("PurchaseID");
            purchase.mProductID = rs.getInt("ProductID");
            purchase.mCustomerID = rs.getInt("CustomerID");
            purchase.mQuantity = rs.getDouble("Quantity");
            purchase.mDate = rs.getString("Date");
            purchase.mCost = rs.getDouble("Cost");
            purchase.mTax = rs.getDouble("Tax");
            purchase.mPrice = rs.getDouble("Price");
            purchase.mTotal = rs.getDouble("Total");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    @Override
    public int savePurchase(PurchaseModel purchase) {
        try {
            Statement stmt = conn.createStatement();
            PurchaseModel p = loadPurchase(purchase.mPurchaseID);

            if (p != null) {
                stmt.executeUpdate("DELETE FROM Purchases WHERE PurchaseID = " + purchase.mPurchaseID);
            }

            String sql = "INSERT INTO Purchases VALUES " + purchase;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_SAVE_FAILED;
        }

        return PURCHASE_SAVE_OK;

    }

    public UserModel loadUser(String username) {
        UserModel user = null;

        try {
            String sql = "SELECT * FROM Users WHERE Username = \"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Fullname");
                user.mUserType = rs.getInt("Usertype");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}






