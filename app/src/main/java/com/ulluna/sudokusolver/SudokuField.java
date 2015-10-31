package com.ulluna.sudokusolver;

/**
 * This class represents one of 81 Fields/squares of a sudoku grid.
 *
 */

public class SudokuField {

    private int row;
    private int column;
    /**
     * Represents the index of the 3x3 sqare where the SudokuField is located.
     */
    private int square;
    private int value;
    private boolean isFixed;


    /**
     *
     * @param row the row index of the Field (integer from 0 to 8)
     * @param column the column index of the Field (integer from 0 to 8)
     * @param value the value in the Field (integer from 1 to 9)
     * @param isFixed the value indicating if the Field is pre-given in input (boolean true if the Field is pre-given false if the Field is not pre-given)
     */

    public SudokuField(int row, int column, int value, boolean isFixed) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.isFixed = isFixed;
        int a,b;
        int[][] tab = {{0,1,2},{3,4,5},{6,7,8}};
        if(row<3) {
            a=0;
        } else if(row<6){
            a=1;
        } else{
            a=2;
        }
        if(column<3) {
            b=0;
        } else if(column<6){
            b=1;
        } else{
            b=2;
        }
        square=tab[a][b];
    }

    /**
     * Constructor used when converting a string to Sudoku board
     * @param tab 2-element array {row, column}
     * @param value number in the field
     */

    public SudokuField(int[] tab, char value){
        row = tab[0];
        column = tab[1];
        this.value = value-'0';
        isFixed=false;
        if (this.value!=0)
            isFixed = true;

        int a,b;
        int[][] tab2 = {{0,1,2},{3,4,5},{6,7,8}};
        if(row<3) {
            a=0;
        } else if(row<6){
            a=1;
        } else{
            a=2;
        }
        if(column<3) {
            b=0;
        } else if(column<6){
            b=1;
        } else{
            b=2;
        }
        square=tab2[a][b];
    }

    /**
     * Tests whether the Field is pre-given in the input
     * @return true if the Field is pre-given, false if the Field is not pre-given
     */
    public boolean isFixed(){
        return isFixed;
    }

    /**
     * Returns the row index of the Field
     * @return the row index of the Field
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row instance variable to given value
     * @param row new row index (integer from 0 to 8)
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column index of the Field
     * @return the column index of the Field
     */

    public int getColumn() {
        return column;
    }

    /**
     * Sets the column instance variable to given value
     * @param column new column index (integer from 0 to 8)
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Returns the square index of the Field. Square is one of nine 3x3 sets of Fields
     * @return the square index of the Field
     */
    public int getSquare() {
        return square;
    }

    /**
     * Sets the square instance variable to given value
     * @param square new square index (integer from 0 to 8)
     */
    public void setSquare(int square) {
        this.square = square;
    }

    /**
     * Returns the value of the Field (from 1 to 9)
     * @return the value of the Field (from 1 to 9)
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value instance variable to given integer
     * @param value new value (integer from 1 to 9)
     */
    public void setValue(int value) {
        this.value = value;
    }
}

