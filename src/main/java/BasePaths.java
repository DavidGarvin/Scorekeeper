public class BasePaths {
    private Player firstBase, secondBase, thirdBase;

    public BasePaths() {
        firstBase = null;
        secondBase = null;
        thirdBase = null;
    }

    //Can return null if no runner on base and that's OK. However, if the input isn't 1-3 then returns null AND an error message.
    public Player getRunner(int base) {
        switch(base) {
            case 1:
                return firstBase;
            case 2:
                return secondBase;
            case 3:
                return thirdBase;
            default:
                System.out.println("Error: " + Result.INVALID_COMMAND + " getRunner() in BasePaths class");
                return null;
        }
    }

    //Returns integer for which base the player is on. If the player isn't on any bases, returns -1.
    public int playerOnWhichBase(Player player) {
        if(firstBase == player) {
            return 1;
        } else if(secondBase == player) {
            return 2;
        } else if(thirdBase == player) {
            return 3;
        }

        return -1;
    }

    public void batterOnBase(Player player) {
        if(firstBase != null) {
            advanceRunner(firstBase);
        }

        firstBase = player;
    }

    public void advanceRunner(Player player) {
        switch(playerOnWhichBase(player)) {
            case 1:
                if(secondBase != null) {
                    advanceRunner(secondBase);
                }
                firstBase = player;
                break;
            case 2:
                if(thirdBase != null) {
                    advanceRunner(thirdBase);
                }
                secondBase = player;
                break;
            case 3:
                System.out.println("Run! " + player.getName() + " scores!"); //Remove later.
                removeRunner(thirdBase);
                break;
            default:
                System.out.println(player.getName() + " is not on base.");
                break;
        }
    }

    public void removeRunner(Player player) {
        switch(playerOnWhichBase(player)) {
            case 1:
                firstBase = null;
                break;
            case 2:
                secondBase = null;
                break;
            case 3:
                thirdBase = null;
                break;
            default:
                System.out.println(player.getName() + " is not on base.");
                break;
        }
    }
}
