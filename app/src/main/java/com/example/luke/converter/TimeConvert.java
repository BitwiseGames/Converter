package com.example.luke.converter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class TimeConvert extends ActionBarActivity {

    final float MILLISECONDS_PER_SECOND = 1000.0f;
    final float SECONDS_PER_SECOND = 1.0f;
    final float SECONDS_PER_MINUTE = 60.0f;
    final float SECONDS_PER_HOUR = 3600.0f;
    final float SECONDS_PER_DAY = 86400.0f;
    final float SECONDS_PER_MONTH = 2678400.0f;
    final float SECONDS_PER_YEAR = 31536000.0f;

    final Spinner spinnerLeft = (Spinner) findViewById(R.id.spinnerLeft);
    final Spinner spinnerRight = (Spinner) findViewById(R.id.spinnerRight);

    final EditText textLeft = (EditText) findViewById(R.id.editTextLeft);
    final TextView textRight = (TextView) findViewById(R.id.editTextRight);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_convert);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timeUnits, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLeft.setAdapter(adapter);
        spinnerRight.setAdapter(adapter);

        AdapterView.OnItemSelectedListener leftListener;
        leftListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textLeft.setHint( parent.getItemAtPosition(position).toString());
                changeText(textRight, spinnerLeft, spinnerRight, textLeft.getText().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        AdapterView.OnItemSelectedListener rightListener;
        rightListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textRight.setHint( parent.getItemAtPosition(position).toString());
                changeText(textRight, spinnerLeft, spinnerRight, textLeft.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinnerRight.setSelection(1);

        spinnerLeft.setOnItemSelectedListener(leftListener);
        spinnerRight.setOnItemSelectedListener(rightListener);

        final TextWatcher leftWatcher;

        leftWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeText(textRight, spinnerLeft, spinnerRight, s.toString());
            }
        };

        textLeft.addTextChangedListener(leftWatcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_convert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeText(TextView SET, Spinner spinnerLeft, Spinner spinnerRight, String text){
        if (!text.equals("")) {
            String unit1 = spinnerLeft.getSelectedItem().toString();
            Float num1 = Float.parseFloat(text);
            String unit2 = spinnerRight.getSelectedItem().toString();
            Float result = convert(unit1, num1, unit2);
            SET.setText(String.format("%.5f", result));
        }
        else {
            SET.setText("");
        }
    }

    private float convert(String unit1, float num1, String unit2){
        // convert unit1 to seconds, then convert from seconds to unit2
        // saves a lot of work
        float unit1InFeet;
        switch(unit1){
            case "Millisecond":
                unit1InFeet = (num1 / MILLISECONDS_PER_SECOND);
                break;
            case "Second":
                unit1InFeet = (num1 * SECONDS_PER_SECOND);
                break;
            case "Minute":
                unit1InFeet = (num1 / SECONDS_PER_MINUTE);
                break;
            case "Hour":
                unit1InFeet = (num1 * SECONDS_PER_HOUR);
                break;
            case "Day":
                unit1InFeet = (num1 * SECONDS_PER_DAY);
                break;
            case "Month":
                unit1InFeet = (num1 * SECONDS_PER_MONTH);
                break;
            case "Year":
                unit1InFeet = (num1 * SECONDS_PER_YEAR);
                break;
            default:
                return 0.0f;
        }

        switch(unit2){
            case "Millisecond":
                return (unit1InFeet * MILLISECONDS_PER_SECOND);
            case "Second":
                return (unit1InFeet / SECONDS_PER_SECOND);
            case "Minute":
                return (unit1InFeet * SECONDS_PER_MINUTE);
            case "Hour":
                return (unit1InFeet / SECONDS_PER_HOUR);
            case "Day":
                return (unit1InFeet / SECONDS_PER_DAY);
            case "Month":
                return (unit1InFeet / SECONDS_PER_MONTH);
            case "Year":
                return (unit1InFeet / SECONDS_PER_YEAR);
            default:
                return 0.0f;
        }
    }
}
