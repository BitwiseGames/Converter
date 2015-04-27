package com.example.luke.converter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class TemperatureConvert extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_convert);

        final EditText celsius = (EditText) findViewById(R.id.celsius); // have to cast it as an editText even though it is an editText?
        final EditText fahrenheit = (EditText) findViewById(R.id.fahrenheit);

        final TextWatcher celsiusWatcher = new TextWatcher(){
            public void afterTextChanged(Editable s){} // not gonna use this or the one below but they're required
            public void beforeTextChanged(CharSequence s, int start, int before, int count){}
            public void onTextChanged(CharSequence s, int start, int before, int count){ // change the fahrenheit text now that the user has entered something
                Double result;
                if (!s.toString().equals("")){ // if the user backspaced and the text box is empty
                    result = Double.parseDouble(s.toString()) * 1.8 + 32;
                    fahrenheit.setText(String.format("%.2f", result )); //round to 2 decimal places
                }
                else {
                    fahrenheit.setText("");
                }
            }
        };
        final TextWatcher fahrenheitWatcher = new TextWatcher(){
            public void afterTextChanged(Editable s){}
            public void beforeTextChanged(CharSequence s, int start, int before, int count){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                celsius.removeTextChangedListener(celsiusWatcher); // remove it so we don't get caught in an infinite loop
                Double result;
                if (!s.toString().equals("")){ // s is the text the user entered
                    result = (Double.parseDouble(s.toString()) - 32) / 1.8;
                    celsius.setText(String.format("%.2f", result ));
                }
                else {
                    celsius.setText("");
                }
                celsius.addTextChangedListener(celsiusWatcher);// add it back again now that we're done
            }
        };

        celsius.addTextChangedListener(celsiusWatcher);
        fahrenheit.addTextChangedListener(fahrenheitWatcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_temperature_convert, menu);
        return super.onCreateOptionsMenu(menu);
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
            EditText celsius = (EditText) findViewById(R.id.celsius);
            EditText fahrenheit = (EditText) findViewById(R.id.fahrenheit);
            celsius.setText("");
            fahrenheit.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}