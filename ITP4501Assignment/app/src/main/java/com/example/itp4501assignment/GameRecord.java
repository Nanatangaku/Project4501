package com.example.itp4501assignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameRecord extends AppCompatActivity {
    Cursor cursor = null;
    TextView tvRecord;
    Button btngoToHome;
    SQLiteDatabase db;
    RadioButton rbplayTime, rbScore, rbAsc, rbDesc;
    Button btnShow;
    String[] columns= {"gameID", "playDate", "playTime", "duration", "correctCount"};
    String sql = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_record);
        tvRecord = findViewById(R.id.tvRecord);
        rbplayTime=findViewById(R.id.rb_Duration);
        rbScore=findViewById(R.id.rb_Score);
        rbAsc=findViewById(R.id.rb_asc);
        rbDesc=findViewById(R.id.rb_desc);
        btnShow=findViewById(R.id.btnShow);

        try {
            /* make SQLite Database connection with read only */
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            cursor = db.rawQuery("select * from Gameslog order by correctCount DESC", null);
            String dataStr = "";
            int rank =1;
            while (cursor.moveToNext()) {
                String gameID = cursor.getString(cursor.getColumnIndex("gameID"));
                String playDate = cursor.getString(cursor.getColumnIndex("playDate"));

                String playTime = cursor.getString(cursor.getColumnIndex("playTime"));

                String duration = cursor.getString(cursor.getColumnIndex("duration"));

                String correctCount = cursor.getString(cursor.getColumnIndex("correctCount"));
                dataStr += ("Rank" + rank + ":" + "ID:" + gameID  + "playDate: " + playDate + " Time: " + playTime + " Duration: " + duration + " CorrectCount: " + correctCount + "\n");
                rank++;
            }
            tvRecord.setText(dataStr);

            db.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void goToHome(View view){
        finish();
    }

    public void Show(View view){
        /* check the value of radio buttons */
        String sortBy = (rbplayTime.isChecked()) ? "Duration " : "correctCount";
        String order = (rbAsc.isChecked()) ? " ASC " : " DESC ";


        try {
            /* make SQLite Database connection with read only */
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.OPEN_READONLY);
            cursor = db.query("GamesLog", columns, null, null, null, null, sortBy + order);
            String datastr2 = "";;
            int rank =1;

            /* set cursor to query the information */
            while (cursor.moveToNext()) {
                String gameID = cursor.getString(cursor.getColumnIndex("gameID"));
                String playDate = cursor.getString(cursor.getColumnIndex("playDate"));

                String playTime = cursor.getString(cursor.getColumnIndex("playTime"));

                String duration = cursor.getString(cursor.getColumnIndex("duration"));

                String correctCount = cursor.getString(cursor.getColumnIndex("correctCount"));
                datastr2 += ("Rank" + rank + ":" + "ID:" + gameID  + "playDate: " + playDate + " Time: " + playTime + " Duration: " + duration + " CorrectCount: " + correctCount + "\n");

            }
            tvRecord.setText(datastr2);

            db.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void ResetDB(View view){
        try{
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.OPEN_READWRITE);

            sql = "DROP TABLE IF EXISTS GamesLog;";
            db.execSQL(sql);

            sql = "CREATE TABLE GamesLog ( gameID int , playDate date, playTime time, duration int , correctCount int );";
            db.execSQL(sql);
            db.close();
            goToRecord();
        }catch(Exception e){
            Log.d("resetdb",e.getMessage());
        }

    }
    public void goToRecord(){
        Intent i = new Intent(this,GameRecord.class);
        startActivity(i);
    }
}