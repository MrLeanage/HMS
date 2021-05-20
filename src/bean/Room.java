package bean;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.dataHandler.UtilityMethod;

public class Room {
    private String rNo = null;
    private String rInfo = null;
    private String rDimension = null;
    private Double rPrice = null;
    private String rStatus = null;

    public Room() {
    }

    public Room(String rNo, String rInfo, String rDimension, Double rPrice, String rStatus) {
        this.rNo = UtilityMethod.addPrefix("Room-", rNo);
        this.rInfo = rInfo;
        this.rDimension = rDimension;
        this.rPrice = rPrice;
        this.rStatus = rStatus;
    }

    public String getrNo() {
        return rNo;
    }

    public StringProperty rNoProperty(){
        return new SimpleStringProperty(rNo);
    }

    public void setrNo(String rNo) {
        this.rNo = rNo;
    }

    public String getrInfo() {
        return rInfo;
    }

    public StringProperty rInfoProperty(){
        return new SimpleStringProperty(rInfo);
    }


    public void setrInfo(String rInfo) {
        this.rInfo = rInfo;
    }

    public String getrDimension() {
        return rDimension;
    }

    public StringProperty rDimensionProperty(){
        return new SimpleStringProperty(rDimension);
    }


    public void setrDimension(String rDimension) {
        this.rDimension = rDimension;
    }

    public Double getrPrice() {
        return rPrice;
    }

    public DoubleProperty rPriceProperty(){
        return new SimpleDoubleProperty(rPrice);
    }


    public void setrPrice(Double rPrice) {
        this.rPrice = rPrice;
    }

    public String getrStatus() {
        return rStatus;
    }

    public StringProperty rStatusProperty(){
        return new SimpleStringProperty(rStatus);
    }


    public void setrStatus(String rStatus) {
        this.rStatus = rStatus;
    }
}
