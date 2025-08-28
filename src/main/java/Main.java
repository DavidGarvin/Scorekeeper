import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Roster roster = null;
        while(true) {
            System.out.println("Team Name - Home or Away - Roster size (min 9, max " + Roster.MAX_PLAYERS + "\n");

            String line = input.nextLine();
            String[] lineArray = Arrays.stream(line.split("-")).map(String::trim).toArray(String[]::new);

            if(lineArray.length != 3) {
                System.out.println("\nError: " + Result.INVALID_COMMAND + "\n");
                continue;
            }

            TeamSide side;
            if(lineArray[1].equalsIgnoreCase("home")) {
                side = TeamSide.HOME;
            } else if(lineArray[1].equalsIgnoreCase("away")) {
                side = TeamSide.AWAY;
            } else {
                System.out.println("\nError: " + Result.INVALID_COMMAND + "\n");
                continue;
            }

            System.out.println();
            roster = new Roster(lineArray[0], side, Integer.parseInt(lineArray[2]));
            break;
        }

        boolean loop = true;
        while(loop) {
            System.out.println("add - Player Name - ID");
            System.out.println("add - ID");
            System.out.println("remove - ID");
            System.out.println("teamname - New Name");
            System.out.println("changeside - Home/Away");
            System.out.println("rostersize - Roster Size");
            System.out.println("print");
            System.out.println("quit\n");

            String command = input.nextLine();
            String[] commandArray = Arrays.stream(command.split("-")).map(String::trim).toArray(String[]::new);
            System.out.println();

            Result result = Result.INVALID_COMMAND;
            switch(commandArray[0].toLowerCase()) {
                case "add":
                    if(commandArray.length >= 3) {
                        result = roster.addPlayer(new Player(commandArray[1], commandArray[2]));
                    } else if(commandArray.length == 2) {
                        result = roster.addPlayer(new Player(commandArray[1]));
                    }
                    break;
                case "remove":
                    if(commandArray.length >= 2) {
                        result = roster.removePlayer(commandArray[1]);
                    }
                    break;
                case "teamname":
                    if(commandArray.length >= 2) {
                        roster.setTeamName(commandArray[1]);
                        result = Result.SUCCESS;
                    }
                    break;
                case "changeside":
                    if(commandArray.length >= 2) {
                        result = roster.setTeamSide(commandArray[1]);
                    }
                    break;
                case "rostersize":
                    if(commandArray.length >= 2) {
                        try {
                            roster.setRoster(Integer.parseInt(commandArray[1]));
                            result = Result.SUCCESS;
                        } catch(NumberFormatException e) {
                            //Ignore because we already have "result" set.
                        }
                    }
                    break;
                case "print":
                    System.out.println(roster);
                    result = Result.SUCCESS;
                    break;
                case "quit":
                    loop = false;
                    result = Result.SUCCESS;
                    break;
            }

            if(result != Result.SUCCESS) {
                System.out.println("Error: " + result + "\n");
            }
        }
    }
}
