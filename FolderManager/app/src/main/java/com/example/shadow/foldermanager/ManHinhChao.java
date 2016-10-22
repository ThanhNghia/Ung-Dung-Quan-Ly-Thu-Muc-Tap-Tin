package com.example.shadow.foldermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ManHinhChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        Thread bamGio = new Thread()
        {
            public void run()
            {
                try {
                    sleep(2000);
                }catch (Exception e)
                {

                }finally {
                    Intent chuyenMH_Main = new Intent(ManHinhChao.this,MainActivity.class);
                    startActivity(chuyenMH_Main);
                }
            }
        };
        bamGio.start();
    }
}
