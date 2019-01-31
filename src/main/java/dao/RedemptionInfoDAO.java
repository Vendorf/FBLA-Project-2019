package dao;

import database.DatabaseManager;
import entities.RedemptionInfo;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RedemptionInfoDAO {
    //TABLE: redemption_codes
    //PROPS: id, book_id, redemption_code, student_id, student_id_assign_date
    //PK: id
    //FK: book_id, student_id

    //*******************************
    //SELECT a RedemptionInfo
    //*******************************
    public static RedemptionInfo searchRedemptionInfo (String redemptionId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, book_id, redemption_code, student_id, student_id_assign_date FROM redemption_codes WHERE id=" + redemptionId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getStudentFromResultSet method and get Student object
            RedemptionInfo redemptionInfo = getRedemptionInfoFromResultSet(rsEmp);

            //Return student object
            return redemptionInfo;
        } catch (SQLException e) {
            //System.out.println("While searching student with " + studentId + " id, an error occurred: " + e);

            //LOG stuff here

            //Return exception
            throw e;
        }
    }

    public static ObservableList<RedemptionInfo> searchRedemptionInfoByStudentId (String studentId) throws SQLException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, book_id, redemption_code, student_id, student_id_assign_date FROM redemption_codes WHERE student_id = " + studentId;



        //Execute SELECT statement
        return doMultipleSelectStatement(selectStmt);
    }

    private static ObservableList<RedemptionInfo> doMultipleSelectStatement(String selectStmt) throws SQLException {
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsRedemptionInfo = DatabaseManager.executeQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<RedemptionInfo> redemptionInfoList = getRedemptionInfoList(rsRedemptionInfo);

            //Return employee object
            return redemptionInfoList;
        } catch (SQLException e) {
            //System.out.println("SQL select operation has been failed: " + e);

            //LOG here

            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Student Object's attributes and return Student object.
    private static RedemptionInfo getRedemptionInfoFromResultSet(ResultSet rs) throws SQLException
    {
        RedemptionInfo redemptionInfo = null;
        if (rs.next()) {
            redemptionInfo = buildRedemptionInfo(rs);
        }
        return redemptionInfo;
    }

    private static RedemptionInfo buildRedemptionInfo(ResultSet rs) throws SQLException {
        RedemptionInfo redemptionInfo = new RedemptionInfo();
        redemptionInfo.setId(rs.getInt("id"));
        redemptionInfo.setBook_id(rs.getInt("book_id"));
        redemptionInfo.setRedemption_code(rs.getString("redemption_code"));
        redemptionInfo.setStudent_id(rs.getInt("student_id"));
        try {
            redemptionInfo.setStudent_id_assign_date(java.sql.Date.valueOf(rs.getString("student_id_assign_date")));
        } catch (IllegalArgumentException e) {
            //LOG
            System.out.println("Error building date");
        }

        return redemptionInfo;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<RedemptionInfo> searchRedemptionInfo () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT id, book_id, redemption_code, student_id, student_id_assign_date FROM redemption_codes";



        //Execute SELECT statement
        return doMultipleSelectStatement(selectStmt);
    }

    //Select * from students operation
    private static ObservableList<RedemptionInfo> getRedemptionInfoList(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<RedemptionInfo> redemptionInfos = FXCollections.observableArrayList();

        while (rs.next()) {
            RedemptionInfo redemptionInfo = buildRedemptionInfo(rs);
            //Add employee to the ObservableList
            redemptionInfos.add(redemptionInfo);
        }
        //return empList (ObservableList of Employees)
        return redemptionInfos;
    }

    public static ObservableList<RedemptionInfo> getUnnassignedCodes() throws SQLException {

        String selectStmt = "SELECT id, book_id, redemption_code, student_id, student_id_assign_date FROM redemption_codes WHERE student_id IS NULL OR student_id = ''";

        return doMultipleSelectStatement(selectStmt);

    }

    public static void assignRedemptionCodes(Student student, Date assignDate, RedemptionInfo... redemptionInfos) throws SQLException { //maybe just do redemptioninfos and date; set it beforehand and then update

        String updateStmt = "UPDATE redemption_codes SET student_id = %d, student_id_assign_date = '%s' WHERE id = %d";

        for(RedemptionInfo redemptionInfo : redemptionInfos){
            try{
                DatabaseManager.executeUpdate(String.format(updateStmt,
                        student.getStudent_id(),
                        assignDate,
                        redemptionInfo.getId()));
            }catch (SQLException e){
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }
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
    public static void insertRedemptionInfo (RedemptionInfo redemptionInfo) throws SQLException {
        //Declare an INSERT statement
        String updateStmt = String.format("INSERT INTO redemption_codes (book_id, redemption_code, student_id, student_id_assign_date) VALUES (%d, '%s', %d, '%s')",
                redemptionInfo.getBook_id(),
                redemptionInfo.getRedemption_code(),
                redemptionInfo.getStudent_id(),
                redemptionInfo.getStudent_id_assign_date());
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
