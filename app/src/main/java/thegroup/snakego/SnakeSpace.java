package thegroup.snakego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SnakeSpace extends AppCompatActivity {

    private ImageView resumeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_space);

        resumeGame = (ImageView) findViewById(R.id.gandalf);
        this.resumeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResumeGameGandalfImage();
            }
        });
    }


    public void clickResumeGameGandalfImage() {
        finish();
    }

}
