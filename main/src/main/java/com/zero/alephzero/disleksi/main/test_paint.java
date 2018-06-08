package com.zero.alephzero.disleksi.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class test_paint extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_paintt);

        paintView = (PaintView) findViewById(R.id.PaintView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        paintView.init(displayMetrics);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.paint_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int i = item.getItemId();
        if (i == R.id.action_normal) {
            paintView.normal();
            return true;
        } else if (i == R.id.action_blur) {
            paintView.blur();
            return true;
        } else if (i == R.id.action_emboss) {
            paintView.emboss();
            return true;
        } else if (i == R.id.action_clear) {
            paintView.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
