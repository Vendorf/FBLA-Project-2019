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

public class GradeDAO {
    //TABLE: grade_level
    //PROPS: id, grade
    //PK: id

    //*******************************
    //SELECT a Student
    //*******************************
    public static Grade searchGrade (String gradeId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, grade FROM grade_level WHERE id=" + gradeId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            Grade grade = getGradeFromResultSet(rsEmp);

            //Return student object
            return grade;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static Grade getGradeFromResultSet(ResultSet rs) throws SQLException
    {
        Grade grade = null;
        if (rs.next()) {
            grade = buildGrade(rs);
        }
        return grade;
    }

    private static Grade buildGrade(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getInt("id"));
        grade.setGrade(rs.getString("grade"));

        return grade;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Grade> searchGrades () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, grade FROM grade_level";

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
            ObservableList<Grade> gradesList = getGradesList(rsGrades);

            //Return employee object
            return gradesList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Select * from students operation
    private static ObservableList<Grade> getGradesList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Grade> gradesList = FXCollections.observableArrayList();

        while (rs.next()) {
            Grade grade = buildGrade(rs);
            //Add employee to the ObservableList
            gradesList.add(grade);
        }
        //return empList (ObservableList of Employees)
        return gradesList;
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
    public static void insertGrade (String grade) throws SQLException {
        //Declare an INSERT statement
        String updateStmt =
                "INSERT INTO grade_level (grade) VALUES (" + grade + ")";
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
