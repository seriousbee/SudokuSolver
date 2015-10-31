package com.ulluna.sudokusolver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    int[] solvedBoard = new int[81];
    SudokuBoard sudokuBoard;
    private final int[] idArray = {
            R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25, R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35, R.id.button36, R.id.button37, R.id.button38, R.id.button39, R.id.button40, R.id.button41, R.id.button42, R.id.button43, R.id.button44, R.id.button45, R.id.button46, R.id.button47, R.id.button48, R.id.button49, R.id.button50, R.id.button51, R.id.button52, R.id.button53, R.id.button54, R.id.button55, R.id.button56, R.id.button57, R.id.button58, R.id.button59, R.id.button60, R.id.button61, R.id.button62, R.id.button63, R.id.button64, R.id.button65, R.id.button66, R.id.button67, R.id.button68, R.id.button69, R.id.button70, R.id.button71, R.id.button72, R.id.button73, R.id.button74, R.id.button75, R.id.button76, R.id.button77, R.id.button78, R.id.button79, R.id.button80, R.id.button81};

    private final int[] numberButtonsArray = {R.id.button101, R.id.button102, R.id.button103, R.id.button104, R.id.button105, R.id.button106, R.id.button107, R.id.button108, R.id.button109};

    private Button[] numberButtons = new Button[9];
    private Button[] buttons = new Button[81];
    Button pressed;

    ArrayList<Button> givenButtons = new ArrayList<Button>();
    boolean visibility = true;
    boolean solved = false;
    boolean solvable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 81; i++) {
                    buttons[i] = (Button)findViewById(idArray[i]);
                    buttons[i].setText("");
                    buttons[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            onGridButtonClick(v);
                        }
                    });
                }
                for(int i=0; i<9; i++){
                    numberButtons[i] = (Button)findViewById(numberButtonsArray[i]);
                    numberButtons[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            onNumberButtonPressed(v);
                        }
                    });
            }
        }
        };
        thread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            clearBoard();
        }
        if(id == R.id.action_solve){
            solveSudoku();
        }
        if(id==R.id.action_visibility){
            changeVisibility(item);
        }
        if(id==R.id.action_remove){
            removeButton();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onGridButtonClick(View v){
        Button button = (Button)findViewById(v.getId());
        if(pressed!=null) {
            pressed.setBackgroundResource(R.drawable.button_enabled);
        } //if a user clicks one field and then moves to the other one
        //the UI should "uncheck" the previous field

        //it only makes sense to change a value of a field if the puzzle
        //has not been solved yet
        if(!solved)
            button.setBackgroundResource(R.drawable.button_pressed);
        //save the ID of the pressed button to handle the event of putting in
        //a number
        pressed = button;

        //if the puzzle is solved, yet the user chose not to display the answers,
        //the app will show the correct number for the field.
        if(solved && !visibility && solvable){
            //we have the ID of the button, but we do not know where in the grid
            //is the button located. We need to find the location in the buttons array
            for (int j = 0; j < buttons.length; j++) {
                if(pressed.equals(buttons[j])){
                    pressed.setText(String.valueOf(solvedBoard[j]));
                    break;
                }
            }
        }
    }

    public void clearBoard(){
        solvedBoard = new int[81];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        }
        solved=false;
        givenButtons.clear();
    }

    public void onNumberButtonPressed(View v){
        if(pressed != null){
            givenButtons.add(pressed);
            pressed.setText(((Button) findViewById(v.getId())).getText());
            pressed.setBackgroundResource(R.drawable.button_enabled);
            pressed = null;
        }
    }

    public void removeButton(){
        if(pressed != null && !solved){
            givenButtons.remove(pressed);
            pressed.setText("");
            pressed.setBackgroundResource(R.drawable.button_enabled);
            pressed=null;
        }
    }

    public void changeVisibility(MenuItem item){
        visibility=!visibility;
        if(visibility){
            item.setIcon(R.drawable.visible);
            item.setTitle("Hide answers");
            Toast.makeText(getApplicationContext(), "Answers are now shown",  Toast.LENGTH_SHORT).show();
            if(solvable && solved){
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setText(String.valueOf(solvedBoard[i]));
                }
            }
        } else {
            item.setIcon(R.drawable.invisible);
            item.setTitle("Show answers");
            Toast.makeText(getApplicationContext(), "Answers are now hidden",  Toast.LENGTH_SHORT).show();
        }
    }

    public String getTheValuesString(){
        String s="";
        for (int i = 0; i < buttons.length; i++) {
            if(buttons[i].getText().toString().isEmpty()){
                s+=0;
            }
            else{
                s+=buttons[i].getText().toString();
            }
        }
        return s;
    }

    public void solveSudoku(){
        if (pressed!=null){
            pressed.setBackgroundResource(R.drawable.button_enabled);
            pressed=null;
        }
        if(solved){
            if (solvable){
                Toast.makeText(getApplicationContext(), "The puzzle has already been solved.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getApplicationContext(), "The puzzle is unsolvable.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        sudokuBoard = new SudokuBoard(getTheValuesString());
        sudokuBoard.solveBoard();
        solvable= sudokuBoard.isSolvable();
        solved = true;
        if(solvable) {
            solvedBoard = sudokuBoard.getValues();
            if(visibility){
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setText(String.valueOf(solvedBoard[i]));
                }
            } else{
                Toast.makeText(getApplicationContext(), "The puzzle is solved.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Sudoku is unsolvable", Toast.LENGTH_SHORT).show();
        }
    }

}
