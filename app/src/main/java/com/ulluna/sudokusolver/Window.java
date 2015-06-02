package com.ulluna.sudokusolver;

/**
 * Created by Tomasz on 5/26/2015.
 */


public class Window {

    private int row;
    private int column;
    private int square;
    private int value;
    private boolean isFixed;

    public Window(int row, int column, int value, boolean isFixed) {
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

    public Window(int[] tab, char value){
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

    public boolean isFixed(){
        return isFixed;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

