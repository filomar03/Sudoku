package com.filomar.sudoku;

import android.widget.TextView;
import android.widget.Toast;

public class SudokuManager {
    private int currentRow, currentColumn;
    private int[][] board;
    private int[][] solvedBoard;

    SudokuManager() {
        currentRow = -1;
        currentColumn = -1;

        board = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                board[row][column] = 0;
            }
        }
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public void clearSelection() {
        setCurrentRow(-1);
        setCurrentColumn(-1);
    }

    public int[][] getBoard() {
        return board;
    }

    public void clearBoard() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                board[row][column] = 0;
            }
        }
    }

    public int getBoardNumberAtPosition(int row, int column) {
        return board[row][column];
    }

    public void setNumber(int num) {
        if (currentRow != -1 && currentColumn != -1) {
            if (num != 0) {
                if (isLegal(currentRow, currentColumn, num)) {
                    board[currentRow][currentColumn] = num;
                }
            } else {
                board[currentRow][currentColumn] = num;
            }
        }
    }

    public boolean isLegal(int _row, int _column, int number) {
        //check row
        for (int row = 0; row < 9; row++) {
            if (board[row][_column] == number) {
                return false;
            }
        }

        //check column
        for (int column = 0; column < 9; column++) {
            if (board[_row][column] == number) {
                return false;
            }
        }

        //check chunk
        int chunkRow = (int) Math.floor(_row / 3) * 3;
        int chunkColumn = (int) Math.floor(_column / 3) * 3;

        for (int row = chunkRow; row < chunkRow + 3; row++) {
            for (int column = chunkColumn; column < chunkColumn + 3; column++) {
                if (board[row][column] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean solve(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    for (int n = 1; n <= 9; n++) {
                        if (isLegal(r, c, n)) {
                            board[r][c] = n;
                            if (solve(board)) {
                                return true;
                            } else {
                                board[r][c] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        this.board = board;
        return true;
    }
}
