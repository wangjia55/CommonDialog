package com.jacob.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Package : com.jacob.ui.dialog
 * Author : jacob
 * Date : 15-6-11
 * Description : 这个类是一个通用的对话框组件
 */
public class AlertDialogFragment extends DialogFragment implements DialogInterface {
    private final static String DIALOG_BUILDER = "dialog_builder";

    private Dialog mDialog;
    private Builder mBuilder;
    private Button mButtonNegative;
    private Button mButtonPositive;
    private TextView mTextViewMessage;
    private TextView mTextViewTitle;
    private ButtonHandler mHandler;
    private Message mButtonPositiveMessage;
    private Message mButtonNegativeMessage;
    private DialogInterface mDialogInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBuilder = (Builder) getArguments().getSerializable(DIALOG_BUILDER);
        }
        mHandler = new ButtonHandler(this);
        mDialogInterface = this;
    }

    private static AlertDialogFragment newInstance(Builder builder) {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DIALOG_BUILDER, builder);
        alertDialogFragment.setArguments(bundle);
        return alertDialogFragment;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDialog();
        initDialogView();
        fillDialogView();
        return mDialog;
    }

    private void initDialog() {
        mDialog = new Dialog(getActivity(), R.style.dialogWithNoTitle);
        mDialog.requestWindowFeature(STYLE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.layout_common_dialog_alert_dialog);
        setDialogWidth(getActivity(), mDialog);
    }

    private void initDialogView() {
        mTextViewTitle = (TextView) mDialog.findViewById(R.id.text_view_dialog_title);
        mTextViewMessage = (TextView) mDialog.findViewById(R.id.text_view_dialog_msg);
        mButtonPositive = (Button) mDialog.findViewById(R.id.button_positive);
        mButtonNegative = (Button) mDialog.findViewById(R.id.button_negative);
    }

    private void fillDialogView() {

        //build title
        if (mBuilder.getTitle() != null) {
            mTextViewTitle.setText(mBuilder.getTitle());
            mTextViewTitle.setVisibility(View.VISIBLE);
        } else {
            mTextViewTitle.setVisibility(View.GONE);
        }

        //build content
        if (mBuilder.getMessage() != null) {
            mTextViewMessage.setText(mBuilder.getMessage());
        }

        //positive button
        if (mBuilder.getPositiveButtonText() != null) {
            mButtonPositiveMessage = mHandler.obtainMessage();
            mButtonPositiveMessage.obj = mBuilder.getPositiveButtonListener();
            mButtonPositiveMessage.what = BUTTON_POSITIVE;

            mButtonPositive.setVisibility(View.VISIBLE);
            mButtonPositive.setText(mBuilder.getPositiveButtonText());
            mButtonPositive.setOnClickListener(mButtonHandler);
            if (mBuilder.getPositiveButtonDrawable() != -1) {
                mButtonPositive.setBackgroundResource(mBuilder.getPositiveButtonDrawable());
            }
        } else {
            mButtonNegative.setVisibility(View.GONE);
        }

        //negative button
        if (mBuilder.getNegativeButtonText() != null) {
            mButtonNegativeMessage = mHandler.obtainMessage();
            mButtonNegativeMessage.obj = mBuilder.getNegativeButtonListener();
            mButtonNegativeMessage.what = BUTTON_NEGATIVE;

            mButtonNegative.setText(mBuilder.getNegativeButtonText());
            mButtonNegative.setVisibility(View.VISIBLE);
            mButtonNegative.setOnClickListener(mButtonHandler);
            if (mBuilder.getNegativeButtonDrawable() != -1) {
                mButtonNegative.setBackgroundResource(mBuilder.getNegativeButtonDrawable());
            }
        } else {
            mButtonNegative.setVisibility(View.GONE);
        }


    }

    @Override
    public Dialog getDialog() {
        return mDialog;
    }

    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Message message;
            if (v == mButtonPositive) {
                message = Message.obtain(mButtonPositiveMessage);
            } else if (v == mButtonNegative) {
                message = Message.obtain(mButtonNegativeMessage);
            } else {
                message = null;
            }

            if (message != null) {
                message.sendToTarget();
            }

            // Post a message so we dismiss after the above handlers are executed
            mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, mDialogInterface)
                    .sendToTarget();
        }
    };


    private static final class ButtonHandler extends Handler {
        // Button clicks have Message.what as the BUTTON{1,2,3} constant
        private static final int MSG_DISMISS_DIALOG = 1;

        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<DialogInterface>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    DialogInterface.OnClickListener listener = ((DialogInterface.OnClickListener) msg.obj);
                    if (listener != null) {
                        listener.onClick(mDialog.get(), msg.what);
                    }

                    break;

                case MSG_DISMISS_DIALOG:
                    ((DialogInterface) msg.obj).dismiss();
            }
        }
    }

    /**
     * Builder类，包含Dialog需要显示的信息，如果以后需要新增配置项，在这个类中修改
     */
    public static class Builder implements Serializable {

        private Context context;
        private CharSequence title;
        private CharSequence message;
        private int mPositiveButtonDrawable;
        private int mNegativeButtonDrawable;
        private CharSequence mPositiveButtonText;
        private CharSequence mNegativeButtonText;

        private DialogInterface.OnClickListener mPositiveButtonListener;
        private DialogInterface.OnClickListener mNegativeButtonListener;


        public Builder(Context context) {
            this.context = context;

        }

        public Context getContext() {
            return context;
        }

        public Builder setTitle(CharSequence titleContent) {
            this.title = titleContent;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;

        }

        public Builder setPositiveButton(CharSequence text, int drawableId, final OnClickListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            mPositiveButtonDrawable = drawableId;
            return this;
        }

        public Builder setPositiveButton(int textId, int drawableId, final OnClickListener listener) {
            mPositiveButtonText = context.getText(textId);
            mPositiveButtonListener = listener;
            mPositiveButtonDrawable = drawableId;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, int drawableId, final OnClickListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonDrawable = drawableId;
            mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, int drawableId, final OnClickListener listener) {
            mNegativeButtonText = context.getText(textId);
            mNegativeButtonDrawable = drawableId;
            mNegativeButtonListener = listener;
            return this;
        }

        public int getPositiveButtonDrawable() {
            return mPositiveButtonDrawable;
        }

        public int getNegativeButtonDrawable() {
            return mNegativeButtonDrawable;
        }

        public CharSequence getPositiveButtonText() {
            return mPositiveButtonText;
        }

        public CharSequence getNegativeButtonText() {
            return mNegativeButtonText;
        }

        public CharSequence getTitle() {
            return title;
        }

        public OnClickListener getPositiveButtonListener() {
            return mPositiveButtonListener;
        }

        public OnClickListener getNegativeButtonListener() {
            return mNegativeButtonListener;
        }

        public CharSequence getMessage() {
            return message;
        }


        public AlertDialogFragment create() {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(this);
            return alertDialogFragment;
        }
    }

    @Override
    public void cancel() {

    }

    void setDialogWidth(Context context, Dialog dialog) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);

        //取得窗口属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = (int) (dm.widthPixels * 0.82f); // 宽度
        lp.alpha = 0.9f; // 透明度
        lp.dimAmount = 0.6f;      //设置黑暗度
        dialogWindow.setAttributes(lp);
    }
}
