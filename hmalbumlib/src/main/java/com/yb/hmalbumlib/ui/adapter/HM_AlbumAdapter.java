package com.yb.hmalbumlib.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yb.hmalbumlib.HM_AlbumConifg;
import com.yb.hmalbumlib.HM_StartAlbumPhoto;
import com.yb.hmalbumlib.HM_StartCorp;
import com.yb.hmalbumlib.R;
import com.yb.hmalbumlib.extar.HM_DisplayUtil;
import com.yb.hmalbumlib.extar.HM_ExtarCropImageView;
import com.yb.hmalbumlib.extar.HM_ImgData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @TODO Created by yb(yb498869020@hotmail.com) on 2016/7/17.
 */
public class HM_AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTENT = 1;
    private static final int CAMERA = 0;
    private final int layoutResId;
    private List<HM_ImgData> mData;
    private int spaceCount;
    private int fixSize;
    private List<HM_ImgData> chooseImage = new ArrayList<>();
    private OnCheckedPhoto onCheckedPhoto;
    private boolean isCameraShow = true;
    private Context mContext;
    private int maxChoose = 1;
    private boolean isCrop;//是否裁剪
    private HM_ExtarCropImageView.CropMode cropMode;
    private int maxOutPutH;
    private int maxOutPutW;
    private Bitmap.CompressFormat comPressFromat;
    private int compressQuality;

    public HM_AlbumAdapter(int layoutResId, List<HM_ImgData> data, int spaceCount) {
        this.layoutResId = layoutResId;
        if (data == null)
            data = new ArrayList<>();
        this.mData = data;
        this.spaceCount = spaceCount;
    }

    public List<HM_ImgData> getChooseImage() {
        return chooseImage;
    }

    public HM_AlbumAdapter setChooseImage(List<HM_ImgData> chooseImage) {
        this.chooseImage = chooseImage;
        return this;
    }

    public HM_AlbumAdapter setOnCheckedPhoto(OnCheckedPhoto onCheckedPhoto) {
        this.onCheckedPhoto = onCheckedPhoto;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (isCameraShow && position == 0) {
            return CAMERA;//添加按钮
        } else {
            return CONTENT;//正常类容
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case CAMERA:
                holder = new ViewHolderCamera(LayoutInflater.from(mContext).inflate(R.layout.hm_album_itme_camera, parent, false));
                fixSize = (HM_DisplayUtil.getDisplayWidth(parent.getContext()) / spaceCount);
                holder.itemView.getLayoutParams().width = fixSize;
                holder.itemView.getLayoutParams().height = fixSize;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCheckedPhoto != null) {
                            onCheckedPhoto.openCamera();
                        }
                    }
                });//打开相机
                break;
            case CONTENT:
                holder = new ViewHolderImage(LayoutInflater.from(mContext).inflate(layoutResId, parent, false));
                fixSize = (HM_DisplayUtil.getDisplayWidth(parent.getContext()) / spaceCount);
                holder.itemView.getLayoutParams().width = fixSize;
                holder.itemView.getLayoutParams().height = fixSize;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CONTENT) {
            setContent((ViewHolderImage) holder, mData.get(getRelPosition(position)));
        } else if (holder.getItemViewType() == CAMERA) {
        }
    }


    private void setContent(final ViewHolderImage baseViewHolder, final HM_ImgData imgData) {
        HM_AlbumConifg.getInstance().
                getEngine()
                .loadThumbnail(
                        mContext,(int) (fixSize * 0.9),  ContextCompat.getDrawable(mContext, R.drawable.hm_bg_placeholder),
                        baseViewHolder.albumItmesIv, Uri.fromFile(new File(imgData.imgPath)));
//        Glide.with((AppCompatActivity) mContext)
//                .fromFile()
//                .asBitmap()
//                .load(new File(imgData.imgPath))
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .dontAnimate()
//                .centerCrop()
//                .override((int) (fixSize * 0.9), (int) (fixSize * 0.9))
//                .into(baseViewHolder.albumItmesIv);


        if (isCrop) {
            baseViewHolder.albumItmesCb.setVisibility(View.GONE);
            //裁剪照片
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new HM_StartCorp.Bulide(mContext,HM_AlbumConifg.getInstance().getEngine())
                            .setChooseImage(imgData)
                            .setCorpMode(cropMode)
                            .setCompressQuality(compressQuality)
                            .setCompressFormat(comPressFromat)
                            .setMaxOutPutH(maxOutPutH)
                            .setMaxOutPutW(maxOutPutW)
                            .setRequestCode(HM_StartCorp.REQUEST_CODE_CROP)
                            .bulide();
                }
            });
        } else {
            multiPhoto(baseViewHolder, imgData);
        }
    }

    private void multiPhoto(ViewHolderImage baseViewHolder, HM_ImgData imgData) {
        //查看大图
        baseViewHolder.itemView.setTag(baseViewHolder.getLayoutPosition());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HM_StartAlbumPhoto.Bulide(mContext,HM_AlbumConifg.getInstance().getEngine())
                        .setList(mData)
                        .setChooseImages(chooseImage)
                        .setMode(HM_StartAlbumPhoto.AlbumPhotoMode.ALBUMSEE)
                        .setCurrentSelect(getRelPosition((Integer) v.getTag()))
                        .setRequestCode(HM_StartAlbumPhoto.REQUESTCODE)
                        .setMaxChoose(maxChoose)
                        .bulide();
            }
        });
        //复选框
        baseViewHolder.albumItmesCb.setChecked(chooseImage.contains(imgData));
        //复选框单机区域
        baseViewHolder.albumItmesFl.setTag(imgData);
        baseViewHolder.albumItmesFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = ((CheckBox) v.findViewById(R.id.album_itmes_cb));
                boolean checked = !cb.isChecked();
                if (checked) {
                    if (chooseImage.size() >= maxChoose) {//最多选择判断
                        Toast.makeText(mContext, String.format(Locale.getDefault(), mContext.getString(R.string.hm_max_chooseimage),maxChoose+""), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        chooseImage.add(((HM_ImgData) v.getTag()));
                    }
                } else {
                    chooseImage.remove(((HM_ImgData) v.getTag()));
                }
                cb.setChecked(checked);
                if (onCheckedPhoto != null) {
                    onCheckedPhoto.checkSize(chooseImage.size());
                }
            }
        });
    }

    public void setCameraShow(boolean showCamear) {
        if ((isCameraShow && showCamear) || (!isCameraShow && !showCamear)) {
            return;
        }
        isCameraShow = showCamear;
        if (isCameraShow) {
            notifyItemInserted(0);
        } else {
            notifyItemRemoved(0);
        }
    }

    public boolean isCameraShow() {
        return isCameraShow;
    }

    @Override
    public int getItemCount() {
        if (isCameraShow) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    private int getRelPosition(int position) {
        return isCameraShow ? position - 1 : position;
    }

    public void setNewData(List<HM_ImgData> newData) {
        this.mData = newData;
        this.notifyDataSetChanged();
    }

    public void setMaxChoose(int maxChoose) {
        this.maxChoose = maxChoose;
    }

    public void setCrop(boolean crop) {
        this.isCrop = crop;
    }

    public void setCropMode(HM_ExtarCropImageView.CropMode cropMode) {
        this.cropMode = cropMode;
    }

    public void setMaxOutPutH(int maxOutPutH) {
        this.maxOutPutH = maxOutPutH;
    }

    public void setMaxOutPutW(int maxOutPutW) {
        this.maxOutPutW = maxOutPutW;
    }

    public void setComPressFromat(Bitmap.CompressFormat comPressFromat) {
        this.comPressFromat = comPressFromat;
    }

    public void setCompressQuality(int compressQuality) {
        this.compressQuality = compressQuality;
    }

    public interface OnCheckedPhoto {
        void checkSize(int size);
        void openCamera();
    }

    private static class ViewHolderImage extends RecyclerView.ViewHolder {
        ImageView albumItmesIv;
        CheckBox albumItmesCb;
        FrameLayout albumItmesFl;

        ViewHolderImage(View view) {
            super(view);
            albumItmesIv= (ImageView) view.findViewById(R.id.album_itmes_iv);
            albumItmesCb= (CheckBox) view.findViewById(R.id.album_itmes_cb);
            albumItmesFl= (FrameLayout) view.findViewById(R.id.album_itmes_fl);
        }
    }

    private static class ViewHolderCamera extends RecyclerView.ViewHolder {
        ImageView itmeLayoutCameraIv;

        ViewHolderCamera(View view) {
            super(view);
            itmeLayoutCameraIv= (ImageView) view.findViewById(R.id.itme_layout_camera_iv);
        }
    }
}
