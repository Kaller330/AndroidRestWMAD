package Operations;

import android.content.DialogInterface;
public interface CustomAlertInterface {

    public abstract void PositiveMethod(DialogInterface dialog, int id);
    public abstract void NegativeMethod(DialogInterface dialog, int id);
}
