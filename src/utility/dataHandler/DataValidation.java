package utility.dataHandler;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    //Checking Strings for not empty with same name

    public static boolean TextFieldNotEmpty(String stringField){
        //returning string fields empty as default value
        boolean returnVal = false;
        if(stringField != null  && !stringField.isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(String stringField, Label label, String validationText){

        if(!TextFieldNotEmpty(stringField)){
            label.setText(validationText);
        }

    }

    public static boolean PasswordFieldMatch(PasswordField passwordField, PasswordField ConfirmPasswordField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(passwordField.getText().equals(ConfirmPasswordField.getText())){
            returnVal = true;
        }
        return returnVal;
    }
    public static void PasswordFieldMatch(PasswordField passwordField, PasswordField ConfirmPasswordField, Label passwordLabel, Label confirmPasswordLabel,String validationText){

        if(!PasswordFieldMatch(passwordField, ConfirmPasswordField)){
            passwordLabel.setText(validationText);
            confirmPasswordLabel.setText(validationText);
        }

    }
    public static boolean ImageFieldNotEmpty(ImageView imageView){
        boolean returnVal = false;
        try{
            if(!(imageView.getImage() == null) || !(imageView.getImage().isError())){
                returnVal = true;
            }
        }catch (NullPointerException ex){

        }

        return returnVal;
    }
    public static void ImageFieldNotEmpty(ImageView imageView, Label label, String validationText){

        if(!ImageFieldNotEmpty(imageView)){
            label.setText(validationText);
        }
    }
    //email validation
    public static final Pattern VALIDEMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isValidEmail(String emailStr) {
        boolean returnVal = false;
        Matcher matcher = VALIDEMAIL.matcher(emailStr);

        if(matcher.find()){
            returnVal = true;
        }
        return returnVal;
    }

    public static void isValidEmail(String emailStr, Label label, String validationText) {

        if((!isValidEmail(emailStr))&& (!emailStr.isEmpty())){
            label.setText(validationText);
        }
    }
    //phone number validation for 10 digits, start with zero, next value from 1-9 and rest 8 digits from 0-9
    public static  final Pattern VALIDPHONENO = Pattern.compile("^[0][1-9][0-9]{8}$");

    public static boolean isValidPhoneNo(String phone){

        Matcher matcher = VALIDPHONENO.matcher(phone);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }

    public static void isValidPhoneNo(String phone, Label label, String validationText){

        if((!isValidPhoneNo(phone))&& (!phone.isEmpty())){
            label.setText(validationText);
        }
    }
    //checking for integer number
    public static  final Pattern VALIDINTEGER = Pattern.compile("\\d+$");
    public static  final Pattern VALIDFLOAT = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+$");
    public static boolean isValidNumberFormat(String number) {
        int numberCheck = 0;
        try{
            Integer.parseInt(number);
            numberCheck = 1;
        }catch(NumberFormatException ex){

        }
        try{
            Float.parseFloat(number);
            numberCheck = 2;
        }catch(NumberFormatException ex){

        }
        if(numberCheck == 1){
            Matcher matcher = VALIDINTEGER.matcher(number);
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }else if(numberCheck == 2){
            Matcher matcher = VALIDFLOAT.matcher(number);
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public static void isValidNumberFormat(String number, Label label, String validationText) {
        if((!isValidNumberFormat(number)) && (!number.isEmpty())){
            label.setText(validationText);
        }
    }
    //checking for maximum length
    public static boolean isValidMaximumLength(String data, int maxLength){
        boolean returnVal = true;
        if(data.length() > maxLength){
            returnVal = false;
        }
        return returnVal;
    }
    public static void isValidMaximumLength(String data, int maxLength, Label label, String validationText){
        if(!isValidMaximumLength(data,maxLength)){
            label.setText(validationText);
        }
    }

    public static  final Pattern VALIDOLDNIC = Pattern.compile("^[0-9]{9}[vVxX]$");
    public static  final Pattern VALIDNEWNIC = Pattern.compile("^[1-2]{1}[0-9]{11}$");

    public static boolean isValidNIC(TextField textField){
        if(isValidNumberFormat(textField.getText())){
            Matcher matcher = VALIDNEWNIC.matcher(textField.getText());
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }else{
            Matcher matcher = VALIDOLDNIC.matcher(textField.getText());
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }
    }
    public static void isValidNIC(TextField textField, Label label, String validationText){
        if(!(isValidNIC(textField))){
            label.setText(validationText);
        }
    }

}
