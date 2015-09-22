package com.yaowang.builderdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void show(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_text_image)
                .beginConfig()
                .title("哈哈")
                .text("请，你还没登录哦")
                .theme(R.style.dialog_style2)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "立即更新", Toast.LENGTH_SHORT).show();
                    }
                })
                .cancel("暂不更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "暂不更新", Toast.LENGTH_SHORT).show();
                    }
                })
                .endConfig()
                .build();
    }

    public void display(View v) {
        Dialog dialog = new Dialog(this, R.style.dialog_style1);
        dialog.setContentView(R.layout.ly_dialog);
        //样式
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        dialog.show();
    }


}
