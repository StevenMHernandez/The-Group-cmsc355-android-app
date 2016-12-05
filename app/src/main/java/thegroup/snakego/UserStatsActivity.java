package thegroup.snakego;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import thegroup.snakego.elements.SnakeTextView;
import thegroup.snakego.models.User;

public class UserStatsActivity extends AppCompatActivity {

    SnakeTextView userTitle;
    SnakeTextView redCount;
    SnakeTextView greenCount;
    SnakeTextView timeUp;
    SnakeTextView returnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_stats);

        userTitle = (SnakeTextView) findViewById(R.id.user_stats_title);
        userTitle.setText(User.get().getName()+"'s Stats!");

        redCount = (SnakeTextView) findViewById(R.id.red_apple_stat);
        redCount.setText("Reds:" + User.get().getRedAppleCount());

        greenCount = (SnakeTextView) findViewById(R.id.green_apple_stat);
        greenCount.setText("Greens:" + User.get().getGreenAppleCount());

        timeUp = (SnakeTextView) findViewById(R.id.time_up);
        timeUp.setText("Time up:" + User.get().getTimeUp() + " sec");

        returnStats = (SnakeTextView) findViewById(R.id.return_from_stats);
        returnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
