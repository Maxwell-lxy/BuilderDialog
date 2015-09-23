package com.yaowang.builderdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-21 23:41
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DialogBuilder extends Dialog {

    static View dialogView;

    public DialogBuilder(Builder builder) {
        super(builder.context, builder.theme);

    }

    public static IDialogBuilder Builder(Context context) {
        dialogView = View.inflate(context, R.layout.ly_dialog, null);

        return new Builder(dialogView);
    }


    public static interface IBuilder {
        public DialogBuilder build();
    }

    public interface IConfigBuilder {
        IConfigBuilder title(int resId);

        IConfigBuilder title(String title);

        IConfigBuilder text(int resId);

        IConfigBuilder text(String text);


        IConfigBuilder ok(View.OnClickListener onClickListener);

        IConfigBuilder ok(int resId, View.OnClickListener onClickListener);

        IConfigBuilder ok(String content, View.OnClickListener onClickListener);

        IConfigBuilder cancel(View.OnClickListener onClickListener);

        IConfigBuilder cancel(int resId, View.OnClickListener onClickListener);

        IConfigBuilder cancel(String cotent, View.OnClickListener onClickListener);

        IConfigBuilder theme(int styId);

        IConfigBuilder canceledOnTouchOutside(boolean iscancelable);
        IConfigBuilder dismiss(boolean isdismiss);

        IBuilder endConfig();
    }

    public static interface IDialogBuilder {
        IConfigBuilder beginConfig();

        IDialogBuilder content(int resId);

        IDialogBuilder content(View view);
        IDialogBuilder dismissDialog();
    }

    public static class Builder implements IBuilder, IConfigBuilder, IDialogBuilder {
        boolean dismiss = true;
        boolean cancelable = true;
        Context context;
        int theme;
        String title;
        TextView text;
        View dialogView;
        TextView titleView;
        FrameLayout containerView;
        View contentView;
        Button okButton;
        Button cancelButton;
        DialogBuilder dialogBuilder;


        Builder(View dialogView) {
            this.context = dialogView.getContext();
            this.dialogView = dialogView;
            titleView = (TextView) dialogView.findViewById(R.id.title);
            okButton = (Button) dialogView.findViewById(R.id.ok);
            cancelButton = (Button) dialogView.findViewById(R.id.cancel);
            containerView = (FrameLayout) dialogView.findViewById(R.id.containerView);
        }

        @Override
        public DialogBuilder build() {
            dialogBuilder = new DialogBuilder(this);
            dialogBuilder.setContentView(dialogView);
            Window dialogWindow = dialogBuilder.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = context.getResources().getDisplayMetrics();
            lp.width = (int) (d.widthPixels * 0.8);
            dialogWindow.setAttributes(lp);

            dialogBuilder.setCanceledOnTouchOutside(cancelable);
            dialogBuilder.show();
            return dialogBuilder;
        }

        @Override
        public IConfigBuilder title(int resId) {
            title(context.getString(resId));
            return this;
        }

        @Override
        public IConfigBuilder title(String title) {
            this.title = title;
            titleView.setText(title);
            return this;
        }

        @Override
        public IConfigBuilder text(int resId) {
            if (resId != -1)
                text(context.getString(resId));
            return this;
        }

        @Override
        public IConfigBuilder text(String content) {
            try {
                text = (TextView) contentView.findViewById(R.id.text);
                text.setText(content);
            } catch (Exception e) {
                throw new RuntimeException("no id=text in layout");
            }
            return this;
        }


        @Override
        public IConfigBuilder ok(final View.OnClickListener onClickListener) {
            performButton(okButton, "确定", onClickListener);
            return this;
        }

        @Override
        public IConfigBuilder ok(int resId, View.OnClickListener onClickListener) {
            if (okButton != null && resId != -1) {
                ok(context.getString(resId), onClickListener);
            }
            return this;
        }

        @Override
        public IConfigBuilder ok(String content, final View.OnClickListener onClickListener) {
            performButton(okButton, content, onClickListener);
            return this;
        }

        @Override
        public IConfigBuilder cancel(final View.OnClickListener onClickListener) {
            performButton(cancelButton, "取消", onClickListener);
            return this;
        }

        @Override
        public IConfigBuilder cancel(int resId, View.OnClickListener onClickListener) {
            if (cancelButton != null && resId != -1) {
                cancel(context.getString(resId), onClickListener);
            }
            return this;
        }

        @Override
        public IConfigBuilder cancel(String content, final View.OnClickListener onClickListener) {
            if (cancelButton != null)
                performButton(cancelButton, content, onClickListener);
            return this;
        }

        public void performButton(Button button, String content, final View.OnClickListener onClickListener) {
            if (button == null || TextUtils.isEmpty(content)) {
                throw new RuntimeException("no find button or content is empty in contentView");
            }
            if (!TextUtils.isEmpty(content)) {
                button.setText(content);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (onClickListener != null)
                        onClickListener.onClick(view);
                }
            });
            button.setVisibility(View.VISIBLE);
        }


        @Override
        public IConfigBuilder theme(int styId) {
            theme = styId;
            return this;
        }

        @Override
        public IConfigBuilder canceledOnTouchOutside(boolean iscancelable) {
            cancelable = iscancelable;
            return this;
        }

        @Override
        public IConfigBuilder dismiss(boolean isdismiss) {
            this.dismiss = isdismiss;
            return this;
        }

        @Override
        public IDialogBuilder content(int resId) {
            if (resId != -1) {
                View view = View.inflate(context, resId, null);
                content(view);
            }
            return this;
        }

        @Override
        public IDialogBuilder content(View view) {
            contentView = view;
            if (containerView != null && containerView.getChildCount() != 0) {
                for (int i = 0; i < containerView.getChildCount(); i++) {
                    containerView.removeViewAt(i);
                }
            }
            if (containerView.getChildCount() == 0 && view != null) {
                containerView.addView(view);
            }
            return this;
        }

        @Override
        public IDialogBuilder dismissDialog() {
            if (dialogBuilder != null && dialogBuilder.isShowing() )
                dialogBuilder.dismiss();
            return this;
        }
        public void dismiss() {
            if (dialogBuilder != null && dialogBuilder.isShowing() && dismiss)
                dialogBuilder.dismiss();
        }
        @Override
        public IBuilder endConfig() {
            return this;
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

    }


}
