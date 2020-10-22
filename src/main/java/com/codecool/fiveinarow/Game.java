package com.codecool.fiveinarow;


import java.util.Scanner;

public class Game implements GameInterface {

    private final char BOARD_FILLING = '*';

    private final char PLAYER_ONE = 'X';
    private final char PLAYER_TWO = '0';

    private final int ROW_IDX = 0;
    private final int COL_IDX = 1;

    private char[][] board;

    private final int rowNum;
    private final int colNum;

    public Game(int nRows, int nCols) {
        // make a board of nRows & nCols
        rowNum = nRows;
        colNum = nCols;
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
        int[] coords = new int[2];

        Scanner scanner = new Scanner(System.in);
        String userInput;

        boolean invalidInput = false;
        do {
            System.out.println("Player " + player + " enter Your coordinates:");
            userInput = scanner.nextLine();

            coords = validateInput(userInput);
            // checking if input is not too long
            if (userInput.length() > 3) {
                System.out.println("Coordinates too long");
                invalidInput = true;

                // checking if coordinates are inside the board
            } else if (coords[ROW_IDX] > this.rowNum || coords[COL_IDX] > this.colNum) {
                System.out.println("Coordinates out of the board. Try again");
                invalidInput = true;

                // checking if coordinates are taken
            } else if (this.board[ROW_IDX][COL_IDX] != BOARD_FILLING) {
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
            this.board[row][col] = PLAYER_ONE;
        }
        this.board[row][col] = PLAYER_TWO;
    }

    public boolean hasWon(int player, int howMany) {
        return false;
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
        for (int i_col = 0; i_col < this.colNum; i_col++) {
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
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {

        int game = 0;
        int currentPlayer = 1;
        System.out.println("Welcome to Gomoku!\n");
        while (game < howMany) {
            System.out.println("Let's start!\n");
            System.out.println(game + 1 + " attempt out of " + howMany);
            while (true) {
                printBoard();
                int[] newCoords = getMove(currentPlayer);
                mark(currentPlayer, newCoords[ROW_IDX], newCoords[COL_IDX]);
                if (hasWon(currentPlayer, howMany)) {
                    System.out.println("Player " + currentPlayer + " has won! Congratulations!");
                    System.out.println("Try Again!\n");
                    currentPlayer = playerSwitch(currentPlayer);
                    game++;
                    break;
                }
                if (isFull()) {
                    System.out.println("The board is full.");
                    System.out.println("Try Again!\n");
                    currentPlayer = playerSwitch(currentPlayer);
                    game++;
                    break;
                }
                currentPlayer = playerSwitch(currentPlayer);
            }
        }
        System.out.println("Thanks for the game!");
    }

    // added validation method for user input
    public int[] validateInput(String inputUser){
        // if it has two elements

        String str = inputUser.substring(0, 1).toUpperCase(); // take out first element ex. A and make it upperCase
        int col = Integer.parseInt(inputUser.substring(1)) - 1; // take out second numbers ex. 11
        char rowAsChar = str.charAt(0);
        int row = rowAsChar - 65;

        return new int[] { row, col };
    }

    public int playerSwitch(int player) {
        return player == 1 ? 2: 1;
    }

}

