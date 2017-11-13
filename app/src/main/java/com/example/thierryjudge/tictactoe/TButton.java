package com.example.thierryjudge.tictactoe;

import android.content.Context;
import android.widget.Button;

public class TButton extends Button
{
    public int gridRow, gridColumn;
    public int row, column;

    public boolean played = false;

    public TButton(Context context, int gridRow, int gridColumn, int row, int column)
    {
        super(context);
        this.gridRow = gridRow;
        this.gridColumn = gridColumn;
        this.row = row;
        this.column = column;
    }

    public TButton(Context context, int row, int column)
    {
        super(context);
        this.row = row;
        this.column = column;
    }
}
