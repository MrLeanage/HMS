package service;

import bean.Reservation;
import bean.Room;
import bean.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import utility.dataHandler.UtilityMethod;
import utility.dbConnection.DBConnection;
import utility.popUp.AlertPopUp;
import utility.query.ReservationQuery;
import utility.query.RoomQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationService {
    private Connection connection;
    public ReservationService(){
        connection = DBConnection.getDBConnection();
    }
    public ObservableList<Reservation> loadAllReservationData() {
        ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(ReservationQuery.LOAD_ALL_RESERVATION_DATA);
            RoomService roomService = new RoomService();
            StudentService studentService = new StudentService();
            Student student = new Student();
            Room room = new Room();
            while (resultSet.next()) {
                student = studentService.loadSpecificStudent(resultSet.getString(2));
                room = roomService.loadSpecificRoom(UtilityMethod.addPrefix("Room-",resultSet.getString(3)));
                reservationObservableList.add( new Reservation(resultSet.getString(1), resultSet.getString(2), student.getsName(), resultSet.getString(3), room.getrPrice(), resultSet.getString(4), resultSet.getString(5)));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return reservationObservableList;
    }

    public Reservation loadSpecificReservation(String rRID) {
        Reservation reservation = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ReservationQuery.LOAD_SPECIFIC_RESERVATION_DATA);
            preparedStatement.setString(1, rRID);
            ResultSet resultSet = preparedStatement.executeQuery();
            RoomService roomService = new RoomService();
            StudentService studentService = new StudentService();
            while (resultSet.next()) {
                Student student = studentService.loadSpecificStudent(resultSet.getString(2));
                Room room = roomService.loadSpecificRoom(UtilityMethod.addPrefix("Room-",resultSet.getString(3)));
                reservation = new Reservation(resultSet.getString(1), resultSet.getString(2), student.getsName(), resultSet.getString(3), room.getrPrice(), resultSet.getString(4), resultSet.getString(5));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return reservation;
    }

    public boolean insertReservationData(Reservation reservation) {
        PreparedStatement psReservation = null;
        try {

            psReservation = connection.prepareStatement(ReservationQuery.INSERT_RESERVATION_DATA);

            psReservation.setString(1, reservation.getrRNIC());
            psReservation.setInt(2, UtilityMethod.seperateID(reservation.getrRNumber()));
            psReservation.setString(3, reservation.getrRDate());
            psReservation.setString(4, reservation.getrRStatus());

            psReservation.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean updateReservationData(Reservation reservation) {
        PreparedStatement psReservation = null;
        try {

            psReservation = connection.prepareStatement(ReservationQuery.UPDATE_RESERVATION_DATA);

            psReservation.setString(1, reservation.getrRNIC());
            psReservation.setInt(2, UtilityMethod.seperateID(reservation.getrRNumber()));
            psReservation.setString(3, reservation.getrRDate());
            psReservation.setString(4, reservation.getrRStatus());
            psReservation.setInt(5, UtilityMethod.seperateID(reservation.getrRID()));

            psReservation.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean deleteReservationData(String rRID) {
        PreparedStatement psReservation = null;
        try {

            psReservation = connection.prepareStatement(ReservationQuery.DELETE_RESERVATION_DATA);

            psReservation.setInt(1, UtilityMethod.seperateID(rRID));
            psReservation.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public SortedList<Reservation> searchTable(TextField searchTextField) {
        //Retreiving all data from database
        ObservableList<Reservation> reservationData = loadAllReservationData();
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Reservation> filteredData = new FilteredList<>(reservationData, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                //if filter text is empty display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                 if (reservation.getrRNIC().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //return if filter matches data
                    return true;
                } else if (reservation.getrRName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     //return if filter matches data
                     return true;
                 } else if (reservation.getrRStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     //return if filter matches data
                     return true;
                 } else {
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
