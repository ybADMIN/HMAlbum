package com.yb.hmalbumlib;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;


/**
 * 对话框辅助类
 * Created by yb on 15/12/03.
 */
public class HM_SystemDialogUtils {

    /***
     * 获取一个dialog
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.HM_AppBaseTheme_DialogTheme);
        return builder;
    }

    /***
     * 获取一个耗时等待对话框
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog prodialog = new ProgressDialog(context);
//        prodialog.setProgressNumberFormat("我是很好的人：%1d/%2d");
        prodialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prodialog.setIndeterminate(true);
        if (!TextUtils.isEmpty(message)) {
            prodialog.setMessage(message);
        }
        return prodialog;
    }
    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        return builder;
    }


    /**
     * 系统消息提示框
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog getMessageSystemDialog(Context context, String message, String btnText, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(btnText, onClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public static AlertDialog.Builder getUpdataDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setTitle(title);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("升级", onClickListener);
        builder.setNegativeButton("暂不升级", null);
        return builder;
    }
    public static AlertDialog.Builder getForceUpdataDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setTitle(title);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("立即升级", onClickListener);
        builder.setCancelable(false);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, String[] btnText, DialogInterface.OnClickListener onOkClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton(btnText[0], onOkClickListener);
        if(btnText.length>1) {
            builder.setNegativeButton(btnText[1], null);
        }
        return builder;
    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String message, String[] btnText, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(btnText[0], onOkClickListener);
        builder.setNegativeButton(btnText[1], onClickListener);
        return builder;
    }
    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String message, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        if(!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton(arrays[0], onClickListener);
        if(arrays.length>1)
            builder.setNegativeButton(arrays[1], null);
        return builder;
    }
}
