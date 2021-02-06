package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.User;
import com.example.myapplication.tools.Utils;


public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView registerNow;
    EditText et_email, et_password;
    DBHelper db;
    public String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
      /*  if (Utils.STATUS == 0) {
            Utils.USERLIST = new Users();
            //aggiungo utente standard
            User user = new User();
            Utils.USERLIST.addUser(user);
            Utils.STATUS = 1;
            Log.e("user", user.getEMAIL());
            Log.e("user", Utils.USERLIST.getUser("email@email.it").getEMAIL());
        }*/


        loginButton = (Button) findViewById(R.id.login_button);
        registerNow = (TextView) findViewById(R.id.registerNow_text);
        et_email = (EditText) findViewById(R.id.login_email);
        et_password = (EditText) findViewById(R.id.login_password);


        Cursor statusCursor = db.getStatus();
        int status = statusCursor.getInt(statusCursor.getColumnIndex("status"));
        int userId = statusCursor.getInt(statusCursor.getColumnIndex("userId"));

        if (status == Utils.STATUS_LOGGED) {
            Cursor userCursor = db.getUserFromId(userId);
            getUserData(userCursor);
            Toast.makeText(LoginActivity.this, "Ben tornato", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                if (check(email, password)) {
                    Log.e("debug", "check enter");
                    Cursor cursor = db.getUser(email);
                    getUserData(cursor);

                    if (!email.equals(Utils.USER.getEMAIL()) || !password.equals(Utils.USER.getPASSWORD())) {
                        //Toast.makeText(LoginActivity.this, "ciaoStronzi "+Utils.USER.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, "Email o password errati", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login succesfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity1.class);
                startActivity(intent);

            }
        });
    }

    private boolean check(String _email, String _password) {
        if (_email.isEmpty() || _password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "campo vuoto", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validatePassword(_password)) {
            Toast.makeText(LoginActivity.this, "password errata", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateEmail(_email)) {
            Toast.makeText(LoginActivity.this, "email errata", Toast.LENGTH_LONG).show();
            return false;
        } else return true;

    }

    private boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    public void getUserData(Cursor cursor ){
        if (cursor.moveToFirst()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            String lastname = cursor.getString(2);
            String birthdate = cursor.getString(3);
            String fullAddress = cursor.getString(4);
            String city = cursor.getString(5);
            String postalcode = cursor.getString(6);
            String email = cursor.getString(7);
            String password = cursor.getString(8);
            String raceNumber = cursor.getString(9);
            String lovedCircuit = cursor.getString(10);
            String hatedCircuit = cursor.getString(11);
            String lovedCar = cursor.getString(12);
            String imageString = cursor.getString(13);
            Log.e("debug", email);
            Log.e("debug", "enter cursor");

            //SETTO I DATI IN LOCALE PRESI DAL DB
            Utils.USER = new User(id, name, lastname, birthdate, fullAddress, city, postalcode, email, password, raceNumber, lovedCircuit, hatedCircuit, lovedCar, imageString);
            db.updateStatus(Utils.STATUS_LOGGED, id);

        }
    }
}


