package com.waf.soma.wearefamily;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.Vector;

public class SignupActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Spinner posSpinner = (Spinner) findViewById(R.id.posSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,R.array.position, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        posSpinner.setAdapter(adapter);


        Button btnSignup = (Button) findViewById(R.id.btnSignup);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editPIN = (EditText)findViewById(R.id.editPIN);
                EditText editName = (EditText)findViewById(R.id.editName);
                Spinner posSpinner = (Spinner) findViewById(R.id.posSpinner);

                if(editName.getText().toString().equals("")) {
                    //이름이 비어있음
                    Toast.makeText(getApplicationContext(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("url", "http://192.168.219.109/phpTest.php"));
                nameValue.add(new BasicNameValuePair("pin", editPIN.getText().toString()));
                nameValue.add(new BasicNameValuePair("name", editName.getText().toString()));
                nameValue.add(new BasicNameValuePair("position", posSpinner.getSelectedItem().toString()));

                new HttpTask().execute(nameValue);

                if(false /*TODO 핀번호 잘못되는 조건 입력*/) {
                    //PIN번호가 잘못됨
                    Toast.makeText(getApplicationContext(),"PIN번호가 유효하지 않습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    DialogFragment dialog = new PolicyDialog();
                    dialog.show(getSupportFragmentManager(), "PolicyDialog");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
