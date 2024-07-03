package com.example.itp4501assignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.health.connect.datatypes.Record;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.Random;

public class playGame extends AppCompatActivity{
    long startTime = 0;
    TextView tvTimer;
    TextView tvQues_num;
    TextView tvQues;
    EditText etUser_ans;
    Button btnConfirm_ans;
    TextView tvAns_result;
    TextView tvReal_ans;
    Button btnNext_ques;
    Button btnGameOver;
    ImageView gifcat;    ImageView imageView;
    MediaPlayer mediaPlayer;
    ImageView gifView;
    String [][] questions = new String[10][2];
    int ques_num = 0;
    int right_ans = 0;
    int wrong_ans = 0;
    Boolean isEnd = false;
    Button btnContinue;
    int timetext = 0;
    int Score = 0;
    Cursor cursor = null;
    Boolean HardMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_game);
        Intent intent = getIntent();
        HardMode = intent.getExtras().getBoolean("HardMode");
        Log.d("Mode",HardMode.toString());
        tvTimer = findViewById(R.id.tvTimer);
        tvQues_num = findViewById(R.id.tvQues_num);
        tvQues = findViewById(R.id.tvQues);
        etUser_ans = findViewById(R.id.etUser_ans);
        btnConfirm_ans = findViewById(R.id.btnConfirm_ans);
        tvAns_result = findViewById(R.id.tvAns_result);
        tvReal_ans = findViewById(R.id.tvReal_ans);
        btnNext_ques = findViewById(R.id.btnNext_ques);
        btnContinue = findViewById(R.id.btnContinue);
        imageView = findViewById(R.id.imageView);
        gifView = findViewById(R.id.gifCat);
        btnGameOver = findViewById(R.id.btnGameOver);



        //reset timer when create activity
        startTime = System.currentTimeMillis();

        Timer();
        randomQuestion();
        setQuestion();
    }
    //count the timer
    private void Timer() {
        startTime = System.currentTimeMillis();
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long currentTime = System.currentTimeMillis();
                                long time = currentTime - startTime;
                                int seconds = (int) (time / 1000);

                                tvTimer.setText(String.format( seconds + "sec"));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();



    }
    //start game
    public void startGame() {
        startTime = System.currentTimeMillis();

        Timer();
        randomQuestion();
        setQuestion();
        tvTimer.setVisibility(View.VISIBLE);
        tvQues_num.setVisibility(View.VISIBLE);
        tvQues.setVisibility(View.VISIBLE);
        etUser_ans.setVisibility(View.VISIBLE);
        btnConfirm_ans.setVisibility(View.VISIBLE);
        tvAns_result.setVisibility(View.INVISIBLE);
        tvReal_ans.setVisibility(View.INVISIBLE);
        btnContinue.setVisibility(View.INVISIBLE);
        btnNext_ques.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        gifcat.setVisibility(View.INVISIBLE);
    }
    //generate random question when create the activity
    private  void randomQuestion() {
        if (HardMode == false) {
            for (int i = 0; i < 10; i++) {
                Random random = new Random();
                int operand1 = random.nextInt(100) + 1;
                int operand2 = random.nextInt(100) + 1;
                String[] operators = {"+", "-", "*", "/"};
                String operator = operators[random.nextInt(operators.length)];
                Log.d("operand1", "" + operand1);
                Log.d("operand2", "" + operand2);
                Log.d("operators", "" + operators);
                String question = operand1 + operator + operand2;
                String answer = "";
                if (operator == "+") {
                    answer = String.valueOf(operand1 + operand2);

                } else if (operator == "-") {
                    if (operand1 - operand2 < 0) {
                        question = operand2 + operator + operand1;
                        answer = String.valueOf(operand2 - operand1);
                    } else {
                        answer = String.valueOf(operand1 - operand2);
                    }
                } else if (operator == "*") {
                    answer = String.valueOf(operand1 * operand2);
                } else {
                    if (operand1 - operand2 < 0) {
                        question = operand2 + operator + operand1;
                        answer = String.valueOf(operand2 / operand1);
                    } else {
                        answer = String.valueOf(operand1 / operand2);
                    }


                }
                questions[i][0] = question;
                questions[i][1] = answer;
                Log.d("question", "" + question);
                Log.d("answer", "" + answer);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                Random random = new Random();
                int operand1 = random.nextInt(100) + 1;
                int operand2 = random.nextInt(100) + 1;
                int operand3 = random.nextInt(100) + 1;
                String[] operators = {"+", "-", "*", "/"};
                String[] operators2 = {"+", "-", "*", "/"};
                String operator = operators[random.nextInt(operators.length)];
                String operator2 = operators[random.nextInt(operators.length)];
                Log.d("operand1", "" + operand1);
                Log.d("operand2", "" + operand2);
                Log.d("operand3", "" + operand2);
                Log.d("operators", "" + operators);
                Log.d("operators2", "" + operators);
                String question = operand1 + operator + operand2 + operator2 + operand3;
                String answer = "";
                //write question and answer of 3 operands and 2 operators
                if (operator == "+" && operator2 == "+") {
                    answer = String.valueOf(operand1 + operand2 + operand3);

                } else if (operator == "+" && operator2 == "-") {
                    answer = String.valueOf(operand1 + operand2 - operand3);
                } else if (operator == "+" && operator2 == "*") {
                    answer = String.valueOf(operand1 + operand2 * operand3);
                } else if (operator == "+" && operator2 == "/") {
                    answer = String.valueOf(operand1 + operand2 / operand3);
                } else if (operator == "-" && operator2 == "+") {
                    answer = String.valueOf(operand1 - operand2 + operand3);
                } else if (operator == "-" && operator2 == "-") {
                    answer = String.valueOf(operand1 - operand2 - operand3);
                } else if (operator == "-" && operator2 == "*") {
                    answer = String.valueOf(operand1 - operand2 * operand3);
                } else if (operator == "-" && operator2 == "/") {
                    answer = String.valueOf(operand1 - operand2 / operand3);
                } else if (operator == "*" && operator2 == "+") {
                    answer = String.valueOf(operand1 * operand2 + operand3);
                } else if (operator == "*" && operator2 == "-") {
                    answer = String.valueOf(operand1 * operand2 - operand3);
                } else if (operator == "*" && operator2 == "*") {
                    answer = String.valueOf(operand1 * operand2 * operand3);
                } else if (operator == "*" && operator2 == "/") {
                    answer = String.valueOf(operand1 * operand2 / operand3);
                } else if (operator == "/" && operator2 == "+") {
                    answer = String.valueOf(operand1 / operand2 + operand3);
                } else if (operator == "/" && operator2 == "-") {
                    answer = String.valueOf(operand1 / operand2 - operand3);
                } else if (operator == "/" && operator2 == "*") {
                    answer = String.valueOf(operand1 / operand2 * operand3);
                } else if (operator == "/" && operator2 == "/") {
                    answer = String.valueOf(operand1 / operand2 / operand3);
                }


                questions[i][0] = question;
                questions[i][1] = answer;
                Log.d("question", "" + question);
                Log.d("answer", "" + answer);
            }
        }
    }
    //set the question and answer to the textview

    public void setQuestion(){
        tvQues_num.setText("Question " + (ques_num+1));
        tvQues.setText(questions[ques_num][0]);
        tvReal_ans.setVisibility(View.INVISIBLE);
        tvAns_result.setText("");
        etUser_ans.setText("");

    }
    //hide hideKeyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(playGame.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void confirmAns(View view){
        hideKeyboard();
        if(etUser_ans.getText().toString().equals((questions[ques_num][1]).toString())) {

            tvAns_result.setText("Correct");
            tvAns_result.setVisibility(View.VISIBLE);
            right_ans++;

            mediaPlayer = MediaPlayer.create(this, R.raw.happycat);
            mediaPlayer.start();
            //save t
        }else{
            tvAns_result.setText("Wrong");
            tvAns_result.setVisibility(View.VISIBLE);

            tvReal_ans.setVisibility(View.VISIBLE);
            tvReal_ans.setText("The correct answer is " + questions[ques_num][1]);
            wrong_ans++;
            mediaPlayer = MediaPlayer.create(this, R.raw.crycatsound);
            mediaPlayer.start();

        }
        btnConfirm_ans.setVisibility(View.INVISIBLE);
        btnNext_ques.setVisibility(View.VISIBLE);

    }

    public void nextQuestion(View view){

        if(ques_num == 9){
            timetext = Integer.parseInt(tvTimer.getText().toString().replace("sec",""));
            Score = right_ans;
            isEnd = true;
            tvTimer.setVisibility(View.INVISIBLE);
            tvQues_num.setText("Game Over");
            tvQues.setText("Right Answer: " + right_ans + "\n" + "Wrong Answer: " + wrong_ans + "\n" + "Final time is " + timetext + "sec");
            btnConfirm_ans.setVisibility(View.INVISIBLE);
            if(right_ans >5){
                Glide.with(this)
                        .asGif()
                        .load(R.raw.happycatdance)
                        .into(imageView);

            }else{
                imageView.setImageResource(R.drawable.laughtcat);
                mediaPlayer = MediaPlayer.create(this, R.raw.catlaughtyou);
                mediaPlayer.start();

            }
            imageView.setVisibility(View.VISIBLE);
            etUser_ans.setVisibility(View.INVISIBLE);
            tvReal_ans.setVisibility(View.INVISIBLE);
            tvAns_result.setVisibility(View.INVISIBLE);

            //stop the timer
            btnContinue.setVisibility(View.VISIBLE);
            btnGameOver.setVisibility(View.VISIBLE);
            btnConfirm_ans.setVisibility(View.INVISIBLE);


        }else {
            tvAns_result.setVisibility(View.INVISIBLE);
            btnConfirm_ans.setVisibility(View.VISIBLE);

            ques_num++;
            setQuestion();
            mediaPlayer.stop();
        }
        btnNext_ques.setVisibility(View.INVISIBLE);


    }
    public void Continue(View view){
        try {


            /* make SQLite Database connection with read only */
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            int gameID = 0;
            cursor = db.rawQuery("select * from Gameslog order by gameID DESC", null);
            if (cursor.moveToFirst()) {
                gameID = cursor.getInt(cursor.getColumnIndex("gameID"));
            }


            gameID++;
            //set the platDate system date
            db.execSQL("INSERT INTO GamesLog (gameID, playDate,playTime,duration,correctCount) VALUES (" + gameID + ",date('now'),time('now')," + timetext + "," + Score + ");");



            db.close();
        } catch (Exception e) {
            Log.d("SQLiteException", e.getMessage());
        }
        mediaPlayer.stop();
        this.finish();
        playGame();

    }
    public void playGame(){
        Intent i = new Intent(this,playGame.class);
        i.putExtra("HardMode",HardMode);
        startActivity(i);
    }

    public void GameOver(View view){
        try {


            /* make SQLite Database connection with read only */
            SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501assignment/Project4501DB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            int gameID = 0;
            cursor = db.rawQuery("select * from Gameslog order by gameID DESC", null);
            if (cursor.moveToFirst()) {
                gameID = cursor.getInt(cursor.getColumnIndex("gameID"));
            }


            gameID++;
            //set the platDate system date
            db.execSQL("INSERT INTO GamesLog (gameID, playDate,playTime,duration,correctCount) VALUES (" + gameID + ",date('now'),time('now')," + timetext + "," + Score + ");");



            db.close();
        } catch (Exception e) {
            Log.d("SQLiteException", e.getMessage());
        }
        mediaPlayer.stop();
        this.finish();
    }




}