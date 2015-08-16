package com.waf.soma.wearefamily;

/**
 * Created by kimseungchul on 15. 8. 16..
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.waf.soma.wearefamily.GCM.GCMActivity;

import java.util.TimerTask;

public class CertificationDialog extends DialogFragment {

    int value=180;//인증 유효시간
    TextView mText;

    String count_time= "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        View view = mLayoutInflater.inflate(R.layout.dialog_certification, null);
        mBuilder.setView(view);
        mBuilder.setTitle("인증코드 입력");

        mText = (TextView) view.findViewById(R.id.time);



        // 총 10초간, 1초마다 반복.
        new CountDownTimer(3*60*1000, 1000){

            @Override
            // 타이머 종료시 실행
            public void onFinish() {
                // TODO Auto-generated method stub
                mText.setText("Timer end");
            }
            @Override
            // 주기적 처리 작업
            public void onTick(long arg0) {
                // TODO Auto-generated method stub

                //mText.setText("Value : " + count_time);
                if(value==0){
                    cancel();
                    mText.setText("인증시간이 만료되었습니다.");

                }
                else{
                    value--;

                    count_time = formatTime(value);
                }
            }
        }.start();






    Button btnAgree = (Button) view.findViewById(R.id.btnAgree);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();


                checkNumber();

                Intent myIntent = new Intent(getActivity(), GCMActivity.class);
                startActivity(myIntent);

                getActivity().finish();
            }
        });

        return mBuilder.create();
    }

    public void checkNumber(){
        //값받아서 체크.
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public String formatTime(long millis) {
        String output = "";
        //mText.setText("Value : " + millis);
        long seconds = millis;

        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);


        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;


        output = minutesD + " : " + secondsD;
        mText.setText(output + " 내에 인증코드를 입력해주세요");

        return output;
    }



}

