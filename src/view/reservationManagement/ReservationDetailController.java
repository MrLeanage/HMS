package view.reservationManagement;

import bean.Reservation;
import bean.Room;
import bean.Student;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.ReservationService;
import service.RoomService;
import utility.dataHandler.DataValidation;
import utility.popUp.AlertPopUp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDetailController implements Initializable {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> IDColumn;

    @FXML
    private TableColumn<Reservation, String> nicColumn;

    @FXML
    private TableColumn<Reservation, String> nameColumn;

    @FXML
    private TableColumn<Reservation, String> roomNoColumn;

    @FXML
    private TableColumn<Reservation, Double> priceColumn;

    @FXML
    private TableColumn<Reservation, String> dateColumn;

    @FXML
    private TableColumn<Reservation, String> statusColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField studentNICTextField;

    @FXML
    private Label studentLabel;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXButton addButton;


    @FXML
    private TextField studentNameTextField;

    @FXML
    private TextField roomNoTextField;

    @FXML
    private Label roomLabel;

    @FXML
    private TextField roomPriceTextField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    private static Reservation selectedReservation;

    private static Student selectedStudent;

    private static Room selectedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Reserved", "Reservation Hold", "Discarded");
        statusChoiceBox.setValue("Reserved");
        statusChoiceBox.setItems(list);
        loadData();
    }

    private void loadData() {
        ReservationService reservationService = new ReservationService();
        ObservableList<Reservation> reservationObservableList = reservationService.loadAllReservationData();

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("rRID"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("rRNIC"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("rRName"));
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<>("rRNumber"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rRPrice"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("rRDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rRStatus"));

        reservationTable.setItems(null);
        reservationTable.setItems(reservationObservableList);
        searchTable();
    }

    private void searchTable() {

        ReservationService reservationService = new ReservationService();

        SortedList<Reservation> sortedData = reservationService.searchTable(searchTextField);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(reservationTable.comparatorProperty());
        //adding sorted and filtered data to the table
        reservationTable.setItems(sortedData);
    }

    @FXML
    private void addReservation(ActionEvent event) {
        clearLabels();
        if (reservationValidation()) {
            Reservation reservation = new Reservation();
            ReservationService reservationService = new ReservationService();

            reservation.setrRNIC(studentNICTextField.getText());
            reservation.setrRNumber(roomNoTextField.getText());
            reservation.setrRDate(LocalDate.now().toString());
            reservation.setrRStatus(statusChoiceBox.getValue());


            if (reservationService.insertReservationData(reservation)) {
                if (!reservation.getrRStatus().equals("Discarded")) {
                    Room room = new Room();
                    room.setrNo(roomNoTextField.getText());
                    room.setrStatus("Reserved");
                    RoomService roomService = new RoomService();
                    roomService.updateRoomStatus(room);
                }
                AlertPopUp.insertSuccesfully("Reservation");
                loadData();
                searchTable();
                clearFields(event);
            } else
                AlertPopUp.insertionFailed("Reservation");

        } else
            reservationValidationMessage();
    }

    @FXML
    private void updateReservation(ActionEvent event) {
        clearLabels();
        if (reservationValidation()) {
            Reservation reservation = new Reservation();
            ReservationService reservationService = new ReservationService();

            reservation.setrRID(selectedReservation.getrRID());
            reservation.setrRNIC(studentNICTextField.getText());
            reservation.setrRNumber(roomNoTextField.getText());
            reservation.setrRDate(LocalDate.now().toString());
            reservation.setrRStatus(statusChoiceBox.getValue());

            RoomService roomService = new RoomService();
            Room specificRoom = roomService.loadSpecificRoom(reservation.getrRNumber());

            //Checking whether Choice box value is equal to "Reserved" or "Hold Reservation", "Discarded" will execute with else
            if (!statusChoiceBox.getValue().equals("Discarded")) {
                //Reserving new Reservation with a available room
                //Checking whether selected record's room "Available" and Choice box value is "Reserved"
                if (specificRoom.getrStatus().equals("Available") && statusChoiceBox.getValue().equals("Reserved")) {
                    if (reservationService.updateReservationData(reservation)) {
                        Room room = new Room();
                        room.setrNo(selectedReservation.getrRNumber());
                        room.setrStatus("Reserved");
                        roomService.updateRoomStatus(room);
                        AlertPopUp.updateSuccesfully("Reservation");
                    } else
                        AlertPopUp.updateFailed("Reservation");
                    //Changing "Reserved" Room to "Hold" Status or "Hold" Room to  Status "Reserved"
                    //Checking whether selected record's room "Reserved" and Existing Reservation status is not "Discarded"
                } else if (specificRoom.getrStatus().equals("Reserved") && !selectedReservation.getrRStatus().equals("Discarded")){
                    if (reservationService.updateReservationData(reservation))
                        AlertPopUp.updateSuccesfully("Reservation");
                    else
                        AlertPopUp.updateFailed("Reservation");
                    //Moving "Discarded" Reservation to "Hold" Status by reserving
                    //Checking whether selected record's room "Available" and Choice box value is "Reservation Hold" and selected Reservation's existing room status value is on "Discarded"
                } else if (specificRoom.getrStatus().equals("Available") && statusChoiceBox.getValue().equals("Reservation Hold") && selectedReservation.getrRStatus().equals("Discarded")){
                    Room room = new Room();
                    room.setrNo(selectedReservation.getrRNumber());
                    room.setrStatus("Reserved");
                    roomService.updateRoomStatus(room);
                    AlertPopUp.updateSuccesfully("Reservation");
                    reservationService.updateReservationData(reservation);
                }else
                    //Showing Room not available
                    AlertPopUp.updateFailed("Room is not Available!!");
            } else {
                //Discarding a reservation and setting room available back
                if (reservationService.updateReservationData(reservation)) {
                    Room room = new Room();
                    room.setrNo(selectedReservation.getrRNumber());
                    room.setrStatus("Available");
                    roomService.updateRoomStatus(room);
                    AlertPopUp.updateSuccesfully("Reservation");
                } else
                    AlertPopUp.updateFailed("Reservation");
            }
            loadData();
            searchTable();
            clearFields(event);
        } else
            reservationValidationMessage();
    }

    @FXML
    private void deleteReservation(ActionEvent event) {
        if (selectedReservation != null) {
            if (selectedReservation.getrRStatus().equals("Discarded")) {
                ReservationService reservationService = new ReservationService();
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Reservation");
                if (action.get() == ButtonType.OK) {
                    if (reservationService.deleteReservationData((selectedReservation.getrRID()))) {
                        AlertPopUp.deleteSuccesfull("Reservation");
                        loadData();
                        searchTable();
                        clearFields(event);
                    } else
                        AlertPopUp.deleteFailed("Reservation");
                } else
                    loadData();
            } else {
                AlertPopUp.deleteFailed("Cannot delete a " + selectedReservation.getrRStatus() + " Reservation");
            }
        } else {
            AlertPopUp.selectRowToDelete("Reservation");
        }
    }

    @FXML
    void setSelectedReservationDataToFields(MouseEvent event) {
        try {
            clearLabels();
            addButton.setVisible(false);
            updateButton.setVisible(true);
            selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
            studentNICTextField.setText(selectedReservation.getrRNIC());
            studentNameTextField.setText(selectedReservation.getrRName());
            roomNoTextField.setText(selectedReservation.getrRNumber());
            roomPriceTextField.setText(selectedReservation.getrRPrice().toString());
            statusChoiceBox.setValue(selectedReservation.getrRStatus());

        } catch (NullPointerException exception) {

        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        studentNICTextField.setText("");
        studentNameTextField.setText("");
        roomNoTextField.setText("");
        roomPriceTextField.setText("");
        statusChoiceBox.setValue("Reserved");
        resetComponents();
        clearLabels();
        selectedReservation = null;
        selectedStudent = null;
        selectedRoom = null;
    }

    private void clearLabels() {
        studentLabel.setText("");
        roomLabel.setText("");
    }

    private boolean reservationValidation() {
        return DataValidation.TextFieldNotEmpty(studentNICTextField.getText())
                && DataValidation.TextFieldNotEmpty(roomNoTextField.getText());
    }

    private void reservationValidationMessage() {
        DataValidation.TextFieldNotEmpty(studentNICTextField.getText(), studentLabel, "Please Select a Student to make a Reservation!");
        DataValidation.TextFieldNotEmpty(roomNoTextField.getText(), roomLabel, "Please Select a Room to make a Reservation!");
    }

    private void resetComponents() {
        addButton.setVisible(true);
        updateButton.setVisible(false);
    }

    @FXML
    private void browseStudent(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("studentPopUp.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentPopUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.sizeToScene();

        stage.showAndWait();

        if (selectedStudent != null) {
            studentNICTextField.setText(selectedStudent.getsNIC());
            studentNameTextField.setText(selectedStudent.getsName());
        }
    }

    @FXML
    private void browseRoom(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("roomPopUp.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(RoomPopUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.sizeToScene();

        stage.showAndWait();
        if (selectedRoom != null) {
            roomNoTextField.setText(selectedRoom.getrNo());
            roomPriceTextField.setText(selectedRoom.getrPrice().toString());
        }
    }

    public void setRoom(Room room) {
        selectedRoom = room;
    }

    public void setStudent(Student student) {
        selectedStudent = student;
    }
}
