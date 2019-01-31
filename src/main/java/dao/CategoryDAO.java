package dao;

import database.DatabaseManager;
import entities.Category;
import entities.Gender;
import entities.Grade;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {
    //TABLE: categories
    //PROPS: id, category
    //PK: id


    //*******************************
    //SELECT a Student
    //*******************************
    public static Category searchCategories (String categoryId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, category FROM categories WHERE id=" + categoryId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            Category category = getCategoryFromResultSet(rsEmp);

            //Return student object
            return category;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static Category getCategoryFromResultSet(ResultSet rs) throws SQLException
    {
        Category category = null;
        if (rs.next()) {
            category = buildCategory(rs);
        }
        return category;
    }

    private static Category buildCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setCategory(rs.getString("category"));

        return category;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Category> searchCategories () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, category FROM categories";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsCategories = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Category> categoriesList = getCategoriesList(rsCategories);

            //Return employee object
            return categoriesList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Select * from students operation
    private static ObservableList<Category> getCategoriesList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Category> categories = FXCollections.observableArrayList();

        while (rs.next()) {
            Category category = buildCategory(rs);
            //Add employee to the ObservableList
            categories.add(category);
        }
        //return empList (ObservableList of Employees)
        return categories;
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
    public static void insertCategory (String category) throws SQLException {
        //Declare an INSERT statement
        String updateStmt =
                "INSERT INTO categories (category) VALUES (" + category + ")";
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
