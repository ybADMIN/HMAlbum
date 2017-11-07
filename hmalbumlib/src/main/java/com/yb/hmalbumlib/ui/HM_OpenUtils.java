package com.yb.hmalbumlib.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.yb.hmalbumlib.extar.HM_StorageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class HM_OpenUtils {

    private final SharedPreferences mPreferences;

    public HM_OpenUtils(SharedPreferences preferences) {
        mPreferences=preferences;
    }
    public static final String TAG = "DevicesUtils";
    public static final int REQUEST_CODE_CAMERA = 201;
    public static final String CAMERA_PHOTO = "CAMERA_PHOTO";

    /**
     * 打开相机
     * @param context
     */
    public  void openCamera(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = HM_StorageUtil.getAlbumDir(context);
            if (file==null)return;
            String photographUrl = file+"/" + new SimpleDateFormat("yyyymmdd_hhmmss").format(new Date()) + System.currentTimeMillis()+ ".png";
            mPreferences.edit().putString(CAMERA_PHOTO, photographUrl).apply();
            Uri uri = Uri.fromFile(new File(photographUrl));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        ((Activity)context).startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 获取返回的路径
     * @param requestCode
     * @param resultCode
     * @param data
     * @param context
     * @return
     */
    public  String getReturnImagePath(int requestCode, int resultCode, Intent data, Context context){
        String imgPath = "";
        if(!(context instanceof Activity)) return imgPath;
       if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            imgPath = mPreferences.getString(CAMERA_PHOTO, "");
        }
        return imgPath;
    }

}
