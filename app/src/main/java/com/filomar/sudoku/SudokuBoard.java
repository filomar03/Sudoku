package com.filomar.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {
    //attributes from activity.xml
    private final int boardColor;
    private final int primarySelectionColor;
    private final int secondarySelectionColor;
    private final int givenNumberColor;
    private final int solvedNumberColor;

    //useful data about view size
    private float fixedSize;
    private float cellSize;
    private float offset;

    //paints
    private final Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint cellHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint sudokuNumbersPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //rect for sudoku numbers
    private final Rect sudokuNumbersRect = new Rect();

    //sudoku manager object
    public final SudokuManager manager = new SudokuManager();

    //constructor of the view object + attributes gathering
    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0 ,0);

        try {
            boardColor = attributes.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            primarySelectionColor = attributes.getInteger(R.styleable.SudokuBoard_primarySelectionColor, 0);
            secondarySelectionColor = attributes.getInteger(R.styleable.SudokuBoard_secondarySelectionColor, 0);
            givenNumberColor = attributes.getInteger(R.styleable.SudokuBoard_givenNumbersColor, 0);
            solvedNumberColor = attributes.getInteger(R.styleable.SudokuBoard_solvedNumbersColor, 0);
        } finally {
            attributes.recycle();
        }
    }

    //setting view size
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        setMeasuredDimension(size, size);
    }

    //drawing in the canvas
    @Override
    protected void onDraw(Canvas canvas) {
        highlightSelectedCell(canvas);
        drawBoard(canvas);
        drawNumbers(canvas);
    }

    //draw sudoku board
    private void drawBoard(Canvas canvas) {
        fixedSize = ((float) getWidth()) / 100 * 90;
        cellSize = fixedSize / 9;
        offset = (getWidth() - fixedSize) / 2;

        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setColor(boardColor);
        drawThickLine();

        canvas.drawRect(offset, offset, getWidth() - offset, getHeight() - offset, boardPaint);

        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }

            canvas.drawLine(cellSize * i + offset, offset, cellSize * i + offset, getHeight() - offset, boardPaint);
            canvas.drawLine(offset, cellSize * i + offset, getWidth() - offset, cellSize * i + offset, boardPaint);
        }
    }

    private void drawThickLine() {
        boardPaint.setStrokeWidth(12);
    }

    private void drawThinLine() {
        boardPaint.setStrokeWidth(5);
    }

    private void highlightSelectedCell(Canvas canvas) {
        if (manager.getCurrentRow() != -1 && manager.getCurrentColumn() != -1) {
            cellHighlightPaint.setStyle(Paint.Style.FILL);
            cellHighlightPaint.setColor(secondarySelectionColor);

            canvas.drawRect(offset, manager.getCurrentColumn() * cellSize + offset, fixedSize + offset, (manager.getCurrentColumn() + 1) * cellSize + offset, cellHighlightPaint);
            canvas.drawRect(manager.getCurrentRow() * cellSize + offset, offset, (manager.getCurrentRow() + 1) * cellSize + offset, fixedSize + offset, cellHighlightPaint);

            cellHighlightPaint.setColor(primarySelectionColor);

            canvas.drawRect(manager.getCurrentRow() * cellSize + offset, manager.getCurrentColumn() * cellSize + offset, (manager.getCurrentRow() + 1) * cellSize + offset, (manager.getCurrentColumn() + 1) * cellSize + offset, cellHighlightPaint);
        }
    }

    private void drawNumbers(Canvas canvas) {

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (manager.getBoardNumberAtPosition(row, column) != 0) {
                    String number = Integer.toString(manager.getBoardNumberAtPosition(row, column));
                    sudokuNumbersPaint.getTextBounds(number, 0, number.length(), sudokuNumbersRect);
                    float width = sudokuNumbersRect.width();
                    float height = sudokuNumbersRect.height();

                    sudokuNumbersPaint.setTextSize(cellSize / 1.5f);
                    sudokuNumbersPaint.setColor(givenNumberColor);

                    canvas.drawText(number, row * cellSize + offset + cellSize / 2 - width / 2, column * cellSize + offset + cellSize / 2 + height / 2, sudokuNumbersPaint);
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() - offset;
        float y = event.getY() - offset;

        boolean isValid = event.getAction() == MotionEvent.ACTION_DOWN && (x > 0 && x < fixedSize) && (y > 0 && y < fixedSize);

        if (isValid) {
            int oldRow = manager.getCurrentRow();
            int oldColumn = manager.getCurrentColumn();
            manager.setCurrentRow((int) Math.floor(x / cellSize));
            manager.setCurrentColumn((int) Math.floor(y / cellSize));

            if (oldRow == manager.getCurrentRow() && oldColumn == manager.getCurrentColumn()) {
                manager.setNumber(0);
            }

            invalidate();
        }

        return isValid;
    }

    //init Paint() to call it only once instead of every invalidate()
}
