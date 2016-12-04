package thegroup.snakego;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import thegroup.snakego.models.User;


public class LoginDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View prompt = li.inflate(R.layout.dialog_login, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(prompt);
        final EditText user = (EditText) prompt.findViewById(R.id.login_name);
        alertDialogBuilder.setTitle("Snake GO Login:");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String username = user.getText().toString();
                        User.get().setName(username); // no error checking
                    }
                });
        return alertDialogBuilder.show();
    }
}
