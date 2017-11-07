package com.yb.hmalbumlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.yb.hmalbumlib.engine.HM_ImageEngine;
import com.yb.hmalbumlib.extar.HM_ExtarCropImageView;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.ui.HM_MultiAlbumActivity;

import java.util.ArrayList;

/**
 * Created by yb on 2016/6/23.
 */
public class HM_StartAlbum {
    private final static String Extar = "extra_album_";
    public final static String EXTRA_ALBUM_CHOOSE_DATA = Extar + "data";//已经选择的照片
    public final static String EXTRA_ALBUM_MAXCHOOSE = Extar + "max_choose";//最大选择数量
    public static final String EXTRA_ALBUM_ISCROP = Extar + "isCrop";//是否裁剪
    public static final String EXTRA_ALBUM_SHOWCAMERA = Extar + "showCamera";//是否裁剪
    public static final String EXTRA_CORP_CROPMODE = HM_StartCorp.EXTRA_CORP_CORPMODE;//裁剪类型
    public static final String EXTRA_CORP_COMPRESSQUALITY = HM_StartCorp.EXTRA_CORP_COMPRESSQUALITY;
    public static final String EXTRA_CORP_MAXOUTPUTW = HM_StartCorp.EXTRA_CORP_MAXOUTPUTW;
    public static final String EXTRA_CORP_MAXOUTPUTH = HM_StartCorp.EXTRA_CORP_MAXOUTPUTH;
    public static final String EXTRA_CORP_COMPRESSFORMAT = HM_StartCorp.EXTRA_CORP_COMPRESSFORMAT;

    @SuppressWarnings("unchecked")
    private HM_StartAlbum(Context mContext, Bulide bulide) {
        Intent intent = new Intent(mContext, HM_MultiAlbumActivity.class);
        if (bulide.maxChoose != 0)
            intent.putExtra(EXTRA_ALBUM_MAXCHOOSE, bulide.maxChoose);

        intent.putExtra(EXTRA_ALBUM_ISCROP, bulide.isCrop);

        intent.putExtra(EXTRA_ALBUM_SHOWCAMERA, bulide.showCamera);

        if (bulide.chooseImages != null) {
            intent.putParcelableArrayListExtra(EXTRA_ALBUM_CHOOSE_DATA, (ArrayList<? extends Parcelable>) bulide.chooseImages);
        }
        if (bulide.compressFormat != null) {
            intent.putExtra(EXTRA_CORP_COMPRESSFORMAT, bulide.compressFormat);
        }
        if (bulide.compressQuality != 0) {
            intent.putExtra(EXTRA_CORP_COMPRESSQUALITY, bulide.compressQuality);
        }
        if (bulide.maxOutPutW != 0) {
            intent.putExtra(EXTRA_CORP_MAXOUTPUTW, bulide.maxOutPutW);
        }
        if (bulide.maxOutPutH != 0) {
            intent.putExtra(EXTRA_CORP_MAXOUTPUTH, bulide.maxOutPutH);
        }
        if (bulide.cropMode !=null) {
            intent.putExtra(EXTRA_CORP_CROPMODE, bulide.cropMode);
        }
        if (bulide.requestCode ==null){
            mContext.startActivity(intent);
        }else {
            ((AppCompatActivity) mContext).startActivityForResult(intent, bulide.requestCode);
        }
    }

    public static class Bulide {
        private Context mContext;
        private int maxChoose;
        private boolean isCrop ;
        private ArrayList<HM_ImgData> chooseImages;
        private int compressQuality;
        private int maxOutPutW;
        private int maxOutPutH;
        private Bitmap.CompressFormat compressFormat;
        private HM_ExtarCropImageView.CropMode cropMode;
        Integer requestCode;
        private boolean showCamera=true;

        public Bulide(Context context, HM_ImageEngine engine) {
            HM_AlbumConifg.getInstance().setEngine(engine);
            this.mContext = context;
        }


        public Bulide setCrop(boolean crop) {
            this.isCrop = crop;
            return this;
        }

        public Bulide setCompressQuality(int compressQuality) {
            this.compressQuality = compressQuality;
            return this;
        }

        public Bulide setMaxOutPutW(int maxOutPutW) {
            this.maxOutPutW = maxOutPutW;
            return this;
        }

        public Bulide setMaxOutPutH(int maxOutPutH) {
            this.maxOutPutH = maxOutPutH;
            return this;
        }

        public int maxChoose() {
            return maxChoose;
        }

        public Bulide setMaxChoose(int maxChoose) {
            this.maxChoose = maxChoose;
            return this;
        }

        public Bulide setRequestCode(Integer requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Bulide setChooseImages(ArrayList<HM_ImgData> chooseImages) {
            this.chooseImages = chooseImages;
            return this;
        }

        public Bulide setCompressFormat(Bitmap.CompressFormat compressFormat) {
            this.compressFormat = compressFormat;
            return this;
        }

        public Bulide setCropMode(HM_ExtarCropImageView.CropMode cropMode) {
            this.cropMode = cropMode;
            return this;
        }

        public ArrayList<HM_ImgData> getChooseImages() {
            return chooseImages;
        }

        public HM_StartAlbum bulide() {
            return new HM_StartAlbum(mContext, this);
        }

        public Bulide setShowCamera(boolean showCamera) {
            this.showCamera = showCamera;
            return this;
        }
    }
}
