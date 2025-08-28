public enum FieldPosition {
    DH(0, "Designated Hitter"),
    P(1, "Pitcher"),
    C(2, "Catcher"),
    FB(3, "First Base"),
    SB(4, "Second Base"),
    TB(5, "Third Base"),
    SS(6, "Shortstop"),
    LF(7, "Left Field"),
    CF(8, "Center Field"),
    RF(9, "Right Field"),
    RV(10, "Rover"),
    EH(11, "Extra Hitter"),
    BN(12, "Bench");

    private final int number;
    private final String displayName;

    FieldPosition(int number, String displayName) {
        this.number = number;
        this.displayName = displayName;
    }

    public int getPositionNumber() {
        return number;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static FieldPosition fromNumber(int number) {
        for(FieldPosition pos : values()) {
            if(pos.number == number) {
                return pos;
            }
        }
        throw new IllegalArgumentException();
    }
}
