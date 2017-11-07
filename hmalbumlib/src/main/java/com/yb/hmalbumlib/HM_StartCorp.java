package com.yb.hmalbumlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.yb.hmalbumlib.engine.HM_ImageEngine;
import com.yb.hmalbumlib.extar.HM_ExtarCropImageView;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.ui.HM_AlbumCorpActivity;


/**
 * Created by yb on 2016/6/23.
 */
public class HM_StartCorp {
    private final static String Extar="extra_corp_";
    public final static String EXTRA_CORP_CHOOSE_DATA = Extar+"data";//已经选择的照片
    public final static String EXTRA_CORP_CORPMODE= Extar+"corpMode";//裁剪类型
    public static final String EXTRA_CORP_COMPRESSQUALITY = Extar + "compressQuality";
    public static final String EXTRA_CORP_MAXOUTPUTW =  Extar +"maxOutPutW";
    public static final String EXTRA_CORP_MAXOUTPUTH=Extar+"maxOutPutH";
    public static final String EXTRA_CORP_COMPRESSFORMAT =Extar+ "compressFormat";
    public static final int REQUEST_CODE_CROP=2;

    private HM_StartCorp(Context mContext, Bulide bulide) {
        Intent intent = new Intent(mContext, HM_AlbumCorpActivity.class);
        if (bulide.CorpMode!=null){
            intent.putExtra(EXTRA_CORP_CORPMODE,bulide.CorpMode);
        }
        if (bulide.compressQuality!=0){//裁剪
            intent.putExtra(EXTRA_CORP_COMPRESSQUALITY,bulide.compressQuality);
        }
        if (bulide.maxOutPutH!=0){
            intent.putExtra(EXTRA_CORP_MAXOUTPUTH,bulide.maxOutPutH);
        }
        if (bulide.maxOutPutW!=0){
            intent.putExtra(EXTRA_CORP_MAXOUTPUTW,bulide.maxOutPutW);
        }
        if (bulide.compressFormat!=null){
            intent.putExtra(EXTRA_CORP_COMPRESSFORMAT,bulide.compressFormat);
        }

           if (bulide.chooseImage!=null){
               intent.putExtra( EXTRA_CORP_CHOOSE_DATA, (Parcelable) bulide.chooseImage);
               intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               ((AppCompatActivity)mContext).startActivityForResult(intent,bulide.requestCode==null? HM_StartCorp.REQUEST_CODE_CROP:bulide.requestCode);
           }
    }

    public static   class  Bulide{
        private Context mContext;
        private HM_ImgData chooseImage;
        private int compressQuality;
        private int maxOutPutW;
        private int maxOutPutH;
        private Bitmap.CompressFormat compressFormat;
        Integer requestCode;
        private  HM_ExtarCropImageView.CropMode CorpMode= HM_ExtarCropImageView.CropMode.FIT_IMAGE;


        public Bulide setCorpMode(HM_ExtarCropImageView.CropMode corpMode) {
            CorpMode = corpMode;
            return this;
        }

        public Bulide(Context context, HM_ImageEngine engine) {
            HM_AlbumConifg.getInstance().setEngine(engine);
            this.mContext = context;
        }

        public Bulide setChooseImage(HM_ImgData chooseImages){
            this.chooseImage=chooseImages;
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

        public Bulide setCompressFormat(Bitmap.CompressFormat compressFormat) {
            this.compressFormat = compressFormat;
            return this;
        }

        public Bulide setMaxOutPutH(int maxOutPutH) {
            this.maxOutPutH = maxOutPutH;
            return this;
        }

        public Bulide setRequestCode(Integer requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public HM_StartCorp bulide(){
           return new HM_StartCorp(mContext,this);
        }
    }
}
