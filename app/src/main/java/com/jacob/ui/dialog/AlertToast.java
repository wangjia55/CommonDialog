package com.jacob.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Package : com.jacob.ui.dialog
 * Author : jacob
 * Date : 15-6-11
 * Description : 这个类是用来xxx
 */
public class AlertToast {
    public static void showToast(Context context, String msg) {
        if (context == null) return;
        Toast toast = getToast(context, msg);
        if (toast != null) toast.show();
    }

    public static void showToast(Context context, int resourceId) {
        if (context == null) return;
        Toast toast = getToast(context, context.getResources().getString(resourceId));
        if (toast != null) toast.show();
    }

    private static Toast getToast(Context context, String msg) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        return toast;
    }

}
