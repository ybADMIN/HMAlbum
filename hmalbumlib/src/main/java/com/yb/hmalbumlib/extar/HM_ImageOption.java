package com.yb.hmalbumlib.extar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description: TODO(--) 
 * @author wsx - heikepianzi@qq.com 
 * @date 2015-4-3 下午6:13:12 
 */

public class HM_ImageOption implements Parcelable {
	public int rotate;//旋转角度
	public HM_ImageOption(int roate) {
		this.rotate = roate;
	}
	public HM_ImageOption() {
	}
	
	@Override
	public String toString() {
		return "HM_ImageOption [rotate=" + rotate + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(rotate);
	}
	
	public static final Creator<HM_ImageOption> CREATOR=new Creator<HM_ImageOption>() {
		
		@Override
		public HM_ImageOption[] newArray(int size) {
			return new HM_ImageOption[size];
		}
		@Override
		public HM_ImageOption createFromParcel(Parcel source) {
			HM_ImageOption ft=new HM_ImageOption();
			ft.rotate=source.readInt();
			return ft;
		}
	};
}
