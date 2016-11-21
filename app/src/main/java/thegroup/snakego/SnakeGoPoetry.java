package thegroup.snakego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

import thegroup.snakego.elements.SnakeTextView;

public class SnakeGoPoetry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_go_poetry);
        SnakeTextView snakeGoPoetry = (SnakeTextView) findViewById(R.id.snakego_poetry_text3);
        snakeGoPoetry.setMovementMethod(new ScrollingMovementMethod());
    }
}
