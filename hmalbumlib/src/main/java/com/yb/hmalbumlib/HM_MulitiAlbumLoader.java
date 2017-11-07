package com.yb.hmalbumlib;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.yb.hmalbumlib.extar.HM_AlbumException;
import com.yb.hmalbumlib.extar.HM_AlbumFileTraversal;
import com.yb.hmalbumlib.extar.HM_ImgData;

import java.util.ArrayList;
import java.util.List;

/**
 * @TODO Created by yb(yb498869020@hotmail.com) on 2016/7/17.
 */
public class HM_MulitiAlbumLoader extends AsyncTaskLoader<List<HM_AlbumFileTraversal>> {
    private static final String TAG = "HM_MulitiAlbumLoader";
    private HM_ImgData newPhoto;

    public HM_MulitiAlbumLoader(Context context) {
        super(context);
    }


    @Override
    public List<HM_AlbumFileTraversal> loadInBackground() {
        try {
            return new HM_AlbumHandler().getLocalImgFileList(getContext());
        } catch (HM_AlbumException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
