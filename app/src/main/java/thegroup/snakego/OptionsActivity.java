package thegroup.snakego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import thegroup.snakego.elements.SnakeTextView;
import thegroup.snakego.models.User;

public class OptionsActivity extends AppCompatActivity {

    SnakeTextView userName;
    SnakeTextView resumeGameText;
    SnakeTextView highScoreText;
    SnakeTextView quitText;
    SnakeTextView score;
    SnakeTextView snakeGoPoetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.options_layout);

        int scores = User.get().getScore();

        score = (SnakeTextView) findViewById(R.id.your_score_text);
        score.setText("Score: " + scores);


        this.userName = (SnakeTextView) findViewById(R.id.user_name);
        this.userName.append(User.get().getName());
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickUserStatsText();
            }
        });

        resumeGameText = (SnakeTextView) findViewById(R.id.resume_game_text);
        this.resumeGameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResumeGameButton();
            }
        });

        highScoreText = (SnakeTextView) findViewById(R.id.high_scores_text);
        highScoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHighScoresText();
            }
        });

        snakeGoPoetry = (SnakeTextView) findViewById(R.id.snakego_poetry_text);
        snakeGoPoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSnakeGoPoetryText();
            }
        });

        quitText = (SnakeTextView) findViewById(R.id.quit_game_text);
        quitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQuit();
            }
        });
    }

    public void clickUserStatsText() {
        Intent intent = new Intent(this, UserStatsActivity.class);
        startActivity(intent);
    }

    public void clickResumeGameButton() {
        Intent intent = new Intent(this, MapsActivity.class);
        //startActivity(intent);
        finish();
    }

    public void clickHighScoresText() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
        //finish();
    }

    public void clickSnakeGoPoetryText() {
        Intent intent = new Intent(this, SnakeGoPoetry.class);
        startActivity(intent);
        //finish();
    }

    public boolean clickQuit() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quitclick", true);
        startActivity(intent);
        finish();
        return true;
    }
}
