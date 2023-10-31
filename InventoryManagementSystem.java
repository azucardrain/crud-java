import java.sql.*;
import java.util.Scanner;
//lol
public class InventoryManagementSystem {
        private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        public static void main(String[] args) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter 'insert' to add a product, 'delete' to remove a product, 'view' to view all products and 'update' to update a product:");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("insert")) {
                    System.out.println("Enter ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter stock:");
                    int stock = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter price:");
                    String price = scanner.nextLine();
                    insertData(connection, id, name, stock, price);
                } else if (action.equalsIgnoreCase("delete")) {
                    System.out.println("Enter ID of the product to delete:");
                    int id = Integer.parseInt(scanner.nextLine());
                    deleteData(connection, id);
                }
                else if (action.equalsIgnoreCase("update")){
                    System.out.println("Enter ID of the product to edit:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println(("enter new stock"));
                    int stock = Integer.parseInt(scanner.nextLine());
                    System.out.println(("enter new price"));
                    String price = scanner.nextLine();
                    updateData(connection, id,stock, price);
                }

                retrieveData(connection);
                connection.close();
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    private static void insertData(Connection connection, int id, String name, int stock, String price) throws SQLException {
        String insertQuery = "INSERT INTO PRODUCTS (ID, NAME, STOCK, PRICE) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, stock);
        preparedStatement.setString(4, price);
        preparedStatement.executeUpdate();
    }

    private static void updateData(Connection connection, int id, int newStock, String newPrice) throws SQLException {
        String updateQuery = "UPDATE PRODUCTS SET STOCK = ?, PRICE = ? WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setInt(1, newStock);
        preparedStatement.setString(2, newPrice);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteQuery = "DELETE FROM PRODUCTS WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    private static void retrieveData(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM PRODUCTS";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);
        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int stock = resultSet.getInt("STOCK");
            String price = resultSet.getString("PRICE");
            System.out.println("ID: " + id + ", NAME: " + name + ", STOCK: " + stock + ", PRICE: " + price);
        }
    }
}