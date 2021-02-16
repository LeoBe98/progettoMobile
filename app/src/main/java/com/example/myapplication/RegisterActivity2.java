package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity {

    Button continue2_button;
    EditText raceNumber_fieldRegister, lovedCircuit_fieldRegister, hatedCircuit_fieldRegister, lovedCar_fieldRegister;
    String name, lastname, birthdate, fullAddress, city, postalcode, email, password, repeatPassword;
    String raceNumber, lovedCircuit, hatedCircuit, lovedCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //prendo i dati dalla activity precedente
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        birthdate = getIntent().getStringExtra("birthdate");
        fullAddress = getIntent().getStringExtra("fullAddress");
        city = getIntent().getStringExtra("city");
        postalcode = getIntent().getStringExtra("postalcode");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        repeatPassword = getIntent().getStringExtra("repeatPassword");



        raceNumber_fieldRegister = (EditText) findViewById(R.id.register_number);
        lovedCircuit_fieldRegister = (EditText) findViewById(R.id.register_lCircuit);
        hatedCircuit_fieldRegister = (EditText) findViewById(R.id.register_hCircuit);
        lovedCar_fieldRegister = (EditText) findViewById(R.id.register_lCar);

        continue2_button = (Button) findViewById(R.id.register2_continue);


        continue2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raceNumber = raceNumber_fieldRegister.getText().toString();
                lovedCircuit = lovedCircuit_fieldRegister.getText().toString();
                hatedCircuit = hatedCircuit_fieldRegister.getText().toString();
                lovedCar = lovedCar_fieldRegister.getText().toString();
                Intent intent = new Intent(RegisterActivity2.this, RegisterActivity3.class);
              //invio i dati
                if (checkData(raceNumber, lovedCircuit, hatedCircuit, lovedCar)) {
                    //invio dati all'activity seguente
                    intent.putExtra("name", name);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("birthdate", birthdate);
                    intent.putExtra("fullAddress", fullAddress);
                    intent.putExtra("city", city);
                    intent.putExtra("postalcode", postalcode);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("repeatPassword", repeatPassword);
                    intent.putExtra("raceNumber", raceNumber);
                    intent.putExtra("lovedCircuit", lovedCircuit);
                    intent.putExtra("hatedCircuit", hatedCircuit);
                    intent.putExtra("lovedCar", lovedCar);
                    startActivity(intent);
                }
            }
        });
    }

    //region Check Data
    private boolean checkData(String _raceNumber, String _lovedCircuit, String _hatedCircuit, String _lovedCar) {
        if(_raceNumber.isEmpty() || _lovedCircuit.isEmpty() || _hatedCircuit.isEmpty() || _lovedCar.isEmpty()){
            Toast.makeText(RegisterActivity2.this, "Complete all fields", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!checkRaceNumber(_raceNumber)){
            return false;
        }

        return true;
    }

    private boolean checkRaceNumber(String _raceNumber) {
        try {
            int controlNumber = Integer.parseInt(_raceNumber);
            if (controlNumber < 0) {
                Toast.makeText(RegisterActivity2.this, "No negative number", Toast.LENGTH_LONG).show();
                return false;
            }
            if (_raceNumber.length() > 2) {
                Toast.makeText(RegisterActivity2.this, "Race Number must be between 0 and 99", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException ex) {
            Toast.makeText(RegisterActivity2.this, "Race Number must be a number", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //endregion
}
