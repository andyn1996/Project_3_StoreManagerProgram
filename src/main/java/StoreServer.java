import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "/Users/andyni/Desktop/COMP3700Store.db";

    public static void main(String[] args) {

        HashMap<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();

        int totalActiveUsers = 0;

        int port = 10007;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect(dbfile);

            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println("GET product with id = " + msg.data);
                    ProductModel p = adapter.loadProduct(Integer.parseInt(msg.data));
                    System.out.println(p.toString());
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PRODUCT) {
                    ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                    System.out.println("PUT command with Product = " + p);
                    int res = adapter.saveProduct(p);
                    if (res == IDataAdapter.PRODUCT_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_CUSTOMER) {
                    System.out.println("GET customer with id = " + msg.data);
                    CustomerModel customer = adapter.loadCustomer(Integer.parseInt(msg.data));
                    System.out.println(customer.toString());
                    if (customer == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.data = gson.toJson(customer);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_CUSTOMER) {
                    CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
                    System.out.println("PUT command with Customer = " + customer);
                    int res = adapter.saveCustomer(customer);
                    if (res == IDataAdapter.CUSTOMER_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_PURCHASE) {
                    System.out.println("GET purchase with id = " + msg.data);
                    PurchaseModel purchase = adapter.loadPurchase(Integer.parseInt(msg.data));
                    System.out.println(purchase.toString());
                    if (purchase == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK;
                        msg.data = gson.toJson(purchase);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PURCHASE) {
                    PurchaseModel purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    System.out.println("PUT command with purchase = " + purchase);
                    int res = adapter.savePurchase(purchase);
                    if (res == IDataAdapter.PURCHASE_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.LOGIN) {
                    UserModel u = gson.fromJson(msg.data, UserModel.class);
                    UserModel user = adapter.loadUser(u.mUsername);
                    System.out.println("LOGIN command with User = " + user);
                    if (user != null && user.mPassword.equals(u.mPassword)) {
                        msg.code = MessageModel.OPERATION_OK;
                        totalActiveUsers++;
                        int accessToken = totalActiveUsers;
                        msg.ssid = accessToken;
                        msg.data = gson.toJson(user, UserModel.class);
                        activeUsers.put(accessToken, user);
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));  // answer login command!
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//                String command = in.nextLine();
//                if (command.equals("GET")) {
//                    String str = in.nextLine();
//                    System.out.println("GET product with id = " + str);
//                    int productID = Integer.parseInt(str);
//
//                    Connection conn = null;
//                    try {
//                        String url = "jdbc:sqlite:" + dbfile;
//                        conn = DriverManager.getConnection(url);
//
//                        String sql = "SELECT * FROM Products WHERE ProductID = " + productID;
//                        Statement stmt = conn.createStatement();
//                        ResultSet rs = stmt.executeQuery(sql);
//
//                        if (rs.next()) {
//                            out.println(rs.getString("Name")); // send back product name!
//                            out.println(rs.getString("ExpirationDate"));
//                            out.println(rs.getString("Price"));
//                            out.println(rs.getString("TaxRate"));
//                            out.println(rs.getString("Quantity"));
//                            out.println(rs.getString("Supplier"));
//                            out.println(rs.getString("ManufacturedDate")); // send back product price!
//                        }
//                        else {
//                            out.println("null");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    conn.close();
//                }
//
//                if (command.equals("PUT")) {
//                    String id = in.nextLine();  // read all information from client
//                    String name = in.nextLine();
//                    String expirationDate = in.nextLine();
//                    String price = in.nextLine();
//                    String taxRate = in.nextLine();
//                    String quantity = in.nextLine();
//                    String supplier = in.nextLine();
//                    String manufacturedDate = in.nextLine();
//
//                    System.out.println("PUT command with ProductID = " + id);
//
//                    Connection conn = null;
//                    try {
//                        String url = "jdbc:sqlite:" + dbfile;
//                        conn = DriverManager.getConnection(url);
//
//                        String sql = "SELECT * FROM Products WHERE ProductID = " + id;
//                        Statement stmt = conn.createStatement();
//                        ResultSet rs = stmt.executeQuery(sql);
//
//                        if (rs.next()) {
//                            rs.close();
//                            stmt.execute("DELETE FROM Products WHERE ProductID = " + id);
//                        }
//
//                        sql = "INSERT INTO Products VALUES (" + id + ",\"" + name + "\"," + "\"" + expirationDate + "\","
//                                + price + "," + taxRate + "," +  quantity +  ",\"" + supplier + "\"" +  ",\"" + manufacturedDate + "\"" + ")";
//                        System.out.println("SQL for PUT: " + sql);
//                        stmt.execute(sql);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    conn.close();
//
//
//                } else {
//                    out.println(0); // logout unsuccessful!
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}