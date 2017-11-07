package com.yb.hmalbumlib.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yb.hmalbumlib.HM_AlbumConifg;
import com.yb.hmalbumlib.R;
import com.yb.hmalbumlib.extar.HM_AlbumFileTraversal;
import com.yb.hmalbumlib.extar.HM_DisplayUtil;

import java.io.File;
import java.util.List;

/**
 * @TODO Created by yb(yb498869020@hotmail.com) on 2016/7/30.
 */

public class HM_PhotoDirAdapter extends RecyclerView.Adapter<HM_PhotoDirAdapter.PhotoDirHolder> {

    private final int mLayoutId;
    private final List<HM_AlbumFileTraversal> mData;
    private String mCurrentAlbum;
    private LayoutInflater mInft;
    private Context mContext;
    private OnDirItemClickListener mDirItemClickListener;

    public void setDirItemClickListener(OnDirItemClickListener dirItemClickListener) {
        mDirItemClickListener = dirItemClickListener;
    }

    public HM_PhotoDirAdapter(int layoutResId, List<HM_AlbumFileTraversal> data) {
        this.mLayoutId = layoutResId;
        this.mData = data;
        if (mData.size()>0)
        this.mCurrentAlbum =data.get(0).filename;
    }

    public String getmCurrentAlbum() {
        return mCurrentAlbum;
    }

    public void setmCurrentAlbum(String mCurrentAlbum) {
        this.mCurrentAlbum = mCurrentAlbum;
    }

    @Override
    public PhotoDirHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInft== null){
            mInft = LayoutInflater.from(parent.getContext());
            mContext = parent.getContext();
        }
        return new PhotoDirHolder(mInft.inflate(mLayoutId,parent,false));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(PhotoDirHolder holder, final int position) {
        if (mDirItemClickListener!=null){
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDirItemClickListener.dirClick(v, (Integer) v.getTag());
                }
            });
        }

        HM_AlbumFileTraversal albumFileTraversal = mData.get(position);
        HM_AlbumConifg.getInstance().getEngine()
                .loadThumbnail(
                        mContext,
                        HM_DisplayUtil.dip2px(mContext,50), ContextCompat.getDrawable(mContext, R.drawable.hm_bg_placeholder),
                        holder.mIvMenuitme,
                        Uri.fromFile(new File(albumFileTraversal.filecontent.get(0).imgPath))
                );
        holder.mTvmenuItemName.setText(String.format("%s(%s)",albumFileTraversal.filename,albumFileTraversal.filecontent.size()));
        if (mCurrentAlbum!=null && mCurrentAlbum.length()>0){
            if (albumFileTraversal.filename.equals(mCurrentAlbum)){
               holder.mCbMenuitem.setChecked(true);
            }else {
                holder.mCbMenuitem.setChecked(false);
            }
        }
        if (position==getItemCount()-1){
            holder.mLine.setVisibility(View.GONE);
        }else {
            holder.mLine.setVisibility(View.VISIBLE);
        }
    }

    static class  PhotoDirHolder extends RecyclerView.ViewHolder{

        private final ImageView mIvMenuitme;
        private final TextView mTvmenuItemName;
        private final CheckBox mCbMenuitem;
        private final View mLine;

        public PhotoDirHolder(View itemView) {
            super(itemView);
            mIvMenuitme = (ImageView) itemView.findViewById(R.id.iv_aibum_menuitme);
            mTvmenuItemName= (TextView) itemView.findViewById(R.id.tv_aibum_menuitme_name);
            mCbMenuitem= (CheckBox) itemView.findViewById(R.id.cb_aibum_menuitme);
            mLine= itemView.findViewById(R.id.v_line);

        }
    }
    public interface OnDirItemClickListener{
        public void dirClick(View view, int pos);
    }

}
