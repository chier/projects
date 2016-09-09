package com.cmcc.common.util.serializer.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
import com.cmcc.common.util.serializer.CauchoSerializer;

/**
 * Burlap 序列化对象 Note: 如果涉及到将对象序列化为 String 和将 String 反序列化为对象 需使用
 * {@link #serialize2String(Object)} 和 {@link #deserializeFromString(String)}
 * 方法来避免字符集问题
 * 
 * @author chenke
 * @since 2006-5-10
 */
public class BurlapSerializer extends AbstractCauchoSerializer {

	public void serialize(Object obj, OutputStream out) throws IOException {
		BurlapOutput bout = new BurlapOutput(out);
		bout.writeObject(obj);
	}

	public Object deserialize(InputStream in) throws IOException {
		BurlapInput bin = new BurlapInput(in);
		return bin.readObject(null);
	}

	public Object deserialize(byte[] o) throws IOException {
		String str = IOUtils.toString(o);
		return deserializeFromString(str);
	}

	public String serialize2String(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		serialize(obj, out);
		return new String(out.toByteArray(), CauchoSerializer.SER_ENCODING);
	}

	public Object deserializeFromString(String content) throws IOException {
		InputStream in = IOUtils.toInputStream(content,
				CauchoSerializer.SER_ENCODING);
		return deserialize(in);
	}

}
