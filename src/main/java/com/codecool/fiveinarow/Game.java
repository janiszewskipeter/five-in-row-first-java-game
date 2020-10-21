package com.codecool.fiveinarow;



import java.util.Scanner;

public class Game implements GameInterface {

    private final int ROW_IDX = 0;
    private final int COL_IDX = 1;

    private int[][] board;

    private final int rowNum;
    private final int colNum;

    public Game(int nRows, int nCols) {
        // make a board of nRows & nCols
        rowNum = nRows;
        colNum = nCols;
        board = new int[nRows][nCols];
        for ( int row = 0; row < nRows; row++){
            for(int col = 0; col < nCols; col++){
                board[row][col] = '*';
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {

        int[] coords = new int[2];

        Scanner scanner = new Scanner(System.in);
        System.out.println("Player" + player + "enter Your coordinates:");

        String userInput = null;
        while (!isFull()){
            userInput = scanner.nextLine();
            coords = validateInput(userInput);
            // checking if input is not too long
            if (userInput.length() > 3){
                System.out.println("Coordinates too long");
                System.out.println("Player" + player + "enter Your coordinates:");
                continue;
            // checking if coordinates are inside the board
            } else if (coords[ROW_IDX] > this.rowNum || coords[COL_IDX] > this.colNum) {
                System.out.println("Coordinates out of the board. Try again");
                System.out.println("Player" + player + "enter Your coordinates:");
                continue;
            // checking if coordinates are taken
            } else if (this.board[ROW_IDX][COL_IDX] != 0) {
                System.out.println("Coordinates are taken. Try again");
                System.out.println("Player" + player + "enter Your coordinates:");
                continue;
            }
            // end if ok
            break;
        }
        scanner.close();

        return coords;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        if (player == 1) {
            this.board[row][col] = 'X';
        }
        this.board[row][col] = '0';
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public void printBoard() {
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
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

