package com.example.luke.converter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class DistanceConvert extends ActionBarActivity {

    final float CENTIMETER_PER_FEET = 30.48f;
    final float FEET_PER_METER = 3.28f;
    final float FEET_PER_KILOMETER = 3280.84f;
    final float INCHES_PER_FEET = 12.0f;
    final float FEET_PER_YARD = 3.0f;
    final float FEET_PER_FEET = 1.0f;
    final float FEET_PER_MILE = 5280.0f;

    final Spinner spinnerLeft = (Spinner) findViewById(R.id.spinnerLeft);
    final Spinner spinnerRight = (Spinner) findViewById(R.id.spinnerRight);

    final TextView textLeft = (TextView) findViewById(R.id.editTextLeft);
    final TextView textRight = (TextView) findViewById(R.id.editTextRight);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_convert);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distanceUnits, android.R.layout.simple_spinner_item);
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
                textRight.setHint(parent.getItemAtPosition(position).toString());
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
        getMenuInflater().inflate(R.menu.menu_distance_convert, menu);
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
        else if (id == R.id.action_clear){
            EditText textLeft = (EditText) findViewById(R.id.editTextLeft);
            TextView textRight = (TextView) findViewById(R.id.editTextRight);
            textLeft.setText("");
            textRight.setText("");
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeText(TextView SET, Spinner spinnerLeft, Spinner spinnerRight, String text){
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

    public float convert(String unit1, float num1, String unit2){

        // convert unit1 to feet, then convert from feet to unit2
        // saves a lot of work
        float unit1InFeet;
        switch(unit1){
            case "Centimeter":
                unit1InFeet = (num1 / CENTIMETER_PER_FEET);
                break;
            case "Meter":
                unit1InFeet = (num1 * FEET_PER_METER);
                break;
            case "Inch":
                unit1InFeet = (num1 / INCHES_PER_FEET);
                break;
            case "Kilometer":
                unit1InFeet = (num1 * FEET_PER_KILOMETER);
                break;
            case "Yard":
                unit1InFeet = (num1 * FEET_PER_YARD);
                break;
            case "Foot":
                unit1InFeet = (num1 * FEET_PER_FEET);
                break;
            case "Mile":
                unit1InFeet = (num1 * FEET_PER_MILE);
                break;
            default:
                return 0.0f;
        }

        switch(unit2){
            case "Centimeter":
                return (unit1InFeet * CENTIMETER_PER_FEET);
            case "Meter":
                return (unit1InFeet / FEET_PER_METER);
            case "Inch":
                return (unit1InFeet * INCHES_PER_FEET);
            case "Kilometer":
                return (unit1InFeet / FEET_PER_KILOMETER);
            case "Yard":
                return (unit1InFeet / FEET_PER_YARD);
            case "Foot":
                return (unit1InFeet / FEET_PER_FEET);
            case "Mile":
                return (unit1InFeet / FEET_PER_MILE);
            default:
                return 0.0f;
        }

    }

}