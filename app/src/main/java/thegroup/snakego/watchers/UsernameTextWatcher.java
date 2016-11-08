package thegroup.snakego.watchers;

import android.text.Editable;
import android.text.TextWatcher;

import thegroup.snakego.models.User;

public class UsernameTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence string, int start, int count, int after) {
        //
    }

    @Override
    public void onTextChanged(CharSequence string, int start, int before, int count) {
        User.get().setName(string.toString());
    }

    @Override
    public void afterTextChanged(Editable string) {
        //
    }
}
