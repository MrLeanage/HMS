package utility.query;

public class ReservationQuery {
    public static final String LOAD_ALL_RESERVATION_DATA = "SELECT * FROM reservation";
    public static final String LOAD_SPECIFIC_RESERVATION_DATA = "SELECT * FROM reservation WHERE rRID = ?";
    public static final String INSERT_RESERVATION_DATA = "INSERT INTO reservation (rRNIC, rRNumber, rRDate, rRStatus) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_RESERVATION_DATA = "UPDATE reservation SET rRNIC = ?, rRNumber = ?, rRDate = ?, rRStatus = ? WHERE rRID = ?";
    public static final String DELETE_RESERVATION_DATA = "DELETE FROM reservation WHERE rRID = ?";
}
