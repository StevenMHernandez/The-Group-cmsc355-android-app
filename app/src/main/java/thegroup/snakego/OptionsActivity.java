package thegroup.snakego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    TextView resumeGameText;
    TextView highScoreText;
    TextView quitGameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        this.resumeGameText = (TextView) findViewById(R.id.resume_game_text);
        this.resumeGameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickResumeGameButton();
            }
        });
        this.highScoreText = (TextView) findViewById(R.id.high_scores_text);
        this.highScoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHighScoresText();
            }
        });

        this.quitGameText = (TextView) findViewById(R.id.quit_game_text);
        this.quitGameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickQuit();
            }
        });

    }

    public void clickResumeGameButton() {
        finish(); // if we pass intents around our map gets lost

    }

    public void clickHighScoresText() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
        finish();
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
