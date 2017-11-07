package com.yb.hmalbumlib.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.yb.hmalbumlib.HM_AlbumConifg;
import com.yb.hmalbumlib.HM_StartCorp;
import com.yb.hmalbumlib.HM_SystemDialogUtils;
import com.yb.hmalbumlib.R;
import com.yb.hmalbumlib.extar.HM_DisplayUtil;
import com.yb.hmalbumlib.extar.HM_ExtarCropImageView;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.extar.HM_StorageUtil;
import com.yb.hmalbumlib.extar.namegenerator.HM_Md5FileNameGenerator;

import java.io.File;

public class HM_AlbumCorpActivity extends AppCompatActivity implements View.OnClickListener {

    HM_ExtarCropImageView cropImageView;
    RelativeLayout corpButtomToolbar;
    ImageButton buttonRotateLeft;
    ImageButton buttonRotateRight;
    ImageButton buttonDone;
    private ProgressDialog waitingDialog;
    private HM_ImgData path;
    private HM_ExtarCropImageView.CropMode mCropMode;
    private TextView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hm_activity_album_corp);
        cropImageView= (HM_ExtarCropImageView) findViewById(R.id.cropImageView);
        corpButtomToolbar= (RelativeLayout) findViewById(R.id.corpButtomToolbar);
        buttonRotateLeft= (ImageButton) findViewById(R.id.buttonRotateLeft);
        buttonRotateRight= (ImageButton) findViewById(R.id.buttonRotateRight);
        buttonDone= (ImageButton) findViewById(R.id.buttonDone);
        backBtn= (TextView) findViewById(R.id.actionbar_lift);
        buttonRotateLeft.setOnClickListener(this);
        buttonRotateRight.setOnClickListener(this);
        buttonDone.setOnClickListener(this);

        setToolbar();
        initData();
    }


    private void initData() {
        path=getIntent().getParcelableExtra(HM_StartCorp.EXTRA_CORP_CHOOSE_DATA);
        mCropMode= (HM_ExtarCropImageView.CropMode)getIntent().getSerializableExtra(HM_StartCorp.EXTRA_CORP_CORPMODE);
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) getIntent().getSerializableExtra(HM_StartCorp.EXTRA_CORP_COMPRESSFORMAT);
        int compressQuality =getIntent().getIntExtra(HM_StartCorp.EXTRA_CORP_COMPRESSQUALITY,90);
        int maxOutputW =getIntent().getIntExtra(HM_StartCorp.EXTRA_CORP_MAXOUTPUTW,300);
        int maxOutPutH =getIntent().getIntExtra(HM_StartCorp.EXTRA_CORP_MAXOUTPUTH,300);
        if (path!=null){
            if (mCropMode==null){
                mCropMode= HM_ExtarCropImageView.CropMode.FREE;
            }
            if (compressFormat==null){
                compressFormat= Bitmap.CompressFormat.JPEG;
            }
            cropImageView.setCropMode(mCropMode);
            cropImageView.setCompressFormat(compressFormat);
            cropImageView.setCompressQuality(compressQuality);
            cropImageView.setOutputMaxSize(maxOutputW, maxOutPutH);
            cropImageView.setInitialFrameScale(0.9f);
//            cropImageView.startLoad(Uri.fromFile(new File(path.imgPath)),mLoadCallback);
            HM_AlbumConifg.getInstance().getEngine().loadImage(this, HM_DisplayUtil.getDisplayWidth(this),HM_DisplayUtil.getDisplayHeight(this),cropImageView,Uri.fromFile(new File(path.imgPath)));
//            Glide.with(this)
//                    .load(Uri.fromFile(new File(path.imgPath)))
//                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .into(cropImageView);
        }
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //导航条设置
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showProgress() {
        if (waitingDialog==null)
        waitingDialog= HM_SystemDialogUtils.getWaitDialog(this,"");
        if (!waitingDialog.isShowing())
            waitingDialog.show();
    }

    public void dismissProgress() {
        if (waitingDialog!=null && waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    public Uri createSaveUri() {
            return Uri.fromFile(new File(HM_StorageUtil.getAlbumDir(this), new HM_Md5FileNameGenerator().generate(System.currentTimeMillis()+"")+".jpeg"));
    }



    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.buttonRotateLeft) {
            cropImageView.rotateImage(HM_ExtarCropImageView.RotateDegrees.ROTATE_M90D);

        } else if (i == R.id.buttonRotateRight) {
            cropImageView.rotateImage(HM_ExtarCropImageView.RotateDegrees.ROTATE_90D);

        } else if (i == R.id.buttonDone) {
            showProgress();
            cropImageView.startCrop(createSaveUri(), mCropCallback, mSaveCallback);
        }
    }

    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            dismissProgress();
        }

        @Override
        public void onError() {
            dismissProgress();
        }
    };
    private final CropCallback mCropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {
        }

        @Override
        public void onError() {

        }
    };

    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            dismissProgress();
            try {
                Intent intent =new Intent();
                intent.putExtra("cropImage",new HM_ImgData(outputUri.getPath()));
                setResult(RESULT_OK,intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError() {
            dismissProgress();
        }
    };
}
