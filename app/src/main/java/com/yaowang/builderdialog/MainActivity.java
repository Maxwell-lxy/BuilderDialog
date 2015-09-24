package com.yaowang.builderdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    public void onClick1(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_textview)
                .beginConfig()
                .title("这是标题")
                .text("这是内容")
                .theme(R.style.dialog_style1)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .cancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()
                .build();
    }

    public void onClick2(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_text_image)
                .beginConfig()
                .title("这是标题")
                .text("这是内容")
                .theme(R.style.dialog_style1)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .cancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()
                .build();
    }

    public void onClick3(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_custom)
                .beginConfig()
                .title("这是标题")
                .theme(R.style.dialog_style1)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()
                .build();
    }

    public void onClick4(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_textview)
                .beginConfig()
                .text("带动画的dialog")
                .title("这是标题")
                .theme(R.style.dialog_style2)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()
                .build();
    }

    public void onClick5(View v) {
        DialogBuilder.Builder(this)
                .content(R.layout.ly_dialog_textview)
                .beginConfig()
                .canceledOnTouchOutside(false)
                .text("带动画的dialog")
                .title("这是标题")
                .theme(R.style.dialog_style2)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .endConfig()
                .build();
    }

    public void onClick6(View v) {
        final DialogBuilder.IDialogBuilder dialogBuilder = DialogBuilder.Builder(this);
        dialogBuilder.content(R.layout.ly_dialog_textview)
                .beginConfig()
                .canceledOnTouchOutside(false)
                .text("自己处理dismiss")
                .dismiss(false)
                .title("这是标题")
                .theme(R.style.dialog_style2)
                .ok(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点击取消dissmissDialog", Toast.LENGTH_SHORT).show();
                    }
                })
                .cancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismissDialog();
                    }
                })
                .endConfig()
                .build();
    }

    public void onClick7(View v) {
        final DialogBuilder.IDialogBuilder dialogBuilder = DialogBuilder.Builder(this);
        dialogBuilder.content(R.layout.ly_share_pop)
                .beginConfig()
                .canceledOnTouchOutside(true)
                .dismiss(false)
                .title("分享到")
                .theme(R.style.dialog_style3)
                .cancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dialogBuilder.dismissDialog();
                    }
                })
                .gravity(Gravity.BOTTOM)
                .hide(DialogBuilder.DEFAULT_HIDE)
                .width(DisplayUtil.getScreenWidth(this))
                .endConfig()
                .build();
    }

}
