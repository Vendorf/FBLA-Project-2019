package dao;

import database.DatabaseManager;
import entities.Gender;
import entities.Grade;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderDAO {
    //TABLE: genders
    //PROPS: id, gender
    //PK: id


    //*******************************
    //SELECT a Student
    //*******************************
    public static Gender searchGender (String genderId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, gender FROM genders WHERE id=" + genderId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            Gender gender = getGenderFromResultSet(rsEmp);

            //Return student object
            return gender;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static Gender getGenderFromResultSet(ResultSet rs) throws SQLException
    {
        Gender gender = null;
        if (rs.next()) {
            gender = buildGender(rs);
        }
        return gender;
    }

    private static Gender buildGender(ResultSet rs) throws SQLException {
        Gender gender = new Gender();
        gender.setId(rs.getInt("id"));
        gender.setGender(rs.getString("gender"));

        return gender;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Gender> searchGenders () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, gender FROM genders";

//                "SELECT " +
//                "id," +
//                "student_id," +
//                "first_name," +
//                "last_name," +
//                "dob," +
//                "genders.gender," +
//                "grade_id" +
//                "FROM students";
        //"INNER JOIN genders on genders.id = gender_id;";


//                "SELECT " +
//                "students.id," +
//                "student_id," +
//                "first_name," +
//                "last_name," +
//                "dob," +
//                "genders.gender," +
//                "grade_id" +
//                "FROM students " +
//                "INNER JOIN genders ON genders.id = gender_id";



        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsGrades = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Gender> gendersList = getGendersList(rsGrades);

            //Return employee object
            return gendersList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Select * from students operation
    private static ObservableList<Gender> getGendersList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Gender> gendersList = FXCollections.observableArrayList();

        while (rs.next()) {
            Gender gender = buildGender(rs);
            //Add employee to the ObservableList
            gendersList.add(gender);
        }
        //return empList (ObservableList of Employees)
        return gendersList;
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
    public static void insertGender (String gender) throws SQLException {
        //Declare an INSERT statement
        String updateStmt =
                "INSERT INTO genders (gender) VALUES (" + gender + ")";
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
