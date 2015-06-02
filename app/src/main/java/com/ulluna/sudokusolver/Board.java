package com.ulluna.sudokusolver;

import java.util.ArrayList;

/**
 * Created by Tomasz on 5/26/2015.
 */


public class Board {

    private ArrayList <Window> grid;
    private boolean solvable = true;
    public Board(int[][] tab) {
        grid = new ArrayList<Window>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                grid.add(new Window (r, c, 0, false));
            }
        }

        for (int i = 0; i < tab.length; i++) {
            insertFixedElement(tab[i][0], tab[i][1], tab[i][2]);
        }
    }

    public Board(String s){
        while(s.length()<81){
            s+="0";
        }
        if(s.length()>81){
            s=s.substring(0,81);
        }
        grid = new ArrayList<Window>();
        for (int i = 0; i < s.length(); i++) {
            grid.add(new Window(getRowColumn(i), s.charAt(i)));
        }
    }

    public int getIndex(int row, int column){
        return row*9+column;
    }

    public int getIndex(Window window){
        return getIndex(window.getRow(), window.getColumn());
    }

    public Window getItem(int row, int column){
        return grid.get(getIndex(row, column));
    }

    private void insertFixedElement(int row, int column, int value){
        grid.set(getIndex(row, column), new Window(row, column, value, true));
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

    private int[] getRowColumn(int index){
        int[] tab = new int[2];
        tab[0]=index/9;
        tab[1]=index%9;
        return tab;
    }

    public boolean isNumberLegal(Window w, int value){
        return checkColumn(w, value) && checkRow(w, value) && checkSquare(w, value);
    }

    public boolean isNumberLegal(int row, int column, int value){
        Window w = new Window(row, column, value, false);
        return checkColumn(w, value) && checkRow(w, value) && checkSquare(w, value);
    }

    private boolean checkSquare(Window window, int value){
        int square=window.getSquare();
        for (int i = 0; i < grid.size(); i++) {
            if(grid.get(i).getSquare()==square){
                if (grid.get(i).getValue()==value){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkColumn(Window window, int value){
        int column=window.getColumn();
        for(int i = 0; i<9; i++){
            if(getItem(i, column).getValue() == value){
                return false;
            }
        }
        return true;
    }

    private boolean checkRow(Window window, int value){
        int row=window.getRow();
        for(int i = 0; i<9; i++){
            if(getItem(row, i).getValue() == value){
                return false;
            }
        }
        return true;
    }

    private Window getNext(Window window){
        int index = getIndex(window)+1;
        while(index<grid.size() && grid.get(index).isFixed()){
            index++;
        }
        if(index>=grid.size()){
            return null;
        }
        return grid.get(index);
    }

    private Window getPrevious(Window window){
        int index = getIndex(window)-1;
        while(index>=0 && grid.get(index).isFixed()){
            index--;
        }
        if(index<0){
            return null;
        }
        return grid.get(index);
    }

    private boolean incrementValue(Window window){
        int x = window.getValue()+1;
        while(!isNumberLegal(window,x)){
            x++;
        }
        if(x > 9){
            resetValue(window);
            return false;
        }
        grid.set(getIndex(window), new Window(window.getRow(), window.getColumn(), x, false));
        return true;
    }

    private void resetValue(Window window){
        grid.set(getIndex(window), new Window(window.getRow(), window.getColumn(), 0, false));
    }

    public boolean isSolvable() {
        return solvable;
    }

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

    private void solve(Window w){
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

    public int[] getValues(){
        int[] tab = new int[81];
        for (int i = 0; i < grid.size(); i++) {
            tab[i]=grid.get(i).getValue();
        }
        return tab;
    }
}

