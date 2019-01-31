package dao;

import database.DatabaseManager;
import entities.Book;
import entities.Category;
import entities.RedemptionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCatalogDAO {
    //TABLE: book_catalog
    //PROPS: id, catalog_id, name, author, publication_year
    //PK: id
    //FK: category_id

    //*******************************
    //SELECT a Book
    //*******************************
    public static Book searchBookCatalog (String bookId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, category_id, name, author, publication_year FROM book_catalog WHERE id=" + bookId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            Book book = getBookFromResultSet(rsEmp);

            //Return student object
            return book;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static Book getBookFromResultSet(ResultSet rs) throws SQLException
    {
        Book book = null;
        if (rs.next()) {
            book = buildBook(rs);
        }
        return book;
    }

    private static Book buildBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        Category category = new Category();
        book.setId(rs.getInt("id"));
        book.setCategory_id(rs.getInt("category_id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setPublication_year(rs.getInt("publication_year"));

        category = CategoryDAO.searchCategories(rs.getString("category_id"));
        book.setCategory(category);

        return book;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Book> searchBookCatalog () throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, category_id, name, author, publication_year FROM book_catalog";



        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsBook = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Book> bookList = getBookList(rsBook);

            //Return employee object
            return bookList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Select * from students operation
    private static ObservableList<Book> getBookList(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Book> books = FXCollections.observableArrayList();

        while (rs.next()) {
            Book book = buildBook(rs);
            //Add employee to the ObservableList
            books.add(book);
        }
        //return empList (ObservableList of Employees)
        return books;
    }

    public static void updateBook (Book book) throws SQLException {

        //check if unique values still unique first by SELECT statement
        //check if grade and gender valid

        String updateStmt = String.format("UPDATE book_catalog SET category_id = %d, name = '%s', author = '%s', publication_year = %d WHERE id = %d",
                book.getCategory_id(),
                book.getName(),
                book.getAuthor(),
                book.getPublication_year(),
                book.getId());

        try{
            DatabaseManager.executeUpdate(updateStmt);
        }catch (SQLException e){
            //LOG
            System.out.println("error on update with statement:\n" + updateStmt);
            throw e;
        }
    }

//    //*************************************
//    //UPDATE an employee's email address
//    //*************************************
//    public static void updateEmpEmail (String empId, String empEmail) throws SQLException, ClassNotFoundException {
//        //Declare a UPDATE statement
//        String updateStmt =
//                "BEGIN\n" +
//                        "   UPDATE employees\n" +
//                        "      SET EMAIL = '" + empEmail + "'\n" +
//                        "    WHERE EMPLOYEE_ID = " + empId + ";\n" +
//                        "   COMMIT;\n" +
//                        "END;";
//
//        //Execute UPDATE operation
//        try {
//            DBUtil.dbExecuteUpdate(updateStmt);
//        } catch (SQLException e) {
//            System.out.print("Error occurred while UPDATE Operation: " + e);
//            throw e;
//        }
//    }

//    //*************************************
//    //DELETE an employee
//    //*************************************
//    public static void deleteEmpWithId (String empId) throws SQLException, ClassNotFoundException {
//        //Declare a DELETE statement
//        String updateStmt =
//                "BEGIN\n" +
//                        "   DELETE FROM employees\n" +
//                        "         WHERE employee_id ="+ empId +";\n" +
//                        "   COMMIT;\n" +
//                        "END;";
//
//        //Execute UPDATE operation
//        try {
//            DBUtil.dbExecuteUpdate(updateStmt);
//        } catch (SQLException e) {
//            System.out.print("Error occurred while DELETE Operation: " + e);
//            throw e;
//        }
//    }

    //*************************************
    //INSERT an employee
    //*************************************
    public static void insertBook (Book book) throws SQLException {
        //Declare an INSERT statement
        String updateStmt = String.format("INSERT INTO book_catalog (category_id, name, author, publication_year) VALUES (%d, '%s', '%s', %d)",
                book.getCategory_id(),
                book.getName(),
                book.getAuthor(),
                book.getPublication_year());
        //Execute INSERT operation
        try {
            DatabaseManager.executeUpdate(updateStmt);
        } catch (SQLException e) {
            //System.out.print("Error occurred while INSERT Operation: " + e);

            //LOG HERE

            throw e;
        }
    }

}
