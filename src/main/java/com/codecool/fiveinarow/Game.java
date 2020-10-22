package com.codecool.fiveinarow;


import java.util.Scanner;

public class Game implements GameInterface {
    private final Player player1 = new Player("Player 1", 'X');
    private final Player player2 = new Player("Player 2", '0');
    private final Board board;

    // Index constants
    private final int I_ROW = 0;
    private final int I_COL = 1;


    public Game(int nRows, int nCols) {
        board = new Board(nRows, nCols, '*');
    }

    public char[][] getBoard() {
        return board.fields;
    }

    public void setBoard(char[][] newBoard) {
        board.fields = newBoard;
    }

    public void enableAi(int player) {
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        if (player == 1) {
            board.fields[row][col] = player1.appearance;
        } else {
            board.fields[row][col] = player2.appearance;
        }
    }

    public int[] getMove(int player) {
        int[] coords = new int[]{0, 0};

        Scanner scanner = new Scanner(System.in);
        String userInput;

        boolean invalidInput;
        do {
            System.out.println("Player " + player + " coordinates:");
            userInput = scanner.nextLine();

            // Quit from game.
            if (userWantEndOfGame(userInput)) return null;

            if (!validateInput(userInput)) {
                System.out.println("Invlid coordinates! Try agan");
                invalidInput = true;
                continue;
            }

            coords = getCoords(userInput);

            // checking if coordinates are outside the board
            if (coords[I_ROW] > board.size.rows - 1 || coords[I_COL] > board.size.cols - 1) {
                System.out.println("Coordinates out of the board. Try again");
                invalidInput = true;

            // checking if coordinates are taken
            } else if (board.fields[coords[I_ROW]][coords[I_COL]] != board.emptyField) {
                System.out.println("Coordinates are taken. Try again");
                invalidInput = true;

            } else {
                invalidInput = false;
            }
        } while (invalidInput);

//        scanner.close();

        return coords;
    }

    public void play(int howMany) {
        int game = 1;
        int currentPlayer = 1;
        int[] newCoords;
        boolean quitGame = false;

        System.out.println("Welcome to Gomoku!\n");
        System.out.println("Let's start!\n");
        do {  // Loop for multiple games.
            System.out.println(game + " attempt out of " + howMany + "\n");

            boolean runGame = true;
            do {  // Loop for actual game.
                printBoard();
                System.out.println(" ");

                newCoords = getMove(currentPlayer);
                if (newCoords == null) {  // user wants to quit the game
                    quitGame = true;
                    break;
                }

                mark(currentPlayer, newCoords[I_ROW], newCoords[I_COL]);

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

            if (!runGame) {  // The game is over.
                printResult(currentPlayer);
                currentPlayer = playerSwitch(currentPlayer);
                board.reset();
                game++;
            }

            if (quitGame) break;

        } while (game < howMany);

        System.out.println("Thanks for the game!");
    }

    public boolean hasWon(int player, int howMany) {
        char playerField;

        if (player == 1) {
            playerField = player1.appearance;
        } else {
            playerField = player2.appearance;
        }

        int counter = 1;
        for (int row = 0; row < board.size.rows; row++) {
            for (int col = 0; col < board.size.cols; col++) {
                if (board.fields[row][col] == playerField) {
                    boolean ownField;
                    do {
                        if (
                            // Horizontal
                            col + counter < board.size.cols && board.fields[row][col + counter] == playerField ||
                            // Vertical
                            row + counter < board.size.rows && board.fields[row + counter][col] == playerField
                        ) {
                            counter++;
                            ownField = true;
                        } else {
                            ownField = false;
                        }
                    } while (ownField);
                }
            }
        }
        return counter == 5;
    }

    public boolean isFull() {
        for (char[] chars : board.fields) {
            for (char aChar : chars) {
                if (aChar == board.emptyField) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        StringBuilder header = new StringBuilder();
        header.append("   ");
        for (int i_col = 1; i_col < board.size.cols + 1; i_col++) {
            header.append(i_col);
            header.append(" ");
        }
        System.out.println(header.toString());

        for (int i_row = 0; i_row < board.size.rows; i_row++) {
            StringBuilder line = new StringBuilder();

            line.append((char) (i_row + 65));
            line.append("  ");
            for (int i_col = 0; i_col < board.size.cols; i_col++) {
                line.append(board.fields[i_row][i_col]);
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

    private boolean userWantEndOfGame(String userInput) {
        return userInput.toLowerCase().equals("quit") || userInput.toLowerCase().equals("exit");
    }

    private boolean validateInput(String userInput){
        // One letter and one or two numbers.
        return userInput.matches("^[A-Za-z][0-9][0-9]?$");
    }

    private int[] getCoords(String userInput){
        String str = userInput.substring(0, 1).toUpperCase(); // take out first element ex. A and make it upperCase
        int col = Integer.parseInt(userInput.substring(1)) - 1; // take out second numbers ex. 11
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


class Board {
    char[][] fields;
    char emptyField;
    BoardSize size;

    public Board(int nRows, int nCols, char emptyField) {
        this.emptyField = emptyField;
        this.size = new BoardSize(nRows, nCols);
        makeBoard(emptyField);
    }

    public void reset() {
        makeBoard(this.emptyField);
    }

    private void makeBoard(char emptyField) {
        this.fields = new char[size.rows][size.cols];
        for ( int row = 0; row < size.rows; row++){
            for(int col = 0; col < size.cols; col++){
                fields[row][col] = emptyField;
            }
        }
    }
}


class BoardSize {
    int rows;
    int cols;

    public BoardSize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }
}
