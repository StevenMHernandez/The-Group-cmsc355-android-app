package thegroup.snakego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScoresActivity extends AppCompatActivity {

    TextView returnToOptionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        this.returnToOptionsText = (TextView) findViewById(R.id.return_to_options_page);
        this.returnToOptionsText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pressReturnToOptionsButton();
            }
        });
    }

    public void pressReturnToOptionsButton() {
        Intent intent = new Intent(HighScoresActivity.this, OptionsActivity.class);
        startActivity(intent);
        finish(); // clearing the back-stack
    }
}