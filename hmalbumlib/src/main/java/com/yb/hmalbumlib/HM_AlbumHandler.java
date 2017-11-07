package com.yb.hmalbumlib;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.Nullable;

import com.yb.hmalbumlib.extar.HM_AlbumException;
import com.yb.hmalbumlib.extar.HM_AlbumFileTraversal;
import com.yb.hmalbumlib.extar.HM_ImgData;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.os.Environment.MEDIA_MOUNTED;


public class HM_AlbumHandler {
	
	/**
	 * 获取全部图片地址
	 * @return
	 * @throws HM_AlbumException
	 */
	public ArrayList<HM_ImgData> listAlldir(Context context) throws HM_AlbumException {
		MediaScannerConnection.scanFile(context, new String[]{sdCardPath()}, null, null);//更新图库
    	Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	Uri uri = intent.getData();
    	ArrayList<HM_ImgData> list = new ArrayList<HM_ImgData>();
    	String[] proj ={MediaColumns.DATA, BaseColumns._ID};
    	Cursor cursor = context.getContentResolver().query(uri, proj,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
				+ MediaStore.Images.Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" }, MediaColumns.DATE_MODIFIED+" DESC");//managedQuery(uri, proj, null, null, null);
		try {
			if (cursor==null) {
                throw new HM_AlbumException("无法获取相册数据");
            }
			while(cursor.moveToNext()){
                String path =cursor.getString(0);
                Integer integer = cursor.getInt(1);
                HM_ImgData imgData = new HM_ImgData();
                imgData.imgPath = new File(path).getAbsolutePath();
                imgData._ID=integer;
                list.add(imgData);
            }
		} finally {
			if (cursor!=null)
			cursor.close();
		}

		return list;
    }
	/**
	 * 某张图片的信息
	 * @return
	 * @throws HM_AlbumException
	 */
	public HM_ImgData getNewPhoto(Context context, Uri uri) throws HM_AlbumException {
		if (uri==null){
			throw new HM_AlbumException("参数Uri不能为空");
		}
		String[] proj ={MediaColumns.DATA, BaseColumns._ID};
		Cursor cursor = context.getContentResolver().query(uri, proj, null, null,null);//managedQuery(uri, proj, null, null, null);
		if (cursor==null) {
			throw new HM_AlbumException("不能获取相册数据");
		}
		cursor.moveToFirst();
		HM_ImgData imgData = new HM_ImgData();
			String path =cursor.getString(0);
			Integer integer = cursor.getInt(1);
			imgData.imgPath = new File(path).getAbsolutePath();
			imgData._ID=integer;
		cursor.close();
		return imgData;
	}
	/**
	 * 得到相册列表
	 * @param context
	 * @return
	 * @throws HM_AlbumException
	 */
	public List<HM_AlbumFileTraversal> getLocalImgFileList(Context context) throws HM_AlbumException {
		List<HM_ImgData> allimglist=listAlldir(context);
		return 	getAlbumForImages(allimglist);
	}
//	/**
//	 * 过滤已选择的图片将图片的ischeck设置成true
//	 * @param context
//	 * @param Choosed
//	 * @return
//	 */
//	public List<HM_AlbumFileTraversal> getChoosedImgFileList(Context context, List<HM_ImgData> Choosed){
//		List<HM_ImgData> allimglist=listAlldir(context,Choosed);
//		return 	getAlbumForImages(allimglist);
//	}
	
//	/**
//	 * 过滤已选择的相片
//	 * @param context
//	 * @return
//	 */
//	public List<HM_ImgData> listAlldir(Context context, List<HM_ImgData> Choosed){
//		MediaScannerConnection.scanFile(context, new String[]{sdCardPath()}, null, null);//更新图库
//    	Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//    	Uri uri = intent.getData();
//    	ArrayList<HM_ImgData> list = new ArrayList<HM_ImgData>();
//    	String[] proj ={MediaColumns.DATA,BaseColumns._ID};
//    	Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
//    	HashMap<Integer, Boolean> filter_map=new HashMap<Integer, Boolean>();
//
//
//		for (HM_ImgData imgData : Choosed) {
//			filter_map.put(imgData._ID, imgData.isClick);
//		}
//
//    	while(cursor.moveToNext()){
//    		String path =cursor.getString(0);
//    		Integer integer = cursor.getInt(1);
//    		HM_ImgData imgData = new HM_ImgData();
//    		imgData.imgPath = new File(path).getAbsolutePath();
//    		imgData._ID=integer;
//    		imgData.position=cursor.getPosition();
//    		Boolean ischeck = filter_map.get(integer);
//    		if (ischeck!=null&&ischeck) {
//    			imgData.isClick=ischeck;
//			}
//    		list.add(imgData);
//    	}
//		return list;
//	}

	/*通过数据列表返回处理后的相册
	 */
	private List<HM_AlbumFileTraversal> getAlbumForImages(List<HM_ImgData> allimglist){
		LinkedList<HM_AlbumFileTraversal> data=new LinkedList<HM_AlbumFileTraversal>();
		String filename="";
		if (allimglist!=null) {
			Set<String> set = new TreeSet<String>();
			String[]str;
			for (int i = 0; i < allimglist.size(); i++) {
				set.add(getfileinfo(allimglist.get(i).imgPath));
			}
			str= set.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				filename=str[i];
				HM_AlbumFileTraversal ftl= new HM_AlbumFileTraversal();
				ftl.filename=filename;
				data.add(ftl);
			}
			
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < allimglist.size(); j++) {
					if (data.get(i).filename.equals(getfileinfo(allimglist.get(j).imgPath))) {
						data.get(i).filecontent.add(allimglist.get(j));
					}
				}
			}
		}
		if (allimglist.size()>0){
			HM_AlbumFileTraversal allimg = new HM_AlbumFileTraversal();
			allimg.filename="所有照片";
			allimg.filecontent=allimglist;
			data.addFirst(allimg);
		}
		return data;
		}
//    /**
//     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
//     * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
//     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
//     * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
//     *
//     * @param imagePath
//     *            图像的路径
//     * @param width
//     *            指定输出图像的宽度
//     * @param height
//     *            指定输出图像的高度
//     * @return 生成的缩略图
//     */
//    public static Bitmap getImageThumbnail(String imagePath, int width, int height)
//    {
//    Bitmap bitmap = BitmapUtil.getSmallBitmap(imagePath,width,height);
//    // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
//    bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
//        ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//    return bitmap;
//    }
	@Nullable
	private static String sdCardPath(){
			File appCacheDir = null;
			String externalStorageState;
			try {
				externalStorageState = Environment.getExternalStorageState();
			} catch (NullPointerException e) { // (sh)it happens (Issue #660)
				externalStorageState = "";
			}
			if (MEDIA_MOUNTED.equals(externalStorageState)) {
			return Environment.getExternalStorageDirectory().toString();
			}
		return null;
	}

	private static String getfileinfo(String data){
		String filename[]= data.split("/");
		if (filename!=null) {
			return filename[filename.length-2];
		}
		return null;
	}

	private static String getFileName(String data){
		String filename[]= data.split("/");
		if (filename!=null) {
			return filename[filename.length-1];
		}
		return null;
	}
}
