public class Roster {
    public static final int MAX_PLAYERS = 26;
    public static final int MIN_PLAYERS = 9;
    public static final int MAX_TEAM_NAME_LENGTH = 20;

    private String teamName;
    private TeamSide teamSide;
    private Player[] players;

    //Default Constructor not allowed;
    private Roster() {
        throw new UnsupportedOperationException("No default constructor allowed.");
    }

    public Roster(String teamName, TeamSide teamSide, int numPlayers) {
        setTeamName(teamName);
        setTeamSide(teamSide);
        setRoster(numPlayers);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = (teamName.length() > MAX_TEAM_NAME_LENGTH) ? teamName.substring(0, MAX_TEAM_NAME_LENGTH) : teamName;
    }

    public TeamSide getTeamSide() {
        return teamSide;
    }

    public void setTeamSide(TeamSide teamSide) {
        this.teamSide = teamSide;
    }

    public Result setTeamSide(String teamSide) {
        if(teamSide.equalsIgnoreCase("home")) {
            setTeamSide(TeamSide.HOME);
            return Result.SUCCESS;
        } else if(teamSide.equalsIgnoreCase("away")) {
            setTeamSide(TeamSide.AWAY);
            return Result.SUCCESS;
        } else {
            return Result.INVALID_COMMAND;
        }
    }

    public Player getPlayer(String id) {
        for(Player player : players) {
            if(player.getId().equals(id)) {
                return player;
            }
        }

        System.out.println("Error: " + Result.NOT_FOUND + " - getPlayer() in Roster class");
        return null;
    }

    public int getPlayerCount() {
        int count = 0;
        for(Player player : players) {
            if(player != null) {
                count++;
            }
        }

        return count;
    }

    public boolean isFull() {
        return (getPlayerCount() == players.length);
    }

    public boolean isEmpty() {
        for(Player player : players) {
            if(player != null) {
                return false;
            }
        }

        return true;
    }

    public Player[] getRoster() {
        return players;
    }

    //Will set a new roster. If it's smaller than the amount of current players then some will be lost.
    //If it's the same size, then do nothing. If there is no current roster then just create one.
    public void setRoster(int numPlayers) {
        int number = Math.min(Math.max(numPlayers, MIN_PLAYERS), MAX_PLAYERS);

        //If initial roster, create and end method.
        if(players.length == 0) {
            players = new Player[number];
            return;
        }

        //If new roster is the same size as old roster, end the method.
        if(number == players.length) {
            return;
        }

        //Otherwise, continue and put as many players from the old roster into the new roster as possible.
        Player[] newPlayers = new Player[number];

        int nextOpenIndex = 0;
        for (Player player : players) {
            if (nextOpenIndex == number) {
                break;
            }

            if (player == null) {
                continue;
            }

            newPlayers[nextOpenIndex] = player;
            nextOpenIndex++;
        }

        this.players = newPlayers;
    }

    //Checks if given ID exists in the current roster.
    public boolean idExists(String id) {
        if(id.isEmpty()) {
            return false;
        }

        String properID = (id.length() > Player.MAX_ID_LENGTH) ? id.substring(0, Player.MAX_ID_LENGTH) : id;

        for(Player player : players) {
            if(player == null) {
                continue;
            }
            if(player.getId().equals(properID)) {
                return true;
            }
        }

        return false;
    }

    public Result addPlayer(Player player) {
        if(player == null) {
            return Result.EMPTY_VALUE;
        }

        if(isFull()) {
            return Result.ROSTER_FULL;
        }

        if(idExists(player.getId())) {
            return Result.DUPLICATE_PLAYER;
        }

        for(int i = 0; i < players.length; i++) {
            if(players[i] == null) {
                players[i] = player;
                return Result.SUCCESS;
            }
        }

        return Result.UNKNOWN_FAILURE;
    }

    public Result removePlayer(String id) {
        if(id.isEmpty()) {
            return Result.EMPTY_VALUE;
        }

        if(isEmpty()) {
            return Result.ROSTER_EMPTY;
        }

        if(!idExists(id)) {
            return Result.NOT_FOUND;
        }

        for(int i = 0; i < players.length; i++) {
            if(players[i].getId().equals(id)) {
                players[i] = null;
                return Result.SUCCESS;
            }
        }

        return Result.UNKNOWN_FAILURE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String side = (teamSide == TeamSide.HOME) ? "Home" : "Away";
        int emptyCount = 0;

        sb.append("---------------\nTeam Name: ").append(this.teamName).append(" (").append(side).append(")\n\n");
        for (Player player : players) {
            if(player == null) {
                emptyCount++;
            } else {
                sb.append(player).append("\n");
            }
        }
        sb.append("----------\nEmpty slots: ").append(emptyCount).append("\n---------------");

        return sb.toString();
    }
}
