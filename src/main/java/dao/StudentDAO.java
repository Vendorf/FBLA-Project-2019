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
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StudentDAO {
    //TABLE: students
    //PROPS: id, student_id, first_name, last_name, dob, gender_id, grade_id
    //PK: id
    //FK: gender_id, grade_id



    //*******************************
    //SELECT a Student
    //*******************************
    public static Student searchStudents (String studentId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT students.id, student_id, first_name, last_name, dob, gender_id, grade_id, genders.gender, grade_level.grade FROM students INNER JOIN genders on genders.id = gender_id INNER JOIN grade_level on grade_level.id = grade_id WHERE student_id=" + studentId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            Student student = getStudentFromResultSet(rsEmp);

            //Return student object
            return student;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static Student getStudentFromResultSet(ResultSet rs) throws SQLException
    {
        Student student = null;
        if (rs.next()) {
            student = buildStudent(rs);
        }
        return student;
    }

    private static Student buildStudent(ResultSet rs) throws SQLException {
        Student student;
        Gender gender;
        Grade grade;
        student = new Student();
        gender = new Gender();
        grade = new Grade();
        student.setId(rs.getInt("id"));
        student.setStudent_id(rs.getInt("student_id"));
        student.setFirst_name(rs.getString("first_name"));
        student.setLast_name(rs.getString("last_name"));
        try {
            student.setDob(java.sql.Date.valueOf(rs.getString("dob")));
        } catch (IllegalArgumentException e) {
            //LOG
            System.out.println("bullshit happened");
        }
        student.setGender_id(rs.getInt("gender_id"));
        student.setGrade_id(rs.getInt("grade_id"));

        //Move the below into their own DAOs and get it from that
        //This would also involve altering the SELECT statements for the search; it would just search student without any joins
        //Then GenderDAO and GradeDAO would search their own, and you would link them from there as with the code below

        gender.setId(rs.getInt("gender_id"));
        gender.setGender(rs.getString("gender"));
        student.setGender(gender);
        grade.setId(rs.getInt("grade_id"));
        grade.setGrade(rs.getString("grade"));
        student.setGrade(grade);


        return student;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Student> searchStudents () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT students.id, student_id, first_name, last_name, dob, gender_id, grade_id, genders.gender, grade_level.grade FROM students INNER JOIN genders on genders.id = gender_id INNER JOIN grade_level on grade_level.id = grade_id";

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
            ResultSet rsStudents = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Student> studentList = getStudentList(rsStudents);

            //Return employee object
            return studentList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Select * from students operation
    private static ObservableList<Student> getStudentList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        while (rs.next()) {
            Student student = buildStudent(rs);
            //Add employee to the ObservableList
            studentList.add(student);
        }
        //return empList (ObservableList of Employees)
        return studentList;
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
    public static void insertStudent (int studentId, String firstName, String lastName, Date dob, int genderId, int gradeId) throws SQLException, ClassNotFoundException {
        //Declare an INSERT statement
        String updateStmt =
                        "INSERT INTO students\n" +
                        "(student_id, first_name, last_name, dob, gender_id, grade_id)\n" +
                        "VALUES\n" +
                        "(" + studentId + ", '" + firstName + "', '"+lastName+"','" + dob + "'," + genderId + ", " + gradeId + ");";

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
