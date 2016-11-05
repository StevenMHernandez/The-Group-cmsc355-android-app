package thegroup.snakego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import thegroup.snakego.models.User;
import thegroup.snakego.utils.SnakeTypeface;

public class OptionsActivity extends AppCompatActivity {

    SnakeTypeface resumeGameText, hsText, quitText, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        int scores = User.get().getScore();

        score = (SnakeTypeface) findViewById(R.id.your_score_text);
        score.setText("Score: " + scores);

        resumeGameText = (SnakeTypeface) findViewById(R.id.resume_game_text);
        this.resumeGameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResumeGameButton();
            }
        });

        hsText = (SnakeTypeface) findViewById(R.id.high_scores_text);

        hsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHighScoresText();
            }
        });

        quitText = (SnakeTypeface) findViewById(R.id.quit_game_text);
        quitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQuit();
            }
        });
    }

    public void clickResumeGameButton() {
        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);
        finish();
    }

    public void clickHighScoresText() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
//        finish();
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
