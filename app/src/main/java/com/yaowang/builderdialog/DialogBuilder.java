package com.yaowang.builderdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    static Builder builder;
    public static final int DEFAULT_WIDTH = -1;
    public static final int DEFAULT_HIDE = DEFAULT_WIDTH - 1;
    public static final int BOTTOM_TOP_HIDE = DEFAULT_HIDE - 1;
    public static final int TOP_HIDE = BOTTOM_TOP_HIDE - 1;

    public DialogBuilder(Builder builder) {
        super(builder.context, builder.theme);

    }

    public static IDialogBuilder Builder(Context context) {
        dialogView = View.inflate(context, R.layout.ly_dialog, null);
        builder = new Builder(dialogView);
        return builder;
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


        IConfigBuilder gravity(int gravity);

        IConfigBuilder width(int width);

        IConfigBuilder hide(int hide);

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
        int gravity = Gravity.CENTER;
        int width = DialogBuilder.DEFAULT_WIDTH;
        int hide = DialogBuilder.DEFAULT_HIDE;
        Context context;
        int theme;
        String title;
        TextView text;
        View dialogView;
        TextView titleView;
        FrameLayout containerLayout;
        LinearLayout titleLayout;
        LinearLayout bottomLayout;
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
            containerLayout = (FrameLayout) dialogView.findViewById(R.id.containerLayout);
            titleLayout = (LinearLayout) dialogView.findViewById(R.id.titleLayout);
            bottomLayout = (LinearLayout) dialogView.findViewById(R.id.bottomLayout);

            okButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
        }

        @Override
        public DialogBuilder build() {
            dialogBuilder = new DialogBuilder(this);
            dialogBuilder.setContentView(dialogView);
            Window dialogWindow = dialogBuilder.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = context.getResources().getDisplayMetrics();
            lp.width = width == DialogBuilder.DEFAULT_WIDTH ? (int) (d.widthPixels * 0.8) : width;

            dialogWindow.setGravity(gravity);
            dialogBuilder.setCanceledOnTouchOutside(cancelable);
            handleHide();
            dialogWindow.setAttributes(lp);
            dialogBuilder.show();
            return dialogBuilder;
        }

        private void handleHide() {
            switch (hide) {
                case DialogBuilder.DEFAULT_HIDE:
                    titleLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    containerLayout.setVisibility(View.VISIBLE);
                    break;
                case DialogBuilder.BOTTOM_TOP_HIDE:
                    titleLayout.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.GONE);
                    containerLayout.setVisibility(View.VISIBLE);
                    break;
                case DialogBuilder.TOP_HIDE:
                    titleLayout.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    containerLayout.setVisibility(View.VISIBLE);
                    break;
            }
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

        public IConfigBuilder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        @Override
        public IConfigBuilder width(int width) {
            this.width = width;
            return this;
        }

        @Override
        public IConfigBuilder hide(int hide) {
            this.hide = hide;
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
            if (containerLayout != null && containerLayout.getChildCount() != 0) {
                for (int i = 0; i < containerLayout.getChildCount(); i++) {
                    containerLayout.removeViewAt(i);
                }
            }
            if (containerLayout.getChildCount() == 0 && view != null) {
                containerLayout.addView(view);
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
