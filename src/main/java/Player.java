import java.lang.reflect.Field;

public class Player {
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_ID_LENGTH = 3;

    private String name;
    private String id;
    private FieldPosition position;

    //Default Constructor not allowed.
    private Player() {
        throw new UnsupportedOperationException("No default constructor allowed.");
    }

    public Player(String id) {
        setName(id);
        setId(id);
        setPosition(FieldPosition.BN);
    }

    public Player(String name, String id) {
        setName(name);
        setId(id);
        setPosition(FieldPosition.BN);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name.length() > MAX_NAME_LENGTH) ? name.substring(0, MAX_NAME_LENGTH) : name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id.length() > MAX_ID_LENGTH) ? id.substring(0, MAX_ID_LENGTH) : id;
    }

    public FieldPosition getPosition() {
        return position;
    }

    public String getPositionDisplay() {
        return position.getDisplayName();
    }

    public int getPositionNumber() {
        return position.getPositionNumber();
    }

    public void setPosition(FieldPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return ("Name: " + getName() + ", ID: " + getId());
    }
}
