package com.zero.alephzero.disleksi.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class test_scroll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);

        String text = read("test.txt");
        TextView textView = findViewById(R.id.TextView);
        textView.setText(text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


    }
    private String read(String filename){
        String text = "";

        try {
            InputStream in = getAssets().open(filename);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            text = new String (buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

}
