package com.example.game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LastActivity extends AppCompatActivity {

    private Button playAgain;
    private TextView textScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        playAgain=findViewById(R.id.playAgain);
        textScore=findViewById(R.id.textView2);

        Intent intent = getIntent();

        String score = intent.getStringExtra("points_i_need");

        textScore.setText("Your score is:"+score);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(LastActivity.this,secondScreen.class);

                startActivity(newIntent);

            }
        });





    }
}
