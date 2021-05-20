package utility.query;

public class RoomQuery {
    public static final String LOAD_ALL_ROOM_DATA = "SELECT * FROM room";
    public static final String LOAD_SPECIFIC_ROOM_DATA = "SELECT * FROM room WHERE rNo = ?";
    public static final String INSERT_ROOM_DATA = "INSERT INTO room (rInfo, rDimension, rPrice, rStatus) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_ROOM_DATA = "UPDATE room SET rInfo = ?, rDimension = ?, rPrice = ?, rStatus = ? WHERE rNo = ?";
    public static final String UPDATE_ROOM_STATUS = "UPDATE room SET rStatus = ? WHERE rNo = ?";
    public static final String DELETE_ROOM_DATA = "DELETE FROM room WHERE rNo = ?";
}
