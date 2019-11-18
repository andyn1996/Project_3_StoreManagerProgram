
public interface IDataAdapter {



    public static final int CONNECTION_OPEN_OK = 100;
    public static final int CONNECTION_OPEN_FAILED = 101;

    public static final int CONNECTION_CLOSE_OK = 200;
    public static final int CONNECTION_CLOSE_FAILED = 201;

    public static final int PURCHASE_SAVE_OK = 500;
    public static final int PURCHASE_SAVE_FAILED = 501;

    public static final int PRODUCT_SAVE_OK = 0;
    public static final int PRODUCT_SAVE_FAILED = 1;

    public static final int CUSTOMER_SAVE_OK = 0;
    public static final int CUSTOMER_SAVE_FAILED = 1;

    // Connect to the database
    public int connect(String dbfile);

    // Disconnect from the database
    public int disconnect();

    public ProductModel loadProduct(int id);
    public int saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int id);
    public int saveCustomer(CustomerModel customer);

    public PurchaseModel loadPurchase(int id);
    public int savePurchase(PurchaseModel purchase);

    public PurchaseHistoryModel loadPurchaseHistory(int customerID);

    public UserModel loadUser(String username);
    //public int saveUser(UserModel user);


}
