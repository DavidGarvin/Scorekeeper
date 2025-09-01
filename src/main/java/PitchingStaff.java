public class PitchingStaff {
    public static final int MAX_PITCHERS = Roster.MAX_PLAYERS;

    private Player[] pitchingStaff;
    private Player activePitcher;

    public PitchingStaff() {
        pitchingStaff = new Player[MAX_PITCHERS];
    }

    public Player[] getPitchingStaff() {
        return pitchingStaff;
    }

    public boolean isEmpty() {
        for(Player player: pitchingStaff) {
            if(player != null) {
                return false;
            }
        }

        return true;
    }

    public boolean idExists(String id) {
        if(id.isEmpty()) {
            System.out.println("Error: " + Result.INVALID_COMMAND + " - idExists() in Lineup class");
            return false;
        }

        String properID = (id.length() > Player.MAX_ID_LENGTH) ? id.substring(0, Player.MAX_ID_LENGTH) : id;

        for(Player player : pitchingStaff) {
            if(player == null) {
                continue;
            }
            if(player.getId().equals(properID)) {
                return true;
            }
        }

        return false;
    }

    public void makeActivePitcher(Player player) {
        if(!idExists(player.getId())) {
            System.out.println("Error: " + Result.INVALID_COMMAND + " - makeActivePitcher() in PitchingStaff class");
            return;
        }

        activePitcher = player;
    }

    public Result addPlayer(Roster roster, String id) {
        if(!roster.idExists(id)) {
            System.out.println("Error: " + Result.NOT_FOUND + " - addPlayer() in PitchingStaff class");
            return Result.NOT_FOUND;
        }

        boolean autoMakeActive = isEmpty();

        for(int i = 0; i < pitchingStaff.length; i++) {
            if(pitchingStaff[i] == null) {
                pitchingStaff[i] = roster.getPlayer(id);
                if(autoMakeActive) {
                    makeActivePitcher(roster.getPlayer(id));
                }
                return Result.SUCCESS;
            }
        }

        System.out.println("Error: " + Result.UNKNOWN_FAILURE + " - makeActivePitcher() in PitchingStaff class");
        return Result.UNKNOWN_FAILURE;
    }

    public Result removePlayer(String id) {
        if(id.isEmpty()) {
            System.out.println("Error: " + Result.EMPTY_VALUE + " - removePlayer() in PitchingStaff class");
            return Result.EMPTY_VALUE;
        }

        if(!idExists(id)) {
            System.out.println("Error: " + Result.NOT_FOUND + " - removePlayer() in PitchingStaff class");
            return Result.NOT_FOUND;
        }

        for(int i = 0; i < pitchingStaff.length; i++) {
            if(pitchingStaff[i].getId().equals(id)) {
                pitchingStaff[i] = null;
                return Result.SUCCESS;
            }
        }

        System.out.println("Error: " + Result.UNKNOWN_FAILURE + " - removePlayer() in PitchingStaff class");
        return Result.UNKNOWN_FAILURE;
    }
}
