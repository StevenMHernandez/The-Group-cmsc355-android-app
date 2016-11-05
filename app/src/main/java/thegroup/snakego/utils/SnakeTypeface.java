package thegroup.snakego.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SnakeTypeface extends TextView {
    public SnakeTypeface(Context context) {
        super(context);
        init();
    }

    public SnakeTypeface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnakeTypeface(Context context, AttributeSet attrs, int defStyleAttr) {
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
