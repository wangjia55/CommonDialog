package com.jacob.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void oneButton(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment.Builder(this)
                .setTitle("这是标题")
                .setMessage("这是一个通用的DialogFragment组件：只有一个按钮，含有标题")
                .setPositiveButton("确定", -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("TAG", "" + which);
                    }
                }).create();

        dialogFragment.show(getSupportFragmentManager(), "");
    }

    public void twoButton(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment.Builder(this)
                .setMessage("这是一个通用的DialogFragment组件：有两个按钮，没有标题，并可以自己设置按钮背景")
                .setPositiveButton("好的", R.drawable.bg_btn_grey, null)
                .setNegativeButton("取消", R.drawable.bg_btn_orange, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("TAG", "" + which);
                    }
                })
                .create();

        dialogFragment.show(getSupportFragmentManager(), "");
    }

    public void showToast(View view) {
        AlertToast.showToast(getApplicationContext(),"这是一个自定义的Toast");
    }

}
