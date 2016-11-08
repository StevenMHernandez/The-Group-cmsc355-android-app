package thegroup.snakego.elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SnakeTextView extends TextView {
    public SnakeTextView(Context context) {
        super(context);
        init();
    }

    public SnakeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnakeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface snake = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/snake_font.ttf");
            setTypeface(snake);
        }
    }
}
