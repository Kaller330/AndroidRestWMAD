package Operations;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.h024470h.wmadassignment.R;

public class CustomAlert {

    public CustomAlert(String title, String message, Context c, String positiveBtnCaption, String negativeBtnCaption, final CustomAlertInterface target ){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.PositiveMethod(dialog, id);
            }
            }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    target.NegativeMethod(dialog, id);
                }
            });
        AlertDialog CustomAlert = builder.create();
        CustomAlert.setCancelable(false);
        CustomAlert.show();


    }
    public CustomAlert(String title, String message, Context c, String positiveBtnCaption, final CustomAlertInterface target ){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.PositiveMethod(dialog, id);
            }
        });
        AlertDialog CustomAlert = builder.create();
        CustomAlert.setCancelable(false);
        CustomAlert.show();


    }
}
