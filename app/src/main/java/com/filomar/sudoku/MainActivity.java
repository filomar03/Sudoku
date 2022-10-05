package com.filomar.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SudokuBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.sudokuBoard);
    }

    public void setNumberOne(View view) {
        gameBoard.manager.setNumber(1);
        gameBoard.invalidate();
    }

    public void setNumberTwo(View view) {
        gameBoard.manager.setNumber(2);
        gameBoard.invalidate();
    }

    public void setNumberThree(View view) {
        gameBoard.manager.setNumber(3);
        gameBoard.invalidate();
    }

    public void setNumberFour(View view) {
        gameBoard.manager.setNumber(4);
        gameBoard.invalidate();
    }

    public void setNumberFive(View view) {
        gameBoard.manager.setNumber(5);
        gameBoard.invalidate();
    }

    public void setNumberSix(View view) {
        gameBoard.manager.setNumber(6);
        gameBoard.invalidate();
    }

    public void setNumberSeven(View view) {
        gameBoard.manager.setNumber(7);
        gameBoard.invalidate();
    }

    public void setNumberEight(View view) {
        gameBoard.manager.setNumber(8);
        gameBoard.invalidate();
    }

    public void setNumberNine(View view) {
        gameBoard.manager.setNumber(9);
        gameBoard.invalidate();
    }

    public void solveClick(View view) {

        gameBoard.manager.solve(gameBoard.manager.getBoard());
        gameBoard.manager.clearSelection();
        gameBoard.invalidate();
    }

    public void clearBoard(View view) {
        gameBoard.manager.clearBoard();
        gameBoard.manager.clearSelection();
        gameBoard.invalidate();
    }
}