package com.example.itp4501assignment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    Button btnRanking;
    Button btnRecord;
    Button btnClose;
    SQLiteDatabase db;
    TextView tvTitle;
    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //create a array holding the color
        float [] positionArray = new float[]{0f,0.3f,0.6f,0.9f};
        btnPlay = findViewById(R.id.btnPlay);
        btnRanking = findViewById(R.id.btnRanking);
        btnRecord = findViewById(R.id.btnRecord);
        btnClose = findViewById(R.id.btnClose);
        tvTitle = findViewById(R.id.tvTitle);

        int[] rainbowColors = {0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFF8B00FF};
        float[] positions = {0f, 0.05f, 0.1f, 0.15f, 0.2f, 0.25f, 0.3f};
        Shader shader = new LinearGradient(0, 0, 0, tvTitle.getTextSize() * rainbowColors.length,
                rainbowColors, positions, Shader.TileMode.MIRROR);
        tvTitle.getPaint().setShader(shader);

        initial_database();
    }

    public void playGame(View view){
        Intent i = new Intent(this,playGame.class);
        startActivity(i);
    }
    public void goToRecord(View view){
        Intent i = new Intent(this,GameRecord.class);
        startActivity(i);
    }
    public void goToGameRanking(View view){
        Intent i = new Intent(this,GameRanking.class);
        startActivity(i);
    }

    //initial the database
    public void initial_database(){
        try{
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            sql = "DROP TABLE IF EXISTS GamesLog;";
            db.execSQL(sql);

            sql = "CREATE TABLE GamesLog ( gameID int , playDate date, playTime time, duration int , correctCount int );";
            db.execSQL(sql);


            db.execSQL("INSERT INTO GamesLog (gameID, playDate,playTime,duration,correctCount) VALUES ( 1,date('now'),time('now')," + 15 + "," + 9 + ");");
            db.execSQL("INSERT INTO GamesLog (gameID, playDate,playTime,duration,correctCount) VALUES ( 2,date('now'),time('now')," + 25 + "," + 8 + ");");


            db.close();



        }catch (SQLiteException e) {
            Log.d("SQLiteException", e.getMessage());
        }
    }

    public void closeGame(View view){
        finish();
    }
}