package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //need to m make references to the declared textview variables
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        //For loop used to loop to assign button array to references of buttons could be done individually but for ease use a nested loop
        for (int i = 0; i < 3; i++) {
            //i<3 because we want to go 3 rounds
            for (int j = 0; j < 3; j++) {
                //the nested loop with i and j will loop through all of the columns in the 2D button array
                //button ID's already allocated in the activity main.xml file. This allows to call the ID in nested loop
                String buttonID = "button_" + i + j; //the +i and +j appends the rows and the columns. It will loop through all the created button ID
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //this resource ID is passed to be able to viewbyid
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                //passes through onclick which means when clicked on
            }
    }

        //this is the assignment for the reset button
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buttonclick to reset game board
                resetGame();
            }
        });
    }



    @Override
    public void onClick(View v) {
        //checks if the button that was clicked contains a string
        //if not the case then there is already an x or an o string inside the button so just return
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        //if above method has an empty string then:
        //also takes care of changing text in the button
        if (player1Turn) { //checks if player turn 1 = true
            ((Button) v).setText("X");//set text to X on click
        } else {//else means that it is player 2 turn
            ((Button) v).setText("O");//set text to O on click
        }
        //need to increment roundcount because we know that one more round is completed
        roundCount++;
        //need to check for win
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
                //this would be where player 1 wins
            } else {
                player2Wins();
                //this is where player 2 wins
            }
        } else if (roundCount == 9) {
            draw();
            //checks roundcounter for 9 lines and can be a draw
        } else {
            player1Turn = !player1Turn;
            //If there is no win or draw then we will switch turns
        }
    }

    //this method will return true of false if somebody won or not
    //go through all rows columns and diagonals to see if somebody won
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        //need to get all button text into string array with another nested loop
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
                //go through all buttons and save them in string array
            }
        }
        //string array to go through all of rows and columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])//compares fields that are next to each other
                    && field[i][0].equals(field[i][2])//comparing fields next and after
                    && !field[i][0].equals("")) {//makes sure its not 3 empty fields
                //checks rows
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            //this one will go through columns instead of rows in the above loop
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        //checks for the diagonals
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        //checks for the diagonals
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
        //checking for win methods
        private void player1Wins()
        {
            //player 1 win
            player1Points++;
            Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
            //send an output to show that the player 1 wins in a popup dialouge
            updatePointsText();
            //method will update amount of points player won
            resetBoard();
            //method will reset game board
        }
        private void player2Wins()
        {
            //player 2 win
            player2Points++;
            Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
            //send an output to show that the player 1 wins in a popup dialouge
            updatePointsText();
            //method will update amount of points player won
            resetBoard();
            //method will reset game board
        }
        private void draw()
        {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            resetBoard();
            //draw
        }
        private void updatePointsText() {
        //updates the player win count by getting from player1 and2points
            textViewPlayer1.setText("Player 1: " + player1Points);
            textViewPlayer2.setText("Player 2: " + player2Points);
        }
       private void resetBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText("");
                    //loop through all rows and columns and set the button text equal to nothing
            }
        }
            roundCount = 0;
            player1Turn = true;
            //resets roundcounter number and sets player 1 turn back to true
    }

        private void resetGame(){
        //resets gameboard by setting player 1 and 2 points to 0 and resetting the game board
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
        }
}