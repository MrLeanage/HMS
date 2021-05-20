package utility.query;

public class StudentQuery {
    public static final String LOAD_ALL_STUDENT_DATA = "SELECT * FROM student";
    public static final String LOAD_SPECIFIC_STUDENT_DATA = "SELECT * FROM student WHERE sNIC = ?";
    public static final String INSERT_STUDENT_DATA = "INSERT INTO student (sNIC, sName, sAddress, sPhone, sImage) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_STUDENT_DATA = "UPDATE student SET sName = ?, sAddress = ?, sPhone = ?, sImage = ? WHERE sNIC = ?";
    public static final String DELETE_STUDENT_DATA = "DELETE FROM student WHERE sNIC = ?";

}
