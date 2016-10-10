package thegroup.snakego;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import thegroup.snakego.Models.User;

public class OptionsDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        LayoutInflater inflate = getActivity().getLayoutInflater();
//
//        builder.setView(inflate.inflate(R.layout.dialog_options, null))
        builder.setTitle("High Score: " + User.get().getHighScore())
                .setMessage("Score : " + User.get().getScore())
                .setPositiveButton(R.string.dia_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
//                .setNegativeButton(R.string.dia_cncl, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                        dismiss();
//                    }
//                });
        // Create the AlertDialog object and return it
        return builder.show();
    }
}
