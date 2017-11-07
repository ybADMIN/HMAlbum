package com.yb.hmalbumlib.extar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.List;


public class HM_AlbumSettingBase implements Parcelable {
	/**
	 * 图片选择数量
	 */
	private int choosesize;
	/**
	 * 文件数量
	 */
	private List<HM_AlbumFileTraversal> files;
	/**
	 * 滑动时是否加载图片
	 */
	private  boolean pauseOnScroll;
	/**
	 * 快速滑动是否加载图片
	 */
	private boolean pauseOnFling=true;
	/**
	 * 相册初始化标示
	 */
	public static final String MODE_INITALBUM="initalbum";
	public static final String TAG_CHOOSESIZE="chooseSize";
	public static final String DATA_CHOOSEFILE="choosefile";
	private  static String ALBUM_PATH;//拍照后保存的相册目录名字
	 //相册预览
	/**
	 * 相册预览标示
	 */
	public static final String MODE_PREVIEW="preview";
	private HM_AlbumFileTraversal f ;
	private int position;
	
	
	//相册中的编辑
	/**
	 * 相册中的编辑模式
	 */
	public static final String MODE_EDITPHOTOFORALBUM="editphotoforalbum";//表示图片预览界面（选择后）
	/**
	 * 选择的照片
	 */
	private ArrayList<HM_ImgData> chooseImage;
	/**
	 * 是否是外部调用
	 */
	private boolean isExtarChoose;
	/**
	 * 当外部调用时候传入的预览id列表
	 */
	private ArrayList<Integer> choosePosition ;
	
	
	//外部调用相册的编辑模式
	/**
	 * 外部调用编辑模式
	 */
	public static final String MODE_EDITPHOTOEXTAR="mode_editphotoextar";
	//操作完成后返回图片列表
	public static final String DATA_IMAGES="imgs";
	/**
	 * 被移除照片的ID
	 */
	public static final String DATA_REMOVEPOSITION="removePosition";
	/**
	 * 当照片旋转时
	 */
	public static final String DATA_IMGROTATE="imgRotate";
	
	

	/**
	 * 当外部调用时候传入的预览id列表
	 */
	public ArrayList<Integer> getChoosePosition() {
		return choosePosition;
	}
	
	/**
	 * 当外部调用时候传入的预览id列表
	 */
	public void setChoosePosition(ArrayList<Integer> choosePosition) {
		this.choosePosition = choosePosition;
	}
	/**
	 * 是否是外部调用
	 */
	public boolean isExtarChoose() {
		return isExtarChoose;
	}
	/**
	 * 是否是外部调用
	 */
	public void setExtarChoose(boolean isExtarChoose) {
		this.isExtarChoose = isExtarChoose;
	}
	/**
	 * 选择的照片
	 */
	public ArrayList<HM_ImgData> getChooseImage() {
		return chooseImage;
	}
	/**
	 * 选择的照片
	 */
	public void setChooseImage(ArrayList<HM_ImgData> chooseImage) {
		this.chooseImage = chooseImage;
	}
	/**
	 * 得到预览相册数据
	 * @return
	 */
	public HM_AlbumFileTraversal getF() {
		return f;
	}
	/**
	 * 设置相册数据用于预览
	 * @param f
	 */
	public void setF(HM_AlbumFileTraversal f) {
		this.f = f;
	}
	/**
	 * 预览相册位置
	 * @return
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * 预览相册位置
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * 多选长度
	 * @return
	 */
	public int getChoosesize() {
		return choosesize;
	}
	/**
	 * 多选长度
	 * @return
	 */
	public void setChoosesize(int tag_choosesize) {
		this.choosesize = tag_choosesize;
	}
	/**
	 * 相册列表对象
	 * @return
	 */
	public List<HM_AlbumFileTraversal> getFiles() {
		return files;
	}
	/**
	 * 相册列表对象
	 * 默认如果不设置则调用系统封装方式请查看：{@link com.yb.hmalbumlib.HM_AlbumHandler}}
	 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
	 * @param files
	 */
	public void setFiles(List<HM_AlbumFileTraversal> files) {
		this.files = files;
	}
	/**
	 * 是否滑动时候停止加载图片
	 * @return
	 */
	public boolean isPauseOnScroll() {
		return pauseOnScroll;
	}
	/**
	 * 是否滑动时候停止加载图片
	 * @return
	 */
	public void setPauseOnScroll(boolean pauseOnScroll) {
		this.pauseOnScroll = pauseOnScroll;
	}
	/**
	 * 是否快速滑动时候停止加载图片
	 * @return
	 */
	public boolean isPauseOnFling() {
		return pauseOnFling;
	}
	/**
	 * 是否快速滑动时候停止加载图片
	 * @return
	 */
	public void setPauseOnFling(boolean pauseOnFling) {
		this.pauseOnFling = pauseOnFling;
	}
	@Override
	public String toString() {
		return "HM_AlbumSettingBase [choosesize=" + choosesize + ", files="
				+ files + ", pauseOnScroll=" + pauseOnScroll
				+ ", pauseOnFling=" + pauseOnFling + ", f=" + f + ", position="
				+ position + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(choosesize);
		dest.writeParcelable(f, 0);
		dest.writeList(files);
		dest.writeByte((byte) (pauseOnFling ? 1 : 0));
		dest.writeByte((byte) (pauseOnScroll?1:0));
		dest.writeInt(position);
		dest.writeList(chooseImage);
		dest.writeByte((byte) (isExtarChoose?1:0));
		dest.writeList(choosePosition);
	}
public static final Creator<HM_AlbumSettingBase> CREATOR=new Creator<HM_AlbumSettingBase>() {
		
		@Override
		public HM_AlbumSettingBase[] newArray(int size) {
			return new HM_AlbumSettingBase[size];
		}
		@SuppressWarnings("unchecked")
		@Override
		public HM_AlbumSettingBase createFromParcel(Parcel source) {
			HM_AlbumSettingBase ft=new HM_AlbumSettingBase();
			ft.choosesize= source.readInt();
			ft.f=source.readParcelable(HM_AlbumFileTraversal.class.getClassLoader());
			ft.files=source.readArrayList(HM_AlbumFileTraversal.class.getClassLoader());
			ft.pauseOnFling=source.readByte()==1?true:false;
			ft.pauseOnScroll=source.readByte()==1?true:false;
			ft.position=source.readInt();
			ft.chooseImage=source.readArrayList(HM_ImgData.class.getClassLoader());
			ft.isExtarChoose=source.readByte()==1?true:false;
			ft.choosePosition = source.readArrayList(Integer.class.getClassLoader());
			return ft;
		}
	};


public static String getAlbumPath(Context context) {
	if (ALBUM_PATH==null) {
		ALBUM_PATH= HM_StorageUtil.getAlbumDir(context).getAbsolutePath();//拍照后保存的相册目录名字
	}
	return ALBUM_PATH;
}



}
