package com.codecool.fiveinarow;


import java.util.Scanner;

public class Game implements GameInterface {
    private final Player player1 = new Player("Player 1", 'X');
    private final Player player2 = new Player("Player 2", '0');

    private final char BOARD_FILLING = '*';
    private final int ROW_IDX = 0;
    private final int COL_IDX = 1;
    private final int rowNum;
    private final int colNum;
    private char[][] board;


    public Game(int nRows, int nCols) {

        rowNum = nRows;
        colNum = nCols;

        // make a board of nRows & nCols
        board = new char[nRows][nCols];
        for ( int row = 0; row < nRows; row++){
            for(int col = 0; col < nCols; col++){
                board[row][col] = BOARD_FILLING;
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
        int[] coords;

        Scanner scanner = new Scanner(System.in);
        String userInput;

        boolean invalidInput = false;
        do {
            System.out.println("Player " + player + " coordinates:");
            userInput = scanner.nextLine();

            coords = validateInput(userInput);
            // checking if input is not too long
            if (userInput.length() > 3) {
                System.out.println("Coordinates too long");
                invalidInput = true;

            // checking if coordinates are outside the board
            } else if (coords[ROW_IDX] > this.rowNum - 1 || coords[COL_IDX] > this.colNum - 1) {
                System.out.println("Coordinates out of the board. Try again");
                invalidInput = true;

            // checking if coordinates are taken
            } else if (this.board[coords[ROW_IDX]][coords[COL_IDX]] != BOARD_FILLING) {
                System.out.println("Coordinates are taken. Try again");
                invalidInput = true;
            }
        } while (invalidInput);

//        scanner.close();

        return coords;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        if (player == 1) {
            this.board[row][col] = player1.appearance;
        } else {
            this.board[row][col] = player2.appearance;
        }
    }

    public boolean hasWon(int player, int howMany) {
//        char playerField;
//
//        if (player == 1) {
//            playerField = PLAYER_ONE;
//        } else {
//            playerField = PLAYER_TWO;
//        }

        int counter = 0;
//        for (int i = 0; i < rowNum; i++) {
//            for (int j = 0; j < colNum; j++) {
//                if (this.board[i][j] == playerField) {
//
//                    boolean ownField = false;
//                    do {
//                        if (this.board[i][j + 1] == playerField) {
//                            counter++;
//                            ownField = true;
//                        } else {
//                            ownField = false;
//                        }
//                    } while (ownField);
//                }
//            }
//        }
        return counter == howMany;
    }

    public boolean isFull() {
        for (char[] chars : this.board) {
            for (char aChar : chars) {
                if (aChar == BOARD_FILLING) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        StringBuilder header = new StringBuilder();
        header.append("   ");
        for (int i_col = 1; i_col < this.colNum + 1; i_col++) {
            header.append(i_col);
            header.append(" ");
        }
        System.out.println(header.toString());

        for (int i_row = 0; i_row < this.rowNum; i_row++) {
            StringBuilder line = new StringBuilder();

            line.append((char) (i_row + 65));
            line.append("  ");
            for (int i_col = 0; i_col < this.colNum; i_col++) {
                line.append(this.board[i_row][i_col]);
                line.append(" ");
            }
            System.out.println(line.toString());
        }
    }

    public void printResult(int player) {
        if (player == 1) {
            player1.result++;
        } else {
            player2.result++;
        }

        System.out.println("Current results: Player 1 -> " + player1.result + " points, Player 2 -> " + player2.result + " points\n");
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
        int game = 0;
        int currentPlayer = 1;

        System.out.println("Welcome to Gomoku!\n");
        System.out.println("Let's start!\n");
        do {  // Loop for multiple games.
            System.out.println(game + 1 + " attempt out of " + howMany + "\n");

            boolean runGame = true;
            do {  // Loop for actual game.
                printBoard();
                System.out.println(" ");

                int[] newCoords = getMove(currentPlayer);
                mark(currentPlayer, newCoords[ROW_IDX], newCoords[COL_IDX]);

                if (hasWon(currentPlayer, howMany)) {
                    System.out.println("Player " + currentPlayer + " has won! Congratulations!");
                    runGame = false;
                }

                if (isFull()) {
                    System.out.println("The board is full.");
                    System.out.println("Try Again!\n");
                    runGame = false;
                }
                currentPlayer = playerSwitch(currentPlayer);

            } while (runGame);

            // The game is over.
            printResult(currentPlayer);
            currentPlayer = playerSwitch(currentPlayer);
            game++;

        } while (game < howMany);

        System.out.println("Thanks for the game!");
    }

    private int[] validateInput(String inputUser){
        // if it has two elements

        String str = inputUser.substring(0, 1).toUpperCase(); // take out first element ex. A and make it upperCase
        int col = Integer.parseInt(inputUser.substring(1)) - 1; // take out second numbers ex. 11
        char rowAsChar = str.charAt(0);
        int row = rowAsChar - 65;

        return new int[] { row, col };
    }

    private int playerSwitch(int player) {
        return player == 1 ? 2: 1;
    }

}


class Player {
    String name;
    char appearance;
    int result;

    public Player(String name, char appearance) {
        this.name = name;
        this.appearance = appearance;
        this.result = 0;
    }
}