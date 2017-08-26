package pl.freki.adjustautobrightness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext());
        int brightnessAdj = preferences.getInt(BrightnessService.brightnessAdjPrefKey, 0);
        EditText brightnessAdjEditText = (EditText) findViewById(R.id.brightness_adj);

        brightnessAdjEditText.setText(String.format(Locale.US, "%d", brightnessAdj));

        this.startService(new Intent(this, ScreenOnService.class));
    }

    private void showAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void onSetBrightnessButtonClick(View view) {
        EditText brightnessAdjEditText = (EditText) findViewById(R.id.brightness_adj);
        int brightnessAdj;

        try {
            brightnessAdj = Integer.parseInt(brightnessAdjEditText.getText().toString());
        } catch (NumberFormatException e) {
            showAlert("Value can only contain integers.");

            return;
        }

        if (brightnessAdj < 0 || brightnessAdj > 255) {
            showAlert("Value must be between 0 and 255.");

            return;
        }

        BrightnessService.setAndSaveBrightnessAdjPercentage(this, brightnessAdj);
    }

}
