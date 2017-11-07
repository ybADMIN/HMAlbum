package com.yb.hmalbumlib.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.yb.hmalbumlib.HM_AlbumConifg;
import com.yb.hmalbumlib.HM_StartAlbumPhoto;
import com.yb.hmalbumlib.R;
import com.yb.hmalbumlib.extar.HM_DisplayUtil;
import com.yb.hmalbumlib.extar.HM_ImgData;
import com.yb.hmalbumlib.ui.HM_PhotoPagerActivity;

import java.io.File;
import java.util.ArrayList;

public class HM_PhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    ArrayList<HM_ImgData> photoEntities;
    long waiting=0;
    private ArrayList<HM_ImgData> chooseImage;
    private HM_StartAlbumPhoto.AlbumPhotoMode mode;

    private OnPhotoListener onPhotoListener;



    public HM_PhotoPagerAdapter(Context c, ArrayList<HM_ImgData> photoEntities, HM_StartAlbumPhoto.AlbumPhotoMode mode) {
        this.photoEntities = photoEntities;
        this.mContext = c;
        this.mode =mode;
    }

    public OnPhotoListener getOnPhotoListener() {
        return onPhotoListener;
    }

    public void setOnPhotoListener(OnPhotoListener onPhotoListener) {
        this.onPhotoListener = onPhotoListener;
    }

    @Override
    public int getCount() {
        return photoEntities.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        HM_ImgData photodata = photoEntities.get(position);
        PhotoView photoView = new PhotoView(container.getContext());
        if (onPhotoListener != null) {
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    onPhotoListener.photoClick(view);
                }
            });
        }
        int resize = HM_DisplayUtil.getDisplayWidth(container.getContext());
        if (HM_AlbumConifg.getInstance().getEngine().supportAnimatedGif()){
            HM_AlbumConifg.getInstance().getEngine()
                    .loadImage(mContext, resize,resize,photoView, Uri.fromFile(new File(photodata.getImage())));
        }else {
            HM_AlbumConifg.getInstance().getEngine()
                    .loadGifImage(mContext, resize,resize,photoView, Uri.fromFile(new File(photodata.getImage())));
        }
        if (mode== HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE)
         photoView.setTag(R.id.hm_hmalbum_position,position);

        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        if (mode== HM_StartAlbumPhoto.AlbumPhotoMode.EDITCHOOSEIMAGE){
            Integer position = (Integer) ((PhotoView) object).getTag(R.id.hm_hmalbum_position);
            if (position!=null && ((HM_PhotoPagerActivity)mContext).getCurrentPage()==position){
                return POSITION_NONE;
            }else {
                return POSITION_UNCHANGED;
            }
        }
        else
       return super.getItemPosition(object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setChooseImage(ArrayList<HM_ImgData> chooseImage) {
        this.chooseImage = chooseImage;
    }

    public interface OnPhotoListener {
        void photoClick(View v);
    }
}