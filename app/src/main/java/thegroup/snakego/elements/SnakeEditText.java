package thegroup.snakego.elements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class SnakeEditText extends EditText {
    public SnakeEditText(Context context) {
        super(context);
        init();
    }

    public SnakeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnakeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
