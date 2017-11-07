package com.yb.hmalbumlib.extar;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

//文件的类
@SuppressLint("ParcelCreator")
public class HM_AlbumFileTraversal implements Parcelable {
	public String filename;//所属图片的文件名称
	public List<HM_ImgData> filecontent=new ArrayList<HM_ImgData>();
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(filename);
		dest.writeList(filecontent);
	}
	
	public static final Creator<HM_AlbumFileTraversal> CREATOR=new Creator<HM_AlbumFileTraversal>() {
		
		@Override
		public HM_AlbumFileTraversal[] newArray(int size) {
			return new HM_AlbumFileTraversal[size];
		}
		@SuppressWarnings("unchecked")
		@Override
		public HM_AlbumFileTraversal createFromParcel(Parcel source) {
			HM_AlbumFileTraversal ft=new HM_AlbumFileTraversal();
			ft.filename= source.readString();
			ft.filecontent= source.readArrayList(HM_AlbumFileTraversal.class.getClassLoader());
			return ft;
		}
	};

	@Override
	public String toString() {
		return "HM_AlbumFileTraversal [filename=" + filename
				+ ", filecontent=" + filecontent + "]";
	}
	
	
}
