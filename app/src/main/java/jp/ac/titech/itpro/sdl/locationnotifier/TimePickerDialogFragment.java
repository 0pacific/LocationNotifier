package jp.ac.titech.itpro.sdl.locationnotifier;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Button;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Button b;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);

        return timePickerDialog;
    }

    public TimePickerDialogFragment setButton (Button b) {
        this.b = b;
        return this;
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(b == null)
            return;

        b.setText(hourOfDay + " : " + minute);
    }

}