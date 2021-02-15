package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity1 extends AppCompatActivity {

    Button continue1_button;
    EditText name_fieldRegister, lastname_fieldRegister, birthdate_fieldRegister, fullAddress_fieldRegister, city_fieldRegister, postalcode_fieldRegister,
            email_fieldRegister, password_fieldRegister, passwordRepeat_fieldRegister;
    String name, lastname, birthdate, fullAddress, city, postalcode, email, password, repeatPassword;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        name_fieldRegister = (EditText) findViewById(R.id.register_name);
        lastname_fieldRegister = (EditText) findViewById(R.id.register_lastname);
        birthdate_fieldRegister = (EditText) findViewById(R.id.register_birthdate);
        fullAddress_fieldRegister = (EditText) findViewById(R.id.register_fulladdress);
        city_fieldRegister = (EditText) findViewById(R.id.register_city);
        postalcode_fieldRegister = (EditText) findViewById(R.id.register_postalcode);
        email_fieldRegister = (EditText) findViewById(R.id.register_email);
        password_fieldRegister = (EditText) findViewById(R.id.register_password);
        passwordRepeat_fieldRegister = (EditText) findViewById(R.id.register_repeatpassword);

        continue1_button = (Button) findViewById(R.id.register1_continue);

        db = new DBHelper(this);

        continue1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_fieldRegister.getText().toString();
                lastname = lastname_fieldRegister.getText().toString();
                birthdate = birthdate_fieldRegister.getText().toString();
                fullAddress = fullAddress_fieldRegister.getText().toString();
                city = city_fieldRegister.getText().toString();
                postalcode = postalcode_fieldRegister.getText().toString();
                email = email_fieldRegister.getText().toString();
                password = password_fieldRegister.getText().toString();
                repeatPassword = passwordRepeat_fieldRegister.getText().toString();

                if (checkData(name, lastname, birthdate, fullAddress, city, postalcode, email, password, repeatPassword)) {
                    //invio dati all'activity seguente
                    Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                    intent.putExtra("name", name);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("birthdate", birthdate);
                    intent.putExtra("fullAddress", fullAddress);
                    intent.putExtra("city", city);
                    intent.putExtra("postalcode", postalcode);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("repeatPassword", repeatPassword);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkData(String _name, String _lastname, String _birthdate, String _fullAddress, String _city, String _postalcode, String _email, String _password, String _repeatPassword) {
      if (_name.isEmpty() || _lastname.isEmpty() || _birthdate.isEmpty() || _fullAddress.isEmpty() || _city.isEmpty() || _postalcode.isEmpty() || _email.isEmpty() || _password.isEmpty() || _repeatPassword.isEmpty()) {
            Toast.makeText(RegisterActivity1.this, "Completa tutti i campi", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(_name.matches(".*\\d.*") || _name.length() <= 3)
        {
            Toast.makeText(RegisterActivity1.this, "Nome falso", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(_lastname.matches(".*\\d.*") || _lastname.length() <= 3)
        {
            Toast.makeText(RegisterActivity1.this, "Cognome falso", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!checkDate(_birthdate)){
            return false;
        }
        else if(!checkPostalCode(_postalcode)){
            return false;
        } else if(_city.matches(".*\\d.*") || _city.length() <= 1)
        {
            Toast.makeText(RegisterActivity1.this, "città inesistente", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!checkEmail(_email)) {
            Toast.makeText(RegisterActivity1.this, "Email errata", Toast.LENGTH_LONG).show();
            return false;
        } else if (!disponibleEmail(_email)) {
            Toast.makeText(RegisterActivity1.this, "Email già presente", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!checkPassword(_password)){
            Toast.makeText(RegisterActivity1.this, "Password non conforme agli standard", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!_password.equals(_repeatPassword)){
            Toast.makeText(RegisterActivity1.this, "Password differenti", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }


    private boolean checkDate(String _birthdate) {
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        dt.setLenient(false);
        try {
            dt.parse(_birthdate);
        } catch (ParseException e) {
            Toast.makeText(RegisterActivity1.this, "Errore formato data", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkPostalCode(String _postalcode) {
        try {
            int controlNumber = Integer.parseInt(_postalcode);
            if (_postalcode.length() != 5) {
                Toast.makeText(RegisterActivity1.this, "Codice postale errato, inserire un numero a 5 cifre", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException ex) {
            Toast.makeText(RegisterActivity1.this, "Codice postale errato", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean disponibleEmail(String _email) {

        Cursor getEmail = db.getEmail();
        for (getEmail.moveToFirst(); !getEmail.isAfterLast(); getEmail.moveToNext()) {
            String s = getEmail.getString(getEmail.getColumnIndex("email"));
            if (_email.equals(s))
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

}
