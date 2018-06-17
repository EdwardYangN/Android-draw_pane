package com.edward.cn.demodraw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class DrawActivity extends AppCompatActivity {

    public MyView3 myView;
    private Button btClear;
    private Button btSave;
    private Button btUndo,btRedo;
    private Button btConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        myView = findViewById(R.id.myView3);
        btClear = findViewById(R.id.btClear);
        btSave = findViewById(R.id.btSave);
        btUndo = findViewById(R.id.btUndo);
        btRedo = findViewById(R.id.btRedo);
        btConfig = findViewById(R.id.btConfig);

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.clearAll();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = myView.saveBitmap(null);
                Toast.makeText(getBaseContext(),"Save done at "+s,Toast.LENGTH_SHORT).show();
            }
        });

        btUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.undo();
            }
        });

        btRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.redo();
            }
        });

        btConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(getBaseContext()).inflate(R.layout.setting_layout,null);
                PopupWindow popWin = new PopupWindow(contentView,500, 500,true);
                popWin.showAsDropDown(view,0,-650);
                Button bt1 = contentView.findViewById(R.id.btPic1);
                Button bt2 = contentView.findViewById(R.id.btPic2);
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myView.saveBitmap("pic1");
                        myView.clearAll();
                        Toast.makeText(getBaseContext(),"pic1 save done",Toast.LENGTH_SHORT).show();
                    }
                });
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myView.saveBitmap("pic2");
                        myView.clearAll();
                        Toast.makeText(getBaseContext(),"pic2 save done",Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(DrawActivity.this,PicActivity.class);
                        startActivity(it);
                    }
                });
            }
        });

    }
}
