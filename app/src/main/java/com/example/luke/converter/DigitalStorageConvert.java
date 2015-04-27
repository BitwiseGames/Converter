package com.example.luke.converter;

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


public class DigitalStorageConvert extends ActionBarActivity {

    final float BITS_PER_BIT = 1.0f;
    final float BITS_PER_BYTE = 8.0f;
    final float BITS_PER_KILOBIT = 1000.0f;
    final float BITS_PER_KILOBYTE = 8000.f;
    final float BITS_PER_MEGABIT = 1000000.0f;
    final float BITS_PER_MEGABYTE = 8000000.0f;
    final float BITS_PER_GIGABIT = 1000000000.0f;
    final float BITS_PER_GIGABYTE = 8.0000000000f;
    final float BITS_PER_TERABIT = 1000000000000.0f;
    final float BITS_PER_TERABYTE = 8000000000000.0f;

    final Spinner spinnerLeft = (Spinner) findViewById(R.id.spinnerLeft);
    final Spinner spinnerRight = (Spinner) findViewById(R.id.spinnerRight);

    final EditText textLeft = (EditText) findViewById(R.id.editTextLeft);
    final TextView textRight = (TextView) findViewById(R.id.editTextRight);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_storage_convert);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.digitalStorageUnits, android.R.layout.simple_spinner_item);
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
        getMenuInflater().inflate(R.menu.menu_digital_storage_convert, menu);
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

        // convert unit1 to bits, then convert from bits to unit2
        // saves a lot of work
        float unit1InBits;
        switch(unit1){
            case "Bit":
                unit1InBits = (num1 / BITS_PER_BIT);
                break;
            case "Byte":
                unit1InBits = (num1 * BITS_PER_BYTE);
                break;
            case "Kilobit":
                unit1InBits = (num1 * BITS_PER_KILOBIT);
                break;
            case "Kilobyte":
                unit1InBits = (num1 * BITS_PER_KILOBYTE);
                break;
            case "Megabit":
                unit1InBits = (num1 * BITS_PER_MEGABIT);
                break;
            case "Megabyte":
                unit1InBits = (num1 * BITS_PER_MEGABYTE);
                break;
            case "Gigabit":
                unit1InBits = (num1 * BITS_PER_GIGABIT);
                break;
            case "Gigabyte":
                unit1InBits = (num1 * BITS_PER_GIGABYTE);
                break;
            case "Terabit":
                unit1InBits = (num1 * BITS_PER_TERABIT);
                break;
            case "Terabyte":
                unit1InBits = (num1 * BITS_PER_TERABYTE);
                break;
            default:
                return 0.0f;
        }

        switch(unit2){
            case "Bit":
                return (unit1InBits * BITS_PER_BIT);
            case "Byte":
                return (unit1InBits / BITS_PER_BYTE);
            case "Kilobit":
                return (unit1InBits / BITS_PER_KILOBIT);
            case "Kilobyte":
                return (unit1InBits / BITS_PER_KILOBYTE);
            case "Megabit":
                return (unit1InBits / BITS_PER_MEGABIT);
            case "Megabyte":
                return (unit1InBits / BITS_PER_MEGABYTE);
            case "Gigabit":
                return (unit1InBits / BITS_PER_GIGABIT);
            case "Gigabyte":
                return (unit1InBits / BITS_PER_GIGABYTE);
            case "Terabit":
                return (unit1InBits / BITS_PER_TERABIT);
            case "Terabyte":
                return (unit1InBits / BITS_PER_TERABYTE);
            default:
                return 0.0f;
        }
    }
}
