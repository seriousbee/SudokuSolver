package com.ulluna.sudokusolver;

import java.util.ArrayList;

/**
 * This class represents a whole Sudoku grid.
 */


public class SudokuBoard {

    /**
     * Stores all SudokuFields of the grid
     */
    private ArrayList <SudokuField> grid;
    private boolean solvable = true;

    /**
     * Constructor that lets put in the given values for a Sudoku Puzzle. Example value of tab:
     * {{5,3,0,0,7,0,0,0,0},{6,0,0,1,9,5,0,0,0},
     {0,9,8,0,0,0,0,6,0},{8,0,0,0,6,0,0,0,3},{4,0,0,8,0,3,0,0,1},
     {7,0,0,0,2,0,0,0,6},{0,6,0,0,0,0,2,8,0},{0,0,0,4,1,9,0,0,5},
     {0,0,0,0,8,0,0,7,9}}

     *
     * @param tab 2D array containing 9 arrays (rows) of length 9 (values in the given row). 0 represents an empty field.
     */
    public SudokuBoard(int[][] tab) {
        grid = new ArrayList<SudokuField>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                grid.add(new SudokuField(r, c, 0, false));
            }
        }

        for (int i = 0; i < tab.length; i++) {
            insertFixedElement(tab[i][0], tab[i][1], tab[i][2]);
        }
    }

    /**
     * Constructor that lets put in the given values for a Sudoku Puzzle. Example value of s:
     * "530070000600195000098000060800060003400803001700020006060000280000419005000080079"
     *
     * @param s String of length 81. 0 represents an empty field. Consecutive 9 characters (beginning at index 9*N) represent a row. Every 9th character represents a value in the same column.
     */

    public SudokuBoard(String s){
        while(s.length()<81){
            s+="0";
        }
        if(s.length()>81){
            s=s.substring(0,81);
        }
        grid = new ArrayList<SudokuField>();
        for (int i = 0; i < s.length(); i++) {
            grid.add(new SudokuField(getRowColumn(i), s.charAt(i)));
        }
    }

    /**
     * Method helping to determine index of a given SudokuField on the Sudoku Grid
     * @param row index of the row where a SudokuField is located (int from 0 to 8)
     * @param column index of the row where a SudokuField is located (int from 0 to 8)
     * @return index of given SudokuField on the Sudoku Grid (int from 0 to 80)
     */
    public int getIndex(int row, int column){
        return row*9+column;
    }

    /**
     *  Method helping to determine index of a given SudokuField on the Sudoku Grid
     * @param sudokuField SudokuField object for which we want to find the index
     * @return index of given SudokuField on the Sudoku Grid (int from 0 to 80)
     */
    public int getIndex(SudokuField sudokuField){
        return getIndex(sudokuField.getRow(), sudokuField.getColumn());
    }

    /**
     *
     * @param row index of the row where a SudokuField is located (int from 0 to 8)
     * @param column index of the row where a SudokuField is located (int from 0 to 8)
     * @return returns a SudokuField object located in the given spot on the grid
     */
    public SudokuField getItem(int row, int column){
        return grid.get(getIndex(row, column));
    }

    /**
     * Creates an instance of the SudokuField class and adds it to the grid ArrayList
     * @param row index of the row where a SudokuField is located (int from 0 to 8)
     * @param column index of the row where a SudokuField is located (int from 0 to 8)
     * @param value the value of the SudokuField (int from 1 to 9)
     */
    private void insertFixedElement(int row, int column, int value){
        grid.set(getIndex(row, column), new SudokuField(row, column, value, true));
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < grid.size(); i++) {
            s+=(grid.get(i).getValue());
            if((i-8)%9==0 && i>0) {
                s += "\n";
            }
        }
        return s;
    }

    /**
     *  Determines the "coordinates" of a SudokuField with a given index
     * @param index the index of a SudokuField
     * @return array of length 2, the first value represents the row, the second one the column
     */
    private int[] getRowColumn(int index){
        int[] tab = new int[2];
        tab[0]=index/9;
        tab[1]=index%9;
        return tab;
    }

    /**
     * Determines if the given value can be placed in the given place on the grit according the the Sudoku rules
     * @param w instance of SudokuField class representing a place in the Sudoku grit
     * @param value the value to be checked whether it fits in the given place
     * @return true if the value can be legally put into the grit, false if not
     */
    public boolean isNumberLegal(SudokuField w, int value){
        return checkColumn(w, value) && checkRow(w, value) && checkSquare(w, value);
    }

    /**
     * Determines if the given value can be placed in the given place on the grit according the the Sudoku rules
     * @param row row index of a place in the Sudoku grit
     * @param column column index of a place in the Sudoku grit
     * @param value the value to be checked whether it fits in the given place
     * @return true if the value can be legally put into the grit, false if not
     */

    public boolean isNumberLegal(int row, int column, int value){
        SudokuField w = new SudokuField(row, column, value, false);
        return checkColumn(w, value) && checkRow(w, value) && checkSquare(w, value);
    }

    /**
     * Determines if the given value is already in the 3x3 square where the sudokuField object is located
     * @param sudokuField instance of SudokuField class representing a place in the Sudoku grit
     * @param value the value to be checked whether it fits in the given place
     * @return true if the given value is the only value in the 3x3 square, false if not
     */
    private boolean checkSquare(SudokuField sudokuField, int value){
        int square= sudokuField.getSquare();
        for (int i = 0; i < grid.size(); i++) {
            if(grid.get(i).getSquare()==square){
                if (grid.get(i).getValue()==value){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given value is already in the column where the sudokuField object is located
     * @param sudokuField instance of SudokuField class representing a place in the Sudoku grit
     * @param value the value to be checked whether it fits in the given place
     * @return true if the given value is the only value in the column, false if not
     */

    private boolean checkColumn(SudokuField sudokuField, int value){
        int column= sudokuField.getColumn();
        for(int i = 0; i<9; i++){
            if(getItem(i, column).getValue() == value){
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given value is already in the row where the sudokuField object is located
     * @param sudokuField instance of SudokuField class representing a place in the Sudoku grit
     * @param value the value to be checked whether it fits in the given place
     * @return true if the given value is the only value in the row, false if not
     */

    private boolean checkRow(SudokuField sudokuField, int value){
        int row= sudokuField.getRow();
        for(int i = 0; i<9; i++){
            if(getItem(row, i).getValue() == value){
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the next unfixed SudokuField for a given field. Returns null if the given SudokuField is the last unfixed one.
     * @param sudokuField instance of SudokuField class for which the algorithm has to find the next unfixed SudokuField
     * @return next not fixed SudokuField object in the grit, or null there are no more unfixed SudokuFields
     */
    private SudokuField getNext(SudokuField sudokuField){
        int index = getIndex(sudokuField)+1;
        while(index<grid.size() && grid.get(index).isFixed()){
            index++;
        }
        if(index>=grid.size()){
            return null;
        }
        return grid.get(index);
    }

    /**
     * Finds the previous unfixed SudokuField for a given field. Returns null if the given SudokuField is the first unfixed one.
     * @param sudokuField instance of SudokuField class for which the algorithm has to find the previous unfixed SudokuField
     * @return previous not fixed SudokuField object in the grit, or null there are no more unfixed SudokuFields
     */
    private SudokuField getPrevious(SudokuField sudokuField){
        int index = getIndex(sudokuField)-1;
        while(index>=0 && grid.get(index).isFixed()){
            index--;
        }
        if(index<0){
            return null;
        }
        return grid.get(index);
    }

    /**
     * Finds the lowest (bigger than the previous value) legal number to be put in the given place and updates the value.
     * @param sudokuField instance of the SudokuField class for which the next lowest value should be found and to be updated
     * @return true if there was a bigger valid value, false if there was not bigger valid value
     */
    private boolean incrementValue(SudokuField sudokuField){
        int x = sudokuField.getValue()+1;
        while(!isNumberLegal(sudokuField,x)){
            x++;
        }
        if(x > 9){
            resetValue(sudokuField);
            return false;
        }
        grid.set(getIndex(sudokuField), new SudokuField(sudokuField.getRow(), sudokuField.getColumn(), x, false));
        return true;
    }

    /**
     * Sets the value for the given sudokuField to be reseted (set to 0)
     * @param sudokuField instance of the SudokuField class to have the value reseted
     */
    private void resetValue(SudokuField sudokuField){
        grid.set(getIndex(sudokuField), new SudokuField(sudokuField.getRow(), sudokuField.getColumn(), 0, false));
    }

    /**
     *
     * @return value of the instance variable indicating whether the SudokuBoard is solvable or not
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * The method that starts the process of solving the SudokuBoard. It first finds the lowest unFixed value and then it starts the recursive method solve(SudokuField w) to attempt to solve the puzzle
     */
    public void solveBoard(){
        int index =0;
        while (index<grid.size() && grid.get(index).isFixed()){
            index++;
        }

        solve(grid.get(index));
        if(solvable){
            System.out.println("Solved!");
        }
    }

    /**
     * This is the core method of the algorithm. It involves checking whether there is a valid number to be put in the Field and depending on the result the method is then executed on the next or previous Field
     * @param w instance of the SudokuField class for which the method should be executed
     */
    private void solve(SudokuField w){
        boolean valid = incrementValue(w);
        if(valid && getNext(w)!=null) {
            try {
                solve(getNext(w));
            } catch (StackOverflowError e){
                solvable=false;
                System.out.println(e);
            }
        }else if(!valid && getPrevious(w)!=null){
            try {
                solve(getPrevious(w));
            } catch (StackOverflowError e){
                solvable=false;
                System.out.println(e);
            }
        }else if(!valid && getPrevious(w)==null){
            solvable=false;
        }
    }

    /**
     * Formats the values for output
     * @return returns an array of integers of length 81. Consecutive 9 int (beginning at index 9*N) represent a row. Every 9th int represents a value in the same column.
     */
    public int[] getValues(){
        int[] tab = new int[81];
        for (int i = 0; i < grid.size(); i++) {
            tab[i]=grid.get(i).getValue();
        }
        return tab;
    }
}

