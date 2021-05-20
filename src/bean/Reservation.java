package bean;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.dataHandler.UtilityMethod;

public class Reservation {
    private String rRID = null;
    private String rRNIC = null;
    private String rRName = null;
    private String rRNumber = null;
    private Double rRPrice = null;
    private String rRDate = null;
    private String rRStatus = null;

    public Reservation() {
    }

    public Reservation(String rRID, String rRNIC, String rRName, String rRNumber, Double rRPrice, String rRDate, String rRStatus) {
        this.rRID = UtilityMethod.addPrefix("RR", rRID);
        this.rRNIC = rRNIC;
        this.rRName = rRName;
        this.rRNumber = UtilityMethod.addPrefix("Room-", rRNumber);
        this.rRPrice = rRPrice;
        this.rRDate = rRDate;
        this.rRStatus = rRStatus;
    }

    public String getrRID() {
        return rRID;
    }

    public StringProperty rRIDProperty(){
        return new SimpleStringProperty(rRID);
    }

    public void setrRID(String rRID) {
        this.rRID = rRID;
    }

    public String getrRNIC() {
        return rRNIC;
    }

    public StringProperty rRNICProperty(){
        return new SimpleStringProperty(rRNIC);
    }

    public void setrRNIC(String rRNIC) {
        this.rRNIC = rRNIC;
    }

    public String getrRName() {
        return rRName;
    }

    public StringProperty rRNameProperty(){
        return new SimpleStringProperty(rRName);
    }

    public void setrRName(String rRName) {
        this.rRName = rRName;
    }

    public String getrRNumber() {
        return rRNumber;
    }

    public StringProperty rRNumberProperty(){
        return new SimpleStringProperty(rRNumber);
    }

    public void setrRNumber(String rRNumber) {
        this.rRNumber = rRNumber;
    }

    public Double getrRPrice() {
        return rRPrice;
    }

    public DoubleProperty rRPriceProperty(){
        return new SimpleDoubleProperty(rRPrice);
    }

    public void setrRPrice(Double rRPrice) {
        this.rRPrice = rRPrice;
    }

    public String getrRDate() {
        return rRDate;
    }

    public StringProperty rRDateProperty(){
        return new SimpleStringProperty(rRDate);
    }

    public void setrRDate(String rRDate) {
        this.rRDate = rRDate;
    }

    public String getrRStatus() {
        return rRStatus;
    }

    public StringProperty rRStatusProperty(){
        return new SimpleStringProperty(rRStatus);
    }

    public void setrRStatus(String rRStatus) {
        this.rRStatus = rRStatus;
    }
}
