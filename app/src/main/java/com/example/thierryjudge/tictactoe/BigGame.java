package com.example.thierryjudge.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Stack;

public class BigGame extends AppCompatActivity {

    GridLayout gridLayout;
    GridLayout[][] gridLayouts = new GridLayout[3][3];
    TButton[][][][] tButtons = new TButton[3][3][3][3];


    GameController gameController = new GameController();

    TextView turnText;

    char[][][][] grids = new char[3][3][3][3];
    char[][] grid = new char[3][3];

    Stack gridStack = new Stack();
    boolean xTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_game);

        turnText = (TextView) findViewById(R.id.textView);

        initGrid();

    }

    private void initGrid()
    {
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(3);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

        GridLayout.LayoutParams parameters = new GridLayout.LayoutParams();
        parameters.setGravity(Gravity.CENTER_HORIZONTAL);

        //set grid layouts
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                gridLayouts[i][j] = new GridLayout(this);
                gridLayouts[i][j].setRowCount(3);
                gridLayouts[i][j].setColumnCount(3);
                gridLayouts[i][j].setBackgroundColor(Color.WHITE);
                gridLayouts[i][j].setPadding(7,7,7,7);

                gridLayout.addView(gridLayouts[i][j], layoutParams);
                //gridLayouts[i].setLayoutParams(parameters);
            }
        }


        //set buttons in grid layouts
        for(int x = 0; x < 3; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                for (int i = 0; i < 3; i++) {

                    for (int j = 0; j < 3; j++)
                    {
                        tButtons[x][y][i][j] = new TButton(this, x, y, i, j);
                        gridLayouts[x][y].addView(tButtons[x][y][i][j], layoutParams);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.width = 115;
                        params.height = 115;
                        tButtons[x][y][i][j].setLayoutParams(params);
                        tButtons[x][y][i][j].setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                buttonClick(v);
                            }
                        });
                    }
                }
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
            grids[button.gridRow][button.gridColumn][button.row][button.column] = 'X';
            turnText.setText("Turn: O");

        }
        else
        {
            button.setText("O");
            grids[button.gridRow][button.gridColumn][button.row][button.column] = 'O';
            turnText.setText("Turn: X");
        }
        xTurn = !xTurn;


        checkLocalWin(button);

        if(checkGlobalWin(button))
        {
            return;
        }

        if(setGrids(button))
        {
            gridStack.push(button.gridRow);
            gridStack.push(button.gridColumn);
            Log.d("TEST", "PUSH: " + button.gridRow + ", " + button.gridColumn);
        }
    }


    //functions to move grids.
    public boolean setGrids(TButton button)
    {
        disableAll();
        if (grid[button.row][button.column] != 'X' && grid[button.row][button.column] != 'O'&& grid[button.row][button.column] != 'D' )
        {
            enableGrid(button.row, button.column);
            return true;
        }
        else if(grid[button.gridRow][button.gridColumn] != 'X' && grid[button.gridRow][button.gridColumn] != 'O' && grid[button.row][button.column] != 'D')
        {
            enableGrid(button.gridRow, button.gridColumn);
            return false;
        }
        else
        {
            //pop from stack;
            int c = (int) gridStack.pop();
            int r = (int) gridStack.pop();
            while(grid[r][c] == 'X' || grid[r][c] == 'O'|| grid[r][c] == 'D')
            {
                c = (int) gridStack.pop();
                r = (int) gridStack.pop();
            }
            Log.d("TEST", "ENABLE AT: " + r + ", " + c);
            enableGrid(r, c);
            return false;
        }
    }
    public void disableAll()
    {
        for(int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        tButtons[x][y][i][j].setEnabled(false);
                        tButtons[x][y][i][j].setBackgroundResource(android.R.drawable.btn_default);
                    }
                }
            }
        }
    }
    public void enableGrid(int gridRow, int gridColumn)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if(!tButtons[gridRow][gridColumn][i][j].played)
                {
                    tButtons[gridRow][gridColumn][i][j].setEnabled(true);
                    tButtons[gridRow][gridColumn][i][j].setBackgroundResource(android.R.drawable.presence_online);
                }
            }
        }
    }



    public void checkLocalWin(TButton button)
    {
        if(gameController.checkWin(grids[button.gridRow][button.gridColumn]) == 1)
        {
            Log.d("TEST", "X win at: "+ button.gridRow + ", " + button.gridColumn);
            gridLayouts[button.gridRow][button.gridColumn].setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
            grid[button.gridRow][button.gridColumn] = 'X';

        }
        if(gameController.checkWin(grids[button.gridRow][button.gridColumn]) == 2)
        {
            Log.d("TEST", "O win at: "+ button.gridRow + ", " + button.gridColumn);
            gridLayouts[button.gridRow][button.gridColumn].setBackgroundResource(android.R.drawable.presence_invisible);
            grid[button.gridRow][button.gridColumn] = 'O';
        }
        if(gameController.checkWin(grids[button.gridRow][button.gridColumn]) == 3)
        {
            Log.d("TEST", "DRAW at: "+ button.gridRow + ", " + button.gridColumn);
            grid[button.gridRow][button.gridColumn] = 'D';
        }
    }

    public boolean checkGlobalWin(TButton button)
    {
        if(gameController.checkWin(grid) == 1)
        {
            Log.d("TEST", "X win");
            gridLayout.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
            disableAll();
            Toast.makeText(this, "X WIN", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(gameController.checkWin(grid) == 2)
        {
            Log.d("TEST", "O win");
            gridLayout.setBackgroundResource(android.R.drawable.presence_invisible);
            disableAll();
            Toast.makeText(this, "O WIN", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(gameController.checkWin(grid) == 3)
        {
            Log.d("TEST", "DRAW");
            disableAll();
            Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

}
