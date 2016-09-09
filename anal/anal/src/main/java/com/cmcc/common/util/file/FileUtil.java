/*
 * 文件名： FileUtil.java
 * 
 * 创建日期： 2008-2-26
 *
 * Copyright(C) 2008, by Along.
 *
 * 原始作者:<a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cmcc.common.util.MathUtil;
import com.cmcc.common.util.date.DateUtil;

/**
 * 操作文件的工具类
 * 
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-2-26
 */
public class FileUtil {

	/**
	 * 得到一个目录下随机的一个文件:根据后缀.
	 * 
	 * @param aDir
	 *            目录
	 * @param aSuffixList
	 *            后缀
	 * @param aDefaultFileName
	 *            缺省文件名
	 * @return String
	 */
	public static String getRandomFile(String aDir, String aSuffixList,
			String aDefaultFileName) {
		String[] aAry = listDir(aDir, aSuffixList);
		int nLen = aAry.length;
		String aFileName = aDefaultFileName;
		if (nLen > 0) {
			int nIndex = MathUtil.getRandom(nLen);
			aFileName = aAry[nIndex > 0 ? nIndex - 1 : 0];
		}
		return aFileName;
	}

	/**
	 * 读取一个文件到字符串里.
	 * 
	 * @param sFileName
	 *            文件名
	 * @param sEncode
	 *            String
	 * @return 文件内容
	 */
	public static String readTextFile(String sFileName, String sEncode) {
		StringBuffer sbStr = new StringBuffer();
		BufferedReader ins = null;
		try {
			// Create object ins - the input file.
			// FileReader inStream = new FileReader(sFileName);
			// BufferedReader ins = new BufferedReader(inStream);

			File destFile = new File(sFileName);
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					destFile), sEncode);
			ins = new BufferedReader(read);
			String dataLine;
			while (null != (dataLine = ins.readLine())) // NOPMD
			{
				sbStr.append(dataLine);
				sbStr.append("\r\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ins) {
					ins.close();
				}
			} catch (IOException e) {
			}
		}

		return sbStr.toString();
	}

	/**
	 * 写文本内容到文件中.
	 * 
	 * @param sFileName
	 *            文件名
	 * @param sContent
	 *            内容
	 */
	public static void writeTextToFile(String sFileName, String sContent) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(sFileName);

			fileWriter.write(sContent, 0, sContent.length());
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fileWriter) {
					fileWriter.close();
				}
			} catch (IOException e) {
			}
		}

	}

	public static void copyfile(File fileForm, String fileTo, boolean overwrite) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fileForm);
			fos = new FileOutputStream(fileTo, !overwrite);
			byte[] buf = new byte[512];
			int size = fis.read(buf);
			while (size != -1) {
				fos.write(buf, 0, size);
				size = fis.read(buf);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				// donothing
			}
		}
	}

	/**
	 * 复制文件 <i>from </i> 到 <i>to </i> 文件.
	 * 
	 * @param fileFrom
	 *            源文件. if <i>from </i> is a directory , copy all of its
	 *            subdirectoris and file in it
	 * @param fileTo
	 *            目标文件.
	 * @param overwrite
	 *            是否覆盖文件. if true the destination is overwritten. otherwise,
	 *            source append to destination
	 * @exception IOException
	 *                会抛出IO异常,也可能是文件找不到异常.
	 */
	public static void copyfile(String fileFrom, String fileTo,
			boolean overwrite) throws IOException {
		File src = new File(fileFrom);
		File dest = new File(fileTo);

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(fileTo, !overwrite);

			byte[] buf = new byte[512];
			// 开始读最多512个字节，如果fis里不够512就
			int size = fis.read(buf);
			while (size != -1) {
				fos.write(buf, 0, size);
				size = fis.read(buf);
			}
		} catch (FileNotFoundException fnfe) {
			if (src.isDirectory()) {
				/* 原文件是目录 复制目录 */
				File[] files = src.listFiles();
				// Stack stack = new Stack();
				for (int i = 0; i < files.length; i++) {
					dest.mkdirs();
					// System.out.println( files[i ]. getName() );
					copyfile(src + File.separator + files[i].getName(), fileTo
							+ File.separator + files[i].getName(), overwrite);
				}
			} else {
				/* 原文件不存在或其它异常情况, 抛出异常 */
				throw fnfe;
			}
		} catch (IOException e) {
			/* 未知的I/O异常 */
			throw e;
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				// donothing
			} finally {
				try {
					if (null != fos) {
						fos.close();
					}
					if (null != fis) {

						fis.close();
					}
				} catch (IOException e) {
					// donothing
				}
			}
		}
	}

	/**
	 * 检查文件是否存在.
	 * 
	 * @param aFileName
	 *            文件名
	 * @return boolean
	 */
	public static boolean fileExist(String aFileName) {
		File aFile = new File(aFileName);
		if (aFile.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 删除一个文件.
	 * 
	 * @param fileName
	 *            文件名
	 * @return boolean
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// ErrInfo.println(fileName);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}

	/**
	 * 删除目录或文件，如果是目录则删除包括子目录的整个目录
	 * 
	 * @param f
	 *            要删除的文件或目录
	 */
	public static void deletedir(File f) {
		if (f.isFile()) {
			f.delete();
		} else if (f.isDirectory()) {
			File h[] = f.listFiles();
			for (int i = 0; i < h.length; i++) {
				deletedir(h[i]);
			}
			f.delete();
		}
	}

	/**
	 * 创建目录.
	 * 
	 * @param aDir
	 *            String
	 * @return boolean
	 */
	public static boolean creatDir(String aDir) {
		File aFile = new File(aDir);
		if (!aFile.exists()) {
			return aFile.mkdir();
		}
		return true;
	}

	/**
	 * 创建多级目录.
	 * 
	 * @param aDir
	 *            String
	 * @return boolean
	 */
	public static boolean creatDirs(String aDir) {
		File aFile = new File(aDir);
		if (!aFile.exists()) {
			return aFile.mkdirs();
		}
		return true;
	}

	/**
	 * 创建多级目录.
	 * 
	 * @param aParentDir
	 *            String
	 * @param aSubDir
	 *            以 / 开头
	 * @return boolean
	 */
	public static boolean creatDirs(String aParentDir, String aSubDir) {
		File aFile = new File(aParentDir);
		if (aFile.exists()) {
			File aSubFile = new File(aParentDir + aSubDir);
			if (aSubFile.exists()) {
				return true;
			} else {
				return aSubFile.mkdirs();
			}
		} else {
			return false;
		}
	}

	/**
	 * 创建目标目录下一个子目录.
	 * 
	 * @param aDir
	 *            目标目录
	 * @param aSubDir
	 *            子目录
	 * @return boolean
	 */
	public static boolean creatDir(String aDir, String aSubDir) {
		creatDir(aDir + "/" + aSubDir);
		return true;
	}

	/**
	 * 按照分号分割的后缀列出一个目录下的符合条件的文件.
	 * 
	 * @param aDir
	 *            String
	 * @param suffixList
	 *            String
	 * @return String[]
	 */
	public static String[] listDir(String aDir, String suffixList) {
		FileNameFilter aFilter = new FileNameFilter(suffixList);

		File aFile = new File(aDir);
		if (aFile.exists()) {
			return aFile.list(aFilter);
		} else {
			String[] aAry = {};
			return aAry;
		}
	}

	/**
	 * 获取文件的扩展名
	 */
	public static String getFileExtName(String filename) {
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			return filename.substring(pos + 1);
		}

		else {
			return "";
		}

	}

	public static String getFileName(String fullname) {
		int pos = fullname.lastIndexOf(".");
		if (pos > 0) {
			return fullname.substring(0, pos);
		} else {
			return fullname;
		}

	}

	public static String getRandomFileName(String filename) {
		String rfilename = filename + DateUtil.year().intValue()
				+ DateUtil.month().intValue() + DateUtil.day().intValue()
				+ DateUtil.hour().intValue() + DateUtil.minute().intValue()
				+ DateUtil.second() + MathUtil.getRandom(100);
		return rfilename;
	}
	/**
	 * 根据文件字节数组 判断文件类型 只适合文件类型
	 * 
	 * @param buf
	 * @return
	 */
	public static String getExt(byte[] buf) {
		String ext = ""; // 扩展名
		int b0 = buf[0] & 255, b1 = buf[1] & 255, b2 = buf[2] & 255, b3 = buf[3] & 255;
		// JPEG
		if (b0 == 255 && b1 == 216 && b2 == 255) {
			ext = "jpg";
		}
		// GIF ("GIF8")
		if (b0 == 71 && b1 == 73 && b2 == 70 && b3 == 56) {
			ext = "gif";
		}
		// PNG
		if (b0 == 137 && b1 == 80 && b2 == 78 && b3 == 71) {
			ext = "png";
		}
		// BMP ("BM")
		if ((b0 == 66 && b1 == 77)) {
			ext = "bmp";
		}
		return ext;
	}
}
