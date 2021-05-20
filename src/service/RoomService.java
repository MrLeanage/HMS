package service;

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
import utility.query.RoomQuery;
import utility.query.StudentQuery;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomService {
    private Connection connection;
    public RoomService(){
        connection = DBConnection.getDBConnection();
    }
    public ObservableList<Room> loadAllRoomData() {
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(RoomQuery.LOAD_ALL_ROOM_DATA);
            while (resultSet.next()) {
                roomObservableList.add(new Room(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getDouble(4), resultSet.getString(5)));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return roomObservableList;
    }

    public Room loadSpecificRoom(String rID) {
        Room room = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(RoomQuery.LOAD_SPECIFIC_ROOM_DATA);
            preparedStatement.setInt(1, UtilityMethod.seperateID(rID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                room = new Room(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5));
            }

        } catch (SQLException sqlException) {
            AlertPopUp.sqlQueryError(sqlException);
        }
        return room;
    }

    public boolean insertRoomData(Room room) {
        PreparedStatement psRoom = null;
        try {

            psRoom = connection.prepareStatement(RoomQuery.INSERT_ROOM_DATA);

            psRoom.setString(1, room.getrInfo());
            psRoom.setString(2, room.getrDimension());
            psRoom.setDouble(3, room.getrPrice());
            psRoom.setString(4, room.getrStatus());

            psRoom.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean updateRoomData(Room room) {
        PreparedStatement psRoom = null;
        try {

            psRoom = connection.prepareStatement(RoomQuery.UPDATE_ROOM_DATA);

            psRoom.setString(1, room.getrInfo());
            psRoom.setString(2, room.getrDimension());
            psRoom.setDouble(3, room.getrPrice());
            psRoom.setString(4, room.getrStatus());
            psRoom.setInt(5, UtilityMethod.seperateID(room.getrNo()));

            psRoom.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean updateRoomStatus(Room room) {
        PreparedStatement psRoom = null;
        try {

            psRoom = connection.prepareStatement(RoomQuery.UPDATE_ROOM_STATUS);

            psRoom.setString(1, room.getrStatus());
            psRoom.setInt(2, UtilityMethod.seperateID(room.getrNo()));

            psRoom.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public boolean deleteRoomData(String rID) {
        PreparedStatement psRoom= null;
        try {

            psRoom = connection.prepareStatement(RoomQuery.DELETE_ROOM_DATA);

            psRoom.setInt(1, UtilityMethod.seperateID(rID));
            psRoom.execute();
            return true;

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
            return false;
        }
    }

    public SortedList<Room> searchTable(TextField searchTextField) {
        //Retreiving all data from database
        ObservableList<Room> roomData = loadAllRoomData();
        //Wrap the ObservableList in a filtered List (initially display all data)
        FilteredList<Room> filteredData = new FilteredList<>(roomData, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(room -> {
                //if filter text is empty display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //comparing search text with table columns one by one
                String lowerCaseFilter = newValue.toLowerCase();

                 if (UtilityMethod.addPrefix("Room-", room.getrNo()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //return if filter matches data
                    return true;
                } else if (room.getrStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                     //return if filter matches data
                     return true;
                 } else {
                    //have no matchings
                    return false;
                }
            });
        });
        //wrapping the FilteredList in a SortedList
        SortedList<Room> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

}
