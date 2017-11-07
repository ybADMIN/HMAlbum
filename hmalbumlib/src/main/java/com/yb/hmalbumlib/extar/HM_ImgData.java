package com.yb.hmalbumlib.extar;

import android.os.Parcel;


/**
 * @Description:
 * @author  yb email:yb498869020@hotmail.com
 * @date 2015-4-3 下午6:13:12 
 */

public class HM_ImgData implements HM_PhotoEntity {
	public String imgPath;
	public Integer _ID=0;

	public HM_ImgData(String imgPath) {
		this.imgPath = imgPath;
	}

	public HM_ImgData() {
	}

	@Override
	public String toString() {
		return "HM_ImgData [imgPath=" + imgPath +  ", _ID=" + _ID + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imgPath);
		dest.writeInt(_ID);
	}
	
	public static final Creator<HM_ImgData> CREATOR=new Creator<HM_ImgData>() {
		
		@Override
		public HM_ImgData[] newArray(int size) {
			return new HM_ImgData[size];
		}
		@Override
		public HM_ImgData createFromParcel(Parcel source) {
			HM_ImgData ft=new HM_ImgData();
			ft.imgPath= source.readString();
			ft._ID=source.readInt();
			return ft;
		}
	};

	@Override
	public boolean equals(Object o) {
		if (o instanceof HM_ImgData){
			return ((HM_ImgData)o).getImage().equals(getImage());
		}
		return super.equals(o);
	}

	@Override
	public String getImage() {
		return imgPath ;
	}

	@Override
	public String thumbnail() {
		return "";
	}
}
