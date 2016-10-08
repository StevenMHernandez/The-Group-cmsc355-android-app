package thegroup.snakego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    TextView resumeGameText;
    TextView highScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        this.resumeGameText = (TextView) findViewById(R.id.resume_game_text);
        this.resumeGameText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pressResumeGameButton();
            }
        });
        this.highScoreText = (TextView) findViewById(R.id.high_scores_text);
        this.highScoreText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                clickHighScoresText();
            }
        });
    }

    public void pressResumeGameButton() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void clickHighScoresText() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }

}
