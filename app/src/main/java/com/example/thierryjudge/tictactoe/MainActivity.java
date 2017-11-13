package com.example.thierryjudge.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {


    TextView titleText;
    Button bigGameButton, smallGameButton;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigGameButton = (Button) findViewById(R.id.bigGameButton);
        smallGameButton = (Button) findViewById(R.id.smallGameButton);


    }

    public void goToSmallGame(View view)
    {
        Intent intent = new Intent(this, SmallGame.class);
        startActivity(intent);
    }

    public void goToBigGame(View view)
    {
        Intent intent = new Intent(this, BigGame.class);
        startActivity(intent);
    }
}


