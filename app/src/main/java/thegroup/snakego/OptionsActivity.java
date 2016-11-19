package thegroup.snakego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import thegroup.snakego.elements.SnakeEditText;
import thegroup.snakego.elements.SnakeTextView;
import thegroup.snakego.models.User;
import thegroup.snakego.watchers.UsernameTextWatcher;

public class OptionsActivity extends AppCompatActivity {

    SnakeEditText usernameInput;
    SnakeTextView resumeGameText;
    SnakeTextView highScoreText;
    SnakeTextView quitText;
    SnakeTextView score;
    SnakeTextView snakeGoPoetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        int scores = User.get().getScore();

        score = (SnakeTextView) findViewById(R.id.your_score_text);
        score.setText("Score: " + scores);

        this.usernameInput = (SnakeEditText) findViewById(R.id.user_name);
        this.usernameInput.setText(User.get().getName());
        this.usernameInput.addTextChangedListener(new UsernameTextWatcher());

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
