package com.yb.hmalbumlib;

import com.yb.hmalbumlib.engine.HM_ImageEngine;

/**
 * Created by ericYang on 2017/8/8.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class HM_AlbumConifg {
    private static HM_AlbumConifg mInstance;

    public void setEngine(HM_ImageEngine engine) {
        mEngine = engine;
    }

    private HM_ImageEngine mEngine;

    private HM_AlbumConifg() {
    }

    public static HM_AlbumConifg getInstance() {
        if (mInstance==null){
            synchronized (HM_AlbumConifg.class){
                if (mInstance==null){
                    mInstance = new HM_AlbumConifg();
                }
            }
        }
        return mInstance;
    }

    public HM_ImageEngine getEngine() {
        if (mEngine==null) throw new RuntimeException("Not find HM_ImageEngine");
        return mEngine;
    }
}
