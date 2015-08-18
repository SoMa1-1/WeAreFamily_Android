package com.waf.soma.wearefamily;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TitleActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                SharedPreferences setting;
                setting = getSharedPreferences("setting", 0);

                Log.i("tokentest", setting.getString("token","tokenGetFailed"));

                //isLogined의 값에 따라 버튼을 눌렀을때 이동할 액티비티 분기
                if (setting.getBoolean("isLogined", false)) {
                    Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(TitleActivity.this, SignupActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title, menu);
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
}
