public class Lineup {
    public static final int MAX_LINEUP = Roster.MAX_PLAYERS;
    public static final int MIN_LINEUP = 9;

    private Player[] lineup;

    public Lineup() {
        lineup = new Player[MIN_LINEUP];
    }

    public Lineup(int numPlayers) {
        setLineup(numPlayers);
    }

    public Player[] getLineup() {
        return lineup;
    }

    //Will set a new lineup. If it's smaller than the amount of current size then some players will be lost.
    //If it's the same size, then do nothing. If there is no current lineup then just create one;
    public void setLineup(int numPlayers) {
        int number = Math.min(Math.max(numPlayers, MIN_LINEUP), MAX_LINEUP);

        //If initial lineup, create and end method.
        if(lineup.length == 0) {
            lineup = new Player[number];
            return;
        }

        //If new lineup is the same size as the old lineup, end the method.
        if(number == lineup.length) {
            return;
        }

        //Otherwise, continue and put as many players from the old lineup into the new lineup as possible.
        Player[] newLineup = new Player[number];

        int nextOpenIndex = 0;
        for (Player player : lineup) {
            if (nextOpenIndex == number) {
                break;
            }

            if (player == null) {
                continue;
            }

            newLineup[nextOpenIndex] = player;
            nextOpenIndex++;
        }

        this.lineup = newLineup;
    }

    public boolean isFull() {
        int count = 0;
        for(Player player : lineup) {
            if(player != null) {
                count++;
            }
        }

        return (count == lineup.length);
    }

    public boolean isEmpty() {
        for(Player player : lineup) {
            if(player != null) {
                return false;
            }
        }

        return true;
    }

    public boolean hasEmptySlot() {
        for(Player player : lineup) {
            if(player == null) {
                return true;
            }
        }

        return false;
    }

    public boolean idExists(String id) {
        if(id.isEmpty()) {
            return false;
        }

        String properID = (id.length() > Player.MAX_ID_LENGTH) ? id.substring(0, Player.MAX_ID_LENGTH) : id;

        for(Player player : lineup) {
            if(player == null) {
                continue;
            }
            if(player.getId().equals(properID)) {
                return true;
            }
        }

        return false;
    }

    //Add a player to the next empty slot in the lineup. Can't be Bench and position can't be taken.
    //Unlimited EH allowed.
    public Result addPlayer(Roster roster, String id, FieldPosition position) {
        if(!hasEmptySlot()) {
            System.out.println("Error: " + Result.ROSTER_FULL + " - addPlayer() in Lineup class");
            return Result.ROSTER_FULL;
        }

        if(!roster.idExists(id)) {
            System.out.println("Error: " + Result.NOT_FOUND + " - addPlayer() in Lineup class");
            return Result.NOT_FOUND;
        }

        if(isFull()) {
            System.out.println("Error: " + Result.ROSTER_FULL + " - addPlayer() in Lineup class");
            return Result.ROSTER_FULL;
        }

        if(position == FieldPosition.BN) {
            System.out.println("Error: " + Result.INVALID_COMMAND + " - addPlayer() in Lineup class, BN not allowed in lineup");
            return Result.INVALID_COMMAND;
        }

        //Check if position already exists. Grab first open index at the same time.
        int index = 0;
        int firstOpenIndex = -1;
        for(Player player : lineup) {
            if(player == null) {
                if(firstOpenIndex == -1) {
                    firstOpenIndex = index;
                }
                continue;
            }

            if(player.getPosition() == position) {
                System.out.println("Error: " + Result.POSITION_TAKEN + " - addPlayer() in Lineup class");
                return Result.POSITION_TAKEN;
            }
        }
        if(firstOpenIndex == -1) {
            System.out.println("Error: " + Result.UNKNOWN_FAILURE + " - addPlayer() in Lineup class");
        }

        //If we survive all of that, add the player to the first empty slot and send success.
        lineup[firstOpenIndex] = roster.getPlayer(id);
        lineup[firstOpenIndex].setPosition(position);
        return Result.SUCCESS;
    }

    //Removes player from the lineup. Note that they still exist in the roster.
    public Result removePlayer(Roster roster, String id) {
        if(isEmpty()) {
            System.out.println("Error: " + Result.ROSTER_EMPTY + " - removePlayer() in Lineup class");
            return Result.ROSTER_EMPTY;
        }

        if(!idExists(id)) {
            System.out.println("Error: " + Result.NOT_FOUND + " - removePlayer() in Lineup class");
            return Result.NOT_FOUND;
        }

        for(int i = 0 ; i < lineup.length; i++) {
            if(lineup[i].getId().equals(id)) {
                roster.getPlayer(id).setPosition(FieldPosition.BN);
                lineup[i] = null;
                return Result.SUCCESS;
            }
        }

        System.out.println("Error: " + Result.UNKNOWN_FAILURE + " - removePlayer() in Lineup class");
        return Result.UNKNOWN_FAILURE;
    }
}
