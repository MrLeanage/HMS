package view.reservationManagement;

import bean.Room;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.RoomService;
import utility.dataHandler.DataValidation;
import utility.popUp.AlertPopUp;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomPopUpController implements Initializable {
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> noColumn;

    @FXML
    private TableColumn<Room, String> infoColumn;

    @FXML
    private TableColumn<Room, String> dimensionColumn;

    @FXML
    private TableColumn<Room, Double> priceColumn;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label roomNoLabel;

    @FXML
    private TextField roomNoTextField;

    @FXML
    private TextField dimensionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextArea infoTextArea;

    @FXML
    private TextField statusTextField;

    @FXML
    private JFXButton cancelButton;

    private static Room selectedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        RoomService roomService = new RoomService();
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();

        for(Room room : roomService.loadAllRoomData()){
            if(room.getrStatus().equals("Available"))
                roomObservableList.add(room);
        }

        noColumn.setCellValueFactory(new PropertyValueFactory<>("rNo"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("rInfo"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("rDimension"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));

        roomTable.setItems(null);
        roomTable.setItems(roomObservableList);
        searchTable();
    }

    private void searchTable() {

        RoomService roomService = new RoomService();

        SortedList<Room> sortedData = roomService.searchTable(searchTextField);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(roomTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ObservableList<Room> list = FXCollections.observableArrayList();
        for(Room room :sortedData){
            if(room.getrStatus().equals("Available"))
                list.add(room);
        }
        roomTable.setItems(list);
    }


    @FXML
    void setSelectedRoomDataToFields(MouseEvent event) {
        try {
            selectedRoom = roomTable.getSelectionModel().getSelectedItem();
            roomNoTextField.setText(selectedRoom.getrNo());
            infoTextArea.setText(selectedRoom.getrInfo());
            dimensionTextField.setText(selectedRoom.getrDimension());
            priceTextField.setText(selectedRoom.getrPrice().toString());
            statusTextField.setText(selectedRoom.getrStatus());

        } catch (NullPointerException exception) {

        }
    }

    @FXML
    void addSelectedRoom(ActionEvent event) {
        if (selectedRoom != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationDetail.fxml"));
            try {
                Parent root = (Parent) loader.load();
                ReservationDetailController orderDetailController = loader.getController();

                orderDetailController.setRoom(selectedRoom);
                selectedRoom = null;
                closeStage();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else
            AlertPopUp.selectRow("Room to Add");
    }

    private void closeStage(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeStage(ActionEvent actionEvent){
        // get a handle to the stage
        closeStage();
    }
}
