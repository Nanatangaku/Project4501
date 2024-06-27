package com.example.itp4501assignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class GameRecord extends AppCompatActivity {
    Cursor cursor = null;
    TextView tvRecord;
    Button btngoToHome;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_record);
        tvRecord = findViewById(R.id.tvRecord);

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
}