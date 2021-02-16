package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

        db = new DBHelper(this);

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

    //region checkData
    private boolean checkData(String _name, String _lastname, String _birthdate, String _fullAddress, String _city, String _postalcode, String _email, String _password, String _repeatPassword) {
      if (_name.isEmpty() || _lastname.isEmpty() || _birthdate.isEmpty() || _fullAddress.isEmpty() || _city.isEmpty() || _postalcode.isEmpty() || _email.isEmpty() || _password.isEmpty() || _repeatPassword.isEmpty()) {
            Toast.makeText(RegisterActivity1.this, "Complete all fields", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(_name.matches(".*\\d.*") || _name.length() <= 3)
        {
            Toast.makeText(RegisterActivity1.this, "Fake name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(_lastname.matches(".*\\d.*") || _lastname.length() <= 3)
        {
            Toast.makeText(RegisterActivity1.this, "Fake lastname", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!checkDate(_birthdate)){
            return false;
        }
        else if(!checkPostalCode(_postalcode)){
            return false;
        } else if(_city.matches(".*\\d.*") || _city.length() <= 1)
        {
            Toast.makeText(RegisterActivity1.this, "Inexistent City", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!checkEmail(_email)) {
            Toast.makeText(RegisterActivity1.this, "Email Wronk", Toast.LENGTH_LONG).show();
            return false;
        } else if (!disponibleEmail(_email)) {
            Toast.makeText(RegisterActivity1.this, "Email already in db", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!checkPassword(_password)){
            Toast.makeText(RegisterActivity1.this, "Password not compliant with standards", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!_password.equals(_repeatPassword)){
            Toast.makeText(RegisterActivity1.this, "Different Passwords", Toast.LENGTH_LONG).show();
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
            Toast.makeText(RegisterActivity1.this, "Error data format", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkPostalCode(String _postalcode) {
        try {
            int controlNumber = Integer.parseInt(_postalcode);
            if (_postalcode.length() != 5) {
                Toast.makeText(RegisterActivity1.this, "Wrong postcode, please enter a 5-digit number", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException ex) {
            Toast.makeText(RegisterActivity1.this, "postcode wrong", Toast.LENGTH_LONG).show();
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
    //endregion
}
