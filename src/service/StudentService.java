package service;

import bean.Student;
import bean.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import utility.dataHandler.DataEncryption;
import utility.dataHandler.UtilityMethod;
import utility.dbConnection.DBConnection;
import utility.popUp.AlertPopUp;
import utility.query.StudentQuery;
import utility.query.UserQuery;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentService {
    private Connection connection;
    public StudentService(){
        connection = DBConnection.getDBConnection();
    }
    public ObservableList<Student> loadAllStudentData() {
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(StudentQuery.LOAD_ALL_STUDENT_DATA);
            while (resultSet.next()) {
                InputStream inputStream = resultSet.getBinaryStream(5);
                studentObservableList.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), UtilityMethod.convertInputStreamToImage(inputStream)));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return studentObservableList;
    }

    public Student loadSpecificStudent(String nic) {
        Student student = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StudentQuery.LOAD_SPECIFIC_STUDENT_DATA);
            preparedStatement.setString(1, nic);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InputStream inputStream = resultSet.getBinaryStream(5);
                student = new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), UtilityMethod.convertInputStreamToImage(inputStream));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return student;
    }

    public boolean insertStudentData(Student student) {
        PreparedStatement psStudent = null;
        try {

            psStudent = connection.prepareStatement(StudentQuery.INSERT_STUDENT_DATA);

            psStudent.setString(1, student.getsNIC());
            psStudent.setString(2, student.getsName());
            psStudent.setString(3, student.getsAddress());
            psStudent.setInt(4, student.getsPhone());
            psStudent.setBinaryStream(5, UtilityMethod.convertImageToInputStream(student.getsImage()));

            psStudent.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean updateStudentData(Student student) {
        PreparedStatement psStudent = null;
        try {

            psStudent = connection.prepareStatement(StudentQuery.UPDATE_STUDENT_DATA);

            psStudent.setString(1, student.getsName());
            psStudent.setString(2, student.getsAddress());
            psStudent.setInt(3, student.getsPhone());
            psStudent.setBinaryStream(4, UtilityMethod.convertImageToInputStream(student.getsImage()));
            psStudent.setString(5, student.getsNIC());

            psStudent.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean deleteStudentData(String nic) {
        PreparedStatement psStudent = null;
        try {

            psStudent = connection.prepareStatement(StudentQuery.DELETE_STUDENT_DATA);

            psStudent.setString(1, nic);
            psStudent.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public SortedList<Student> searchTable(TextField searchTextField) {
        //Retreiving all data from database
        ObservableList<Student> studentData = loadAllStudentData();
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Student> filteredData = new FilteredList<>(studentData, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                //if filter text is empty display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                 if (student.getsNIC().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //return if filter matches data
                    return true;
                } else if (student.getsName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     //return if filter matches data
                     return true;
                 }else if (student.getsPhone().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     //return if filter matches data
                     return true;
                 } else {
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Student> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
