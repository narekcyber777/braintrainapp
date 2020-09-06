package com.example.braintrainerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static int actualAnswer = -1;
    final String CORRECT_ANSWER = "Correct :)";
    final String WRONG_ANSWER = "Wrong :)";
    final String GAME_OVER = "Its Done!";
    private MediaPlayer mediaPlayer;
    private GridLayout gridLayout;
    private TextView secondsText, actionText, countText, resultText;
    private Button startGame, playAgain;
    private ArrayList<View> views;
    private boolean isGameActive = false;
    private CountDownTimer startGameTime;

    public void startGame(View view) {

        click();
        if (startGame.isShown()) {
            startGame.setVisibility(View.GONE);

            for (View view1 : views) {
                view1.setVisibility(View.VISIBLE);

            }
        }
        startAgame();


    }

    public void chooseAnswer(View view) {
        click();

        if (isGameActive) {
            Button numericAnswer = (Button) view;
            doActionTotal(Integer.parseInt(numericAnswer.getText().toString()) == actualAnswer);

            mainLogic();
        }


    }

    public void restartGame(View view) {
        click();
        startAgame();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.gridLayout);
        startGame = findViewById(R.id.start);
        playAgain = findViewById(R.id.play_again);
        resultText = findViewById(R.id.result);
        countText = findViewById(R.id.game_count);
        actionText = findViewById(R.id.action);
        secondsText = findViewById(R.id.seconds);

        views = new ArrayList<>(Arrays.asList(gridLayout, resultText, countText, actionText, secondsText));


    }

    public void click() {
        MediaPlayer.create(MainActivity.this, R.raw.click).start();
    }

    private void mainLogic() {
        int num1 = (int) (Math.random() * 100);

        int num2 = (int) (Math.random() * 100);
        int answer = num1 + num2;
        actualAnswer = answer;

        actionText.setText(num1 + " + " + num2);


        int[] array = {-1, -1, -1, -1};
        array[(int) (Math.random() * 3)] = answer;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                int y = ((int) (Math.random() * 4));
                int randomNum = numericAction(y, array[i], answer);
                if (!isDuplicated(array, randomNum)) {
                    array[i] = randomNum;
                } else {
                    int count = 0;//how much he checks
                    while (isDuplicated(array, randomNum)) {
                        if (count > 5) {
                            randomNum = randomNum + 1;
                        } else {
                            System.out.println("felix");
                            randomNum = numericAction(y, array[i], answer);
                        }
                        count++;
                    }


                    array[i] = randomNum;
                }


            }
        }
        mixNumbers(array);

    }

    private boolean isDuplicated(int[] array, int randomNum) {
        for (int i = 0; i < array.length; i++) {
            if (randomNum == array[i]) return true;

        }
        return false;
    }

    private void mixNumbers(int[] array) {
        // retrieve all numbers boxes
        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            Button button = (Button) gridLayout.getChildAt(i);
            button.setText(String.valueOf(array[i]));

        }

    }

    private int numericAction(int num, int arrayValue, int answer) {
        int randomNum = (int) (Math.random() * 100) + 1;

        switch (num) {
            case 0:
                return randomNum;

            case 1:
                return answer + (int) (int) (Math.random() * 10) + 1;

            case 2:
                return answer + (int) (int) (Math.random() * 2) + 1;


            case 3:
                int mathRandom = 0;
                mathRandom = (int) (Math.random() * 15);

                if (answer - mathRandom <= 0) {
                    while (answer - mathRandom <= 0) {


                        mathRandom = (int) (Math.random() * 15);
                    }
                }
                return answer - mathRandom;

            case 4:
                int mathRandom2 = 0;
                mathRandom2 = (int) (Math.random() * 30);

                if (answer - mathRandom2 <= 0) {
                    while (answer - mathRandom2 <= 0) {
                        mathRandom2 = (int) (Math.random() * 30);
                    }
                }
                return answer - mathRandom2;


        }
        return arrayValue;
    }

    private void doActionTotal(boolean isTrue) {
        String takeAwayfromResults = countText.getText().toString();
        int indexOfMyValue = takeAwayfromResults.indexOf("/");
        try {
            int num1 = Integer.parseInt(takeAwayfromResults.substring(0, indexOfMyValue).trim());


            int num2 = Integer.parseInt(takeAwayfromResults.substring(indexOfMyValue + 1).trim());
            if (isTrue) {
                num1++;
                resultText.setText(CORRECT_ANSWER);
            } else {
                resultText.setText(WRONG_ANSWER);
            }

            num2++;


            countText.setText(stringNumeric(num1, num2));

        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Smth get Wrong", Toast.LENGTH_LONG).show();
        }
    }


    private String stringNumeric(long milliseconds) {
        return (int) (milliseconds / 1000) + "s";
    }

    private String stringNumeric(int trueAnswer, int totalAnsered) {
        return trueAnswer + "/" + totalAnsered;
    }


    private void startAgame() {
        resultText.setText("");
        playAgain.setVisibility(View.GONE);
        isGameActive = true;
        countText.setText("0/0");
        startGameTime = new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                secondsText.setText(stringNumeric(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                isGameActive = false;
                resultText.setText(GAME_OVER);
                playAgain.setVisibility(View.VISIBLE);


            }
        }.start();

        mainLogic();


    }


}
