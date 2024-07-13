/*
    Emma S: Farkle is a game that allows the user to be able to play a game involving
    six die where the die are rolled and scored depending on how often they occur to
    their values. If a dice is scored, it no longer can be rolled. The player, at some
    point, can either choose to stop or, when no die can be scored, have their score
    forfeited.
*/


package com.example.emmas.farkle;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton[] buttons = new ImageButton[6];
    int[] buttonState = new int[6];
    int[] diceImage = new int[6];
    int[] diceValue = new int[6];
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_DIE = 2;
    Button roll;
    Button score;
    Button stop;
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;
    int currentScore;
    int totalScore;
    int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the die buttons.
        buttons[0] = (ImageButton)this.findViewById(R.id.imageButton1);
        buttons[1] = (ImageButton)this.findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton)this.findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton)this.findViewById(R.id.imageButton4);
        buttons[4] = (ImageButton)this.findViewById(R.id.imageButton5);
        buttons[5] = (ImageButton)this.findViewById(R.id.imageButton6);
        for (int a = 0; a < buttons.length; a++) {
            buttons[a].setOnClickListener(this);
            buttons[a].setEnabled(false);
            buttons[a].setBackgroundColor(Color.LTGRAY);
        }
        // Set up rest of the buttons.
        roll = (Button)this.findViewById(R.id.button1);
        roll.setOnClickListener(this);
        score = (Button)this.findViewById(R.id.button2);
        score.setOnClickListener(this);
        score.setEnabled(false);
        stop = (Button)this.findViewById(R.id.button3);
        stop.setOnClickListener(this);
        stop.setEnabled(false);
        currentScoreTV = (TextView)this.findViewById(R.id.textView1);
        totalScoreTV = (TextView)this.findViewById(R.id.textView2);
        currentRoundTV = (TextView)this.findViewById(R.id.textView3);
        diceImage[0] = R.drawable.dice1;
        diceImage[1] = R.drawable.dice2;
        diceImage[2] = R.drawable.dice3;
        diceImage[3] = R.drawable.dice4;
        diceImage[4] = R.drawable.dice5;
        diceImage[5] = R.drawable.dice6;

    }

    @Override
    public void onClick(View v) {
        if (v.equals(roll)) {
            for (int a = 0; a < buttons.length; a++) {
                if (buttonState[a] == HOT_DIE) {
                    // Randomly assign die values.
                    int choice = (int)(Math.random() * 6);
                    diceValue[a] = choice;
                    buttons[a].setImageResource(diceImage[choice]);
                    buttons[a].setEnabled(true);
                    roll.setEnabled(false);
                    score.setEnabled(true);
                    stop.setEnabled(false);
                }
            }

        }
        else if (v.equals(score)){
            int[] valueCount = new int[7];
            for (int a = 0; a < buttonState.length; a++){
                if (buttonState[a] == SCORE_DIE) {
                    valueCount[diceValue[a] + 1]++;
                }
            }
            if ((valueCount[2] > 0 && valueCount[2] < 3) ||
                (valueCount[3] > 0 && valueCount[3] < 3) ||
                (valueCount[4] > 0 && valueCount[4] < 3) ||
                (valueCount[6] > 0 && valueCount[6] < 3)) {
                // Alert users they selected invalid die.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Die selected are not scorable.");
                alertDialogBuilder
                        .setMessage("Please select only scorable die.")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else if (valueCount[1] == 0 && valueCount[2] == 0 &&
                    valueCount[3] == 0 && valueCount[4] == 0 &&
                    valueCount[5] == 0 && valueCount[6] == 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("No score!");
                    alertDialogBuilder
                            .setMessage("Forfeit score and go to next round?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    currentScore = 0;
                                    currentRound++;
                                    currentScoreTV.setText("Current Score: " + currentScore);
                                    currentRoundTV.setText("Current Round: " + currentRound);
                                    resetDice();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
            }
            else {
                if (valueCount[1] < 3) {
                    currentScore += (valueCount[1] * 100);
                }
                if (valueCount[5] < 3) {
                    currentScore += (valueCount[5] * 50);
                }
                if (valueCount[1] >= 3) {
                    currentScore += ((valueCount[1] - 2) * 1000);
                }
                if (valueCount[2] >= 3) {
                    currentScore += ((valueCount[2] - 2) * 200);
                }
                if (valueCount[3] >= 3) {
                    currentScore += ((valueCount[3] - 2) * 300);
                }
                if (valueCount[4] >= 3) {
                    currentScore += ((valueCount[4] - 2) * 400);
                }
                if (valueCount[5] >= 3) {
                    currentScore += ((valueCount[5] - 2) * 500);
                }
                if (valueCount[6] >= 3) {
                    currentScore += ((valueCount[6] - 2) * 600);
                }
            }
            currentScoreTV.setText("Current Score: " + currentScore);
            int lockedCount = 0;
            for (int a = 0; a < buttons.length; a++) {
                if (buttonState[a] == LOCKED_DIE) {
                    lockedCount++;
                }
            }
            if (lockedCount == 6) {
                for (int a = 0; a < buttons.length; a++) {
                    buttonState[a] = HOT_DIE;
                    buttons[a].setBackgroundColor(Color.LTGRAY);
                }
            }
            resetDice();
        }
        else if (v.equals(stop)){
            totalScore += currentScore;
            currentScore = 0;
            currentScoreTV.setText("Current Score: " + currentScore);
            totalScoreTV.setText("Total Score: " + totalScore);
            currentRound++;
            currentRoundTV.setText("Current Round: " + currentRound);
            for (int a = 0; a < buttons.length; a++) {
                buttons[a].setEnabled(false);
                buttonState[a] = HOT_DIE;
                buttons[a].setBackgroundColor(Color.LTGRAY);
            }
            roll.setEnabled(true);
            score.setEnabled(false);
            stop.setEnabled(false);
        }
        else {
            for (int a = 0; a < buttons.length; a++){
                if (v.equals(buttons[a])) {
                    if (buttonState[a] == HOT_DIE) {
                        buttons[a].setBackgroundColor(Color.RED);
                        buttonState[a] = SCORE_DIE;
                    }
                    else {
                        buttons[a].setBackgroundColor(Color.LTGRAY);
                        buttonState[a] = HOT_DIE;
                    }
                }
            }
        }
    }

    private void resetDice() {
        for (int a = 0; a < buttons.length; a++) {
            if (buttonState[a] == SCORE_DIE) {
                buttonState[a] = LOCKED_DIE;
                buttons[a].setBackgroundColor(Color.BLUE);
                buttons[a].setEnabled(false);
            }
        }
        roll.setEnabled(true);
        score.setEnabled(false);
        stop.setEnabled(true);
    }
}
