package bean;

import javafx.beans.property.*;
import javafx.scene.image.ImageView;


public class Student {
    private String sNIC = null;
    private String sName = null;
    private String sAddress = null;
    private Integer sPhone = null;
    private ImageView sImage = null;

    public Student() {
    }

    public Student(String sNIC, String sName, String sAddress, Integer sPhone, ImageView sImage) {
        this.sNIC = sNIC;
        this.sName = sName;
        this.sAddress = sAddress;
        this.sPhone = sPhone;
        this.sImage = sImage;
    }

    public String getsNIC() {
        return sNIC;
    }

    public StringProperty sNICProperty(){
        return new SimpleStringProperty(sNIC);
    }

    public void setsNIC(String sNIC) {
        this.sNIC = sNIC;
    }

    public String getsName() {
        return sName;
    }

    public StringProperty sNameProperty(){
        return new SimpleStringProperty(sName);
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAddress() {
        return sAddress;
    }

    public StringProperty sAddressProperty(){
        return new SimpleStringProperty(sAddress);
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public Integer getsPhone() {
        return sPhone;
    }

    public IntegerProperty sPhoneProperty(){
        return new SimpleIntegerProperty(sPhone);
    }

    public void setsPhone(Integer sPhone) {
        this.sPhone = sPhone;
    }

    public ImageView getsImage() {
        return sImage;
    }

    public ObjectProperty<ImageView> sImageProperty(){
        return new SimpleObjectProperty<>(sImage);
    }

    public void setsImage(ImageView sImage) {
        this.sImage = sImage;
    }
}
