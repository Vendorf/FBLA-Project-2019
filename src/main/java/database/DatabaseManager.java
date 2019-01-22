package database;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DatabaseManager {


    private static String dbUrl = "jdbc:sqlite:C:/sqlite/db/testDB.db";
    private static Connection connection = null;


    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
    }

    public static void disconnect() throws SQLException {
        if(connection != null){
            connection.close();
        }
    }

//    /**
//     * Connect to a sample database
//     *
//     * @param fileName the database file name
//     */
//    public static void createNewDatabase(String fileName) {
//
//        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName + ".db";
//
//        try (Connection conn = DriverManager.getConnection(url)) {
//            if (conn != null) {
//                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }


    public static ResultSet executeQuery(String query) throws  SQLException{

        Statement queryStatement = null;
        ResultSet resultSet = null;
        CachedRowSet crs = null;
        try {
            connect();
            queryStatement = connection.createStatement();
            resultSet = queryStatement.executeQuery(query);
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(resultSet);

        } catch(SQLException e){
            //LOG stuff
            throw e;
        }
        finally{
            if(queryStatement != null){
                queryStatement.close();
            }
            if(resultSet != null){
                resultSet.close();
            }
            disconnect();
        }
        return crs;
    }

    public static void executeUpdate(String updateQuery) throws SQLException{


    }

}
