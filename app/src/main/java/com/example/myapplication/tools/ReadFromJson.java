package com.example.myapplication.tools;
import android.app.Activity;
import android.util.Log;
import com.example.myapplication.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class ReadFromJson {
    //leggo i campionati dal json e li ritorno in una stringa
    public static String getCampionati(Activity activity) {
        String res = "";
        InputStream is = activity.getResources().openRawResource(R.raw.campionati);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Json", "error " +e);
            return "error";
        }

        res = writer.toString();
        return res;
    }
    //leggo le classifiche dal json e li ritorno in una stringa
    public static String getClassifiche(Activity activity) {
        String res = "";
        InputStream is = activity.getResources().openRawResource(R.raw.classifiche);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Json", "error " +e);
            return "error";
        }

        res = writer.toString();
        return res;
    }
}
