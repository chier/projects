package com.cmcc.common.util.serializer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.cmcc.common.util.FileUtils;
import com.cmcc.common.util.serializer.CauchoSerializer;

/**
 * @author chenke
 * @since 2006-5-10
 */
public abstract class AbstractCauchoSerializer implements CauchoSerializer {

	/**
	 * 将对象 obj 序列化为文件 pathname
	 */
	public void serialize(Object obj, String pathname) throws IOException {
		File f = new File(pathname);
		FileUtils.mkdir(f.getParent());
		FileOutputStream fout = new FileOutputStream(f);
		try {
			serialize(obj, fout);
			IOUtils.closeQuietly(fout);
		} catch (IOException e) {
			FileUtils.delete(pathname);
			throw e;
		}
	}

	/**
	 * 将文件 pathname 反序列化为对象
	 */
	public Object deserialize(String pathname) throws IOException {
		FileInputStream fin = new FileInputStream(pathname);
		try {
			Object obj = deserialize(fin);
			return obj;
		} finally {
			IOUtils.closeQuietly(fin);
		}
	}

}
