package org.syanstue.myapplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

//鎿嶄綔SD鍗＄殑宸ュ叿绫�--"娌规潯"
public class SDUtil {

	// 鍒ゆ柇SD鍗＄殑鐘舵��:鏄惁鎸傝浇
	public static boolean isMounted() {
		// 鑾峰彇璁惧涓婁富瑕佺殑鎵╁紶瀛樺偍璁惧鐨勭姸鎬�
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}

		return false;
	}

	// 寰楀埌SD鍗＄殑鐗╃悊(缁濆)璺緞
	public static String getSdPath() {
		if (isMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}
	
	//鑾峰彇鍐呴儴瀛樺偍鐨勮矾寰�
	public static String getInternalPath(Context context,String type){
		
		File file = context.getExternalFilesDir(type);
		return file.getAbsolutePath();
	}

//	// 璁＄畻SD鍗＄殑鎬诲ぇ灏�
//	public static long getSdSize() {
//
//		if (isMounted()) {
//			// 鍖呭惈浜嗘枃浠剁郴缁熺┖闂翠俊鎭殑绫�
//			StatFs statfs = new StatFs(getSdPath());
//			// 寰楀埌sd鍗″潡鐨勬暟閲�
//			// int blockCount = statfs.getBlockCount();
//			long blockCountLong = statfs.getBlockCountLong();
//			// 寰楀埌姣忓潡鐨勫ぇ灏�
//			// int blockSize = statfs.getBlockSize();
//			long blockSizeLong = statfs.getBlockSizeLong();
//
//			return blockCountLong * blockSizeLong / 1024 / 1024;
//
//			// 杩欏彞璇濈瓑鍚屼簬涓婇潰鐨勯偅涓夊彞璇�:寰楀埌鎬诲ぇ灏�
//			// long totalBytes = statfs.getTotalBytes()/1024/1024;
//		}
//
//		return 0;
//
//	}

	// 璁＄畻SD鍗＄殑鍙敤绌洪棿
//	public static long getAvailableSize() {
//		if (isMounted()) {
//			StatFs fs = new StatFs(getSdPath());
//			// 寰楀埌鍙敤鐨勫潡鏁�
//			// int availableBlocks = fs.getAvailableBlocks();
//			long availableBlocksLong = fs.getAvailableBlocksLong();
//			long blockSizeLong = fs.getBlockSizeLong();
//			return availableBlocksLong * blockSizeLong / 1024 / 1024;
//
//			// 绛夊悓浜庝笂闈㈢殑涓夊彞璇�:寰楀埌鍙敤绌洪棿鐨勬�诲ぇ灏�
//			// return fs.getAvailableBytes()/1024/1024;
//		}
//		return 0;
//	}

	// 灏嗘煇涓枃浠跺瓨鍒皊d鍗′腑鏂规硶
	public static boolean saveFileInfoSD(byte[] data, String dir,
			String filename) {

		if (isMounted()) {
			// 鎷兼帴鍑烘潵鏂囦欢鐨勫瓨鏀捐矾寰�
			String path = getSdPath() + File.separator + dir;
			File file = new File(path);
			// 鍒ゆ柇鏂囦欢鏄惁瀛樺湪
			if (!file.exists()) {
				// 濡傛灉涓嶅瓨鍦�,鍒欏垱寤哄嚭鏉�
				file.mkdir();
			}

			BufferedOutputStream bos = null;
			try {
				// "澶х瀛愬灏忕瀛�--->鏁堢巼鏇撮珮涓�浜�"
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, filename)));
				// 灏嗘枃浠跺啓鍏ュ埌sd涓�
				bos.write(data, 0, data.length);
				bos.flush();

				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return false;

	}

	// 浠巗d鍗′腑鍙栧嚭鏌愪釜鏂囦欢
	public static byte[] getFileFromSD(String dir, String filename) {
		if (isMounted()) {
			//鎷兼帴鏂囦欢鐨勭粷瀵硅矾寰�
			String path = getSdPath() + File.separator + dir + File.separator
					+ filename;
			File file = new File(path);
			//鍒ゆ柇鏂囦欢鏄惁瀛樺湪
			if (file.exists()) {

				ByteArrayOutputStream baos = null;
				BufferedInputStream bis = null;
				try {
					//瀛楄妭鏁扮粍杈撳嚭娴�:杈撳嚭缂撳啿鍖轰腑鐨勫唴瀹�
					baos = new ByteArrayOutputStream();
					//杈撳叆娴�,灏嗘煇涓枃浠朵腑鐨勫唴瀹硅鍙栬繃鏉�
					bis = new BufferedInputStream(new FileInputStream(file));
					int len = 0;
					byte[] buffer = new byte[1024 * 4];
					while ((len = bis.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
						baos.flush();
					}

					//杩斿洖鏂囦欢鍐呭
					return baos.toByteArray();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//鍏抽棴娴�.
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

		return null;
	}
	
	public static byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


}
