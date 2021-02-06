package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity1 extends AppCompatActivity {

    Button continue1_button;
    EditText name_fieldRegister, lastname_fieldRegister, birthdate_fieldRegister, fullAddress_fieldRegister, city_fieldRegister, postalcode_fieldRegister,
    email_fieldRegister, password_fieldRegister, passwordRepeat_fieldRegister;
    String name, lastname, birthdate, fullAddress, city, postalcode, email, password, repeatPassword;
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

                //invio dati all'activity seguente
                Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                intent.putExtra("name", name );
                intent.putExtra("lastname", lastname );
                intent.putExtra("birthdate", birthdate );
                intent.putExtra("fullAddress", fullAddress );
                intent.putExtra("city", city );
                intent.putExtra("postalcode", postalcode );
                intent.putExtra("email", email );
                intent.putExtra("password", password );
                intent.putExtra("repeatPassword", repeatPassword );
                startActivity(intent);
            }
        });
    }
}
