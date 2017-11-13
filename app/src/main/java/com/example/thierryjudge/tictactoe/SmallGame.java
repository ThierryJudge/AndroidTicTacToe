package com.example.thierryjudge.tictactoe;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.x;

public class SmallGame extends AppCompatActivity {


    GridLayout gridLayout;
    TButton[][] tButtons = new TButton[3][3];

    TextView turnText;

    GameController gameController = new GameController();

    char[][] grid = new char[3][3];

    boolean xTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_game);

        turnText = (TextView) findViewById(R.id.textView);

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(3);

        ActionBar.LayoutParams layoutParams =
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++)
            {
                tButtons[i][j] = new TButton(this, i, j);
                gridLayout.addView(tButtons[i][j], layoutParams);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 300;
                params.height = 300;
                tButtons[i][j].setLayoutParams(params);
                tButtons[i][j].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        buttonClick(v);
                    }
                });
            }
        }
    }


    public void buttonClick(View v)
    {
        TButton button = (TButton) v;
        button.played = true;

        if(xTurn)
        {
            button.setText("X");
            grid[button.row][button.column] = 'X';
            turnText.setText("Turn: O");

        }
        else
        {
            button.setText("O");
            grid[button.row][button.column] = 'O';
            turnText.setText("Turn: X");
        }
        xTurn = !xTurn;

        int winResult = gameController.checkWin(grid);
        if(winResult == 1)
        {
            Log.d("TEST", "X win");
            disableAll();
            Toast.makeText(this, "X WIN", Toast.LENGTH_LONG).show();
            turnText.setText("X WIN");
            return;
        }
        else if(winResult == 2)
        {
            Log.d("TEST", "O win");
            disableAll();
            Toast.makeText(this, "O WIN", Toast.LENGTH_LONG).show();
            turnText.setText("O WIN");
            return;
        }
        else if(winResult == 3)
        {
            Log.d("TEST", "DRAW");
            disableAll();
            Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show();
            turnText.setText("DRAW");
            return;
        }

        if(!xTurn) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if celll is empty
                    if (grid[i][j] != 'X' || grid[i][j] != 'O') {
                        // Make the move
                        grid[i][j] = 'O';

                        // compute evaluation function for this
                        // move.
                        int moveVal = gameController.miniMax(grid, 0, true);

                        // Undo the move
                        grid[i][j] = '_';
                        Log.d("TEMP", "MOVE: "+ i + ", " + j + " :" + moveVal);
                    }
                }
            }
        }


    }


    public void disableAll()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                tButtons[i][j].setEnabled(false);
            }
        }


    }



}
