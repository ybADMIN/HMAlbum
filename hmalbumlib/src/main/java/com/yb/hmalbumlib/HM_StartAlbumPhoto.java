package com.yb.hmalbumlib;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.yb.hmalbumlib.engine.HM_ImageEngine;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.ui.HM_PhotoPagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yb on 2016/6/23.
 */
public class HM_StartAlbumPhoto {
    private final static String Extar = "extra_photosee_";
    public final static String EXTRA_PHOTOSEE_DATA = Extar + "data";
    public final static String EXTRA_PHOTOSEE_CURRENTSELECT = Extar + "currentselect";
    public static String EXTRA_PHOTOSEE_MODE= Extar + "mode";
    public static String EXTRA_PHOTOSEE_CLICKCLOSE = Extar + "clickclose";
    public static String EXTRA_PHOTOSEE_CHOOSEIMAGE = Extar + "chooseimage";
    public static String EXTRA_PHOTOSEE_MAXCHOOSE = Extar + "maxChoose";
    public static final int REQUESTCODE=1;
    private HM_StartAlbumPhoto(Context mContext, Bulide bulide) {
        Intent intent = new Intent(mContext, HM_PhotoPagerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bulide.list != null && bulide.list.size() > 0) {
            intent.putParcelableArrayListExtra(EXTRA_PHOTOSEE_DATA, (ArrayList<? extends Parcelable>) bulide.list);
            if (bulide.currentSelect != 0)
                intent.putExtra(EXTRA_PHOTOSEE_CURRENTSELECT, bulide.currentSelect);

            intent.putExtra(EXTRA_PHOTOSEE_CLICKCLOSE, bulide.isClickHideTitle);
            if (bulide.mode==null) throw new RuntimeException("启动模式不能为空");
            intent.putExtra(EXTRA_PHOTOSEE_MODE,bulide.mode);
            if (bulide.maxChoose != 0)
                intent.putExtra(EXTRA_PHOTOSEE_MAXCHOOSE, bulide.maxChoose);
            if (bulide.chooseImages != null) {
                intent.putParcelableArrayListExtra(EXTRA_PHOTOSEE_CHOOSEIMAGE, (ArrayList<? extends Parcelable>) bulide.chooseImages);
            }


            if (bulide.isForResult){
                if (bulide.requestCode!=null){
                    ((AppCompatActivity) mContext).startActivityForResult(intent, bulide.requestCode);
                }else {
                    ((AppCompatActivity) mContext).startActivity(intent);
                }
            }
            else
             (mContext).startActivity(intent);
        }
    }

    /**
     * {@code
     * IMAGEFORCAMERA 照相机返回
     * EDITCHOOSEIMAGE 照片编辑
     * ALBUMSEE 照片查看 只有ALBUMSEE 可以在外部设置}
     *
     */
    public  enum  AlbumPhotoMode{
        IMAGEFORCAMERA,EDITCHOOSEIMAGE,ALBUMSEE
    }

    public static class Bulide {
        private Context mContext;
        private List<HM_ImgData> list;
        private int currentSelect;
        private boolean isClickHideTitle = true;
        private boolean isForResult = true;
        private List<HM_ImgData> chooseImages;
        private int maxChoose ;
        private AlbumPhotoMode mode;// 只有ALBUMSEE 可以在外部设置
        private Integer requestCode;//外部启动自定义请求码
        public Bulide(Context context, HM_ImageEngine engine) {
            HM_AlbumConifg.getInstance().setEngine(engine);
            this.mContext = context;
        }
        public List<? extends HM_ImgData> list() {
            return list;
        }

        public Bulide setList(List<HM_ImgData> list) {
            this.list = list;
            return this;
        }

        public Bulide setClickHideTitle(boolean clickHideTitle) {
            this.isClickHideTitle = clickHideTitle;
            return this;
        }

        public int currentSelect() {
            return currentSelect;
        }

        public Bulide setCurrentSelect(int currentSelect) {
            this.currentSelect = currentSelect;
            return this;
        }

        public Bulide setChooseImages(List<HM_ImgData> chooseImages) {
            this.chooseImages = chooseImages;
            return this;
        }


        public Bulide setMode(AlbumPhotoMode mode) {
            this.mode = mode;
            return this;
        }

        public Bulide setMaxChoose(int maxChoose) {
            this.maxChoose = maxChoose;
            return this;
        }

        public Bulide setForResult(boolean forResult) {
            isForResult = forResult;
            return this;
        }


        public HM_StartAlbumPhoto bulide() {
            return new HM_StartAlbumPhoto(mContext, this);
        }

        /**
         * 外部启动自定义请求码
         * @param requestCode
         * @return
         */
        public Bulide setRequestCode(Integer requestCode) {
            this.requestCode = requestCode;
            return this;
        }
    }
}
