/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yb.hmalbumlib.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.hmalbumlib.HM_StartAlbumPhoto;
import com.yb.hmalbumlib.R;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.ui.adapter.HM_PhotoPagerAdapter;
import com.yb.hmalbumlib.ui.widget.HM_HackyViewPager;

import java.util.ArrayList;
import java.util.Locale;

public class HM_PhotoPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, HM_PhotoPagerAdapter.OnPhotoListener, View.OnClickListener {

    HM_HackyViewPager photoViewPager;
    TextView photoViewTvIndicator;
    CheckBox photoViewCb;
    RelativeLayout photoViewRlBottomContent;
    private TextView mActionRight;
    private TextView mActionCenter;
    private TextView mActionLeft;
    private View mActionLayout;

    private int currentSelect;
    private HM_PhotoPagerAdapter adapter;
    private boolean isClickHideTitle;
    private ArrayList<HM_ImgData> list;
    private ArrayList<HM_ImgData> chooseImage = new ArrayList<>();//已经选择的照片
    private int maxChoose;//最多选择照片
    private boolean resultOk;
    private HM_StartAlbumPhoto.AlbumPhotoMode mode;


    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hm_album_photo_page);
        photoViewPager = (HM_HackyViewPager) findViewById(R.id.photo_view_pager);
        photoViewTvIndicator = (TextView)findViewById(R.id.photo_view_tv_indicator);
        photoViewCb = (CheckBox)findViewById(R.id.photo_view_cb);
        photoViewRlBottomContent= (RelativeLayout) findViewById(R.id.photo_view_rl_bottom_content);
        mActionRight = (TextView) findViewById(R.id.actionbar_right);
        mActionCenter = (TextView) findViewById(R.id.actionbar_center);
        mActionLeft = (TextView) findViewById(R.id.actionbar_lift);
        mActionLayout = findViewById(R.id.toolbar_layout);
        mActionLeft.setOnClickListener(this);
        mActionRight.setOnClickListener(this);
        photoViewCb.setOnClickListener(this);
        photoViewRlBottomContent.setOnClickListener(this);
        setToolbar();
        currentSelect = getIntent().getIntExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_CURRENTSELECT, 0);
        isClickHideTitle = getIntent().getBooleanExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_CLICKCLOSE, false);
        maxChoose = getIntent().getIntExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_MAXCHOOSE, 1);
        mode = (HM_StartAlbumPhoto.AlbumPhotoMode) getIntent().getSerializableExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_MODE);
        if (mode == null) {
            throw new RuntimeException("请选择你要启动的模式 com.huanmedia.yourchum.common.view.album.HM_StartAlbumPhoto$AlbumPhotoMode");
        }
        if (mode == HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE) {//编辑模式下不显示底部工具条
            photoViewCb.setVisibility(View.GONE);
            ((RelativeLayout.LayoutParams) photoViewTvIndicator.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        if (getIntent().hasExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_DATA)) {
            list = getIntent().getParcelableArrayListExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_DATA);
            photoViewTvIndicator.setText(String.format(Locale.getDefault(), "%d/%d", currentSelect + 1, list.size()));
            adapter = new HM_PhotoPagerAdapter(this, list,mode);
            if (mode != HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE){
                chooseImages();
            }
            else {//编辑模式下当list不等于null的时候才能删除
                if (list!=null && list.size()>0){
                    mActionRight.setText("删除");
                    mActionRight.setTextColor(ContextCompat.getColor(this, R.color.hm_green_3DDEB4));
                }
                else{
                    mActionRight.setText("删除");
                   mActionRight.setClickable(false);
                }
            }
            photoViewPager.setAdapter(adapter);
            if (isClickHideTitle) {//开启单击关闭
                adapter.setOnPhotoListener(this);
            }
            photoViewPager.addOnPageChangeListener(this);
            photoViewPager.setCurrentItem(currentSelect);
            //如果选择的是第一张照片可能会无法选中CheckBox 手动设置
            if (currentSelect == 0) {
                photoViewTvIndicator.setText(String.format("%d/%d", currentSelect + 1, adapter.getCount()));
                photoViewCb.setChecked(chooseImage.contains(list.get(currentSelect)));
            }

        }
    }

    /**
     * 图片选择
     */
    private void chooseImages() {
        if (getIntent().hasExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_CHOOSEIMAGE))//已经选择的照片
        {
            chooseImage = getIntent().getParcelableArrayListExtra(HM_StartAlbumPhoto.EXTRA_PHOTOSEE_CHOOSEIMAGE);
            if (chooseImage == null) chooseImage = new ArrayList<>();
            if (chooseImage.size() > 0) {
                mActionRight.setText(String.format(Locale.getDefault(), "确定(%d)", chooseImage.size()));
                mActionRight.setTextColor( ContextCompat.getColor(this, R.color.hm_green_3DDEB4));
               mActionRight.setClickable(true);
                adapter.setChooseImage(chooseImage);
            } else {
                mActionRight.setClickable(false);
            }
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionRight.setVisibility(View.VISIBLE);
        mActionRight.setText(String.format(Locale.getDefault(), "确定(%d)", chooseImage.size()));
        mActionRight.setTextColor(ContextCompat.getColor(this, R.color.hm_text_hint));
        mActionLayout.setAlpha(0.8f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onPageSelected(int position) {
        photoViewTvIndicator.setText(String.format("%d/%d", position + 1, adapter.getCount()));
        if (mode != HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE)
            photoViewCb.setChecked(chooseImage.contains(list.get(position)));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void photoClick(View v) {
        if (photoViewRlBottomContent.getAlpha() == 0) {
            animTitleBar(true);
        } else {
            animTitleBar(false);
        }
    }

    private void animTitleBar(boolean isShow) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            ActionBar actionbar = getSupportActionBar();
            assert actionbar != null;
            actionbar.setShowHideAnimationEnabled(true);
            if (isShow) {
                actionbar.show();
            } else {
                actionbar.hide();
            }
        } else {
            mActionLayout.animate().alpha(isShow ? 0.8f : 0).setDuration(200);
            if (isShow) {
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        photoViewRlBottomContent.animate().alpha(isShow ? 1 : 0).setDuration(200);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        switch (mode) {
            case ALBUMSEE://浏览照片时候数据不为空默认必须返回数据
                intent.putExtra("chooseImages", chooseImage);
                if (resultOk) {
                    intent.putExtra("resultOk", true);//关闭相册页面并返回数据
                }
                setResult(RESULT_OK, intent);
                break;
            case IMAGEFORCAMERA://相机返回数据的时候必须要点击确定才会将拍摄照片返回
                if (resultOk) {
                    intent.putExtra("imageForCamera", true);//拍照后确认 将关闭相册并且只返回拍照数据和已经选中数据
                    intent.putExtra("resultOk", true);//关闭相册页面并返回数据
                    intent.putExtra("chooseImages", chooseImage);
                    setResult(RESULT_OK, intent);
                }
                break;
            case EDITCHOOSEIMAGE://编辑（删除）已选中照片 只有点击确定的时候才会修改数据
                intent.putExtra("chooseImages", list);
                setResult(RESULT_OK, intent);
                break;
        }

        super.onBackPressed();
    }
    @Override
    public void onClick(View v) {//点击Checkbox后
        int i = v.getId();
        if (i == R.id.photo_view_cb) {
            boolean isChecked = photoViewCb.isChecked();
            int position = photoViewPager.getCurrentItem();
            if (isChecked) {
                if (chooseImage.size() >= maxChoose) {
                    Toast.makeText(this, String.format(Locale.getDefault(), getString(R.string.hm_max_chooseimage), maxChoose + ""), Toast.LENGTH_SHORT).show();
                    ((CheckBox) v).setChecked(false);
                    return;
                } else {
                    chooseImage.add(list.get(position));
                }
            } else {
                chooseImage.remove(list.get(position));
            }
            //选中计数
            mActionRight.setText(String.format(Locale.getDefault(), "确定(%d)", chooseImage.size()));
            if (chooseImage.size() == 0) {
                mActionRight.setTextColor(ContextCompat.getColor(this, R.color.hm_text_hint));
                mActionRight.setClickable(false);
            } else {
                mActionRight.setTextColor(ContextCompat.getColor(this, R.color.hm_green_3DDEB4));
                mActionRight.setClickable(true);
            }

        } else if (i == R.id.photo_view_rl_bottom_content) {
        } else if (i == R.id.actionbar_lift) {
            onBackPressed();

        } else if (i == R.id.actionbar_right) {
            if (mode != HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE) {
                resultOk = true;
                onBackPressed();
            } else {

                if (list == null) return;

                list.remove(photoViewPager.getCurrentItem());
                if (list.size() == 0) {
                    onBackPressed();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

        }
    }

    public int getCurrentPage() {
        return photoViewPager.getCurrentItem();
    }
}
