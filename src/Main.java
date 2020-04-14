import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    // Starting position on the board
    public static final int startOfBoard = 1;

    //Ending position on the board
    public static final int endOfBoard = 100;

    // HashMap containing head of snake and tail of snake
    public HashMap<Integer, Integer> snakes;

    // HashMap containing start of ladder and end of ladder
    public HashMap<Integer, Integer> ladders;

    //HashMap containing player name and their current state
    public HashMap<String, Integer> playerMap;

    // State of the board - False if no one has won yet
    public boolean isComplete;

    // Initialize the board and setup all snakes and ladders
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        BufferedReader reader;

        snakes = new HashMap<>();
        ladders = new HashMap<>();
        playerMap = new HashMap<>();

        // Queue of players in order of their turns
        ArrayDeque<String> playerList = new ArrayDeque<String>();

        // Random number generator to simulate a die roll
        Random random = new Random();

        isComplete = false;

        try {
            reader = new BufferedReader(new FileReader("/Users/mannebhai/webops/practice/mock-machine-coding-1/input/test.txt"));

            String line = reader.readLine();

            // Read and enter locations of snakes
            int numberOfSnakes = Integer.valueOf(line);
            line = reader.readLine();
            for(int i=0; i<numberOfSnakes; i++) {
                snakes.put(Integer.valueOf(line.split(" ")[0]), Integer.valueOf(line.split(" ")[1]));
                line = reader.readLine();
            }

            // Read and enter locations of ladders
            int numberOfLadders = Integer.valueOf(line);
            line = reader.readLine();
            for(int i=0; i<numberOfLadders; i++) {
                ladders.put(Integer.valueOf(line.split(" ")[0]), Integer.valueOf(line.split(" ")[1]));
                line = reader.readLine();
            }

            // Read and enter locations of players
            int numberOfPlayers = Integer.valueOf(line);
            line = reader.readLine();
            for(int i=0; i<numberOfPlayers; i++) {
                playerMap.put(line, startOfBoard);
                playerList.add(line);
                line = reader.readLine();
            }

            //Close reader once all input is received
            reader.close();

            // Keep rolling the dice and making moves until someone wins
            while(!isComplete || playerList.isEmpty()) {
                // Pop the first player in the queue to make a move
                String player = playerList.pop();

                move(player, random.nextInt(5) + 1);

                // Push the player back to the end of the queue
                playerList.add(player);
            }

            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(String player, int moveCount) {
        // Take player name and moveCount and update the new state after the move is made

        System.out.print(player + " rolled a " + Integer.toString(moveCount) + " and moved from " + Integer.toString(playerMap.get(player)) + " to ");
        int newState = playerMap.get(player) + moveCount;

        if(newState > endOfBoard) {
            System.out.print(Integer.toString(newState-moveCount) + "\n");
            return;
        }

        while(snakes.containsKey(newState) || ladders.containsKey(newState)) {
            if(snakes.containsKey(newState)) {
                newState = snakes.get(newState);
            } else if(ladders.containsKey(newState)) {
                newState = ladders.get(newState);
            }
        }

        System.out.print(Integer.toString(newState) + "\n");

        playerMap.put(player, newState);

        if(newState == endOfBoard) {
            isComplete = true;
            System.out.print(player + " wins the game\n");
        }

        return;
    }
}