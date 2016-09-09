package com.cmcc.common.util.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * hessian 和 burlap 对象序列化的接口.
 * 提供这个接口的目的是避免以后需要在序列化的方式上在 hessian 和 burlap 之间切换.
 * 
 * @author chenke
 * @since 2006-5-10
 */
public interface CauchoSerializer {
    
    public static final String SER_ENCODING = "UTF-8";
    
    /**
     * ������ obj ���л���� out
     */
    public void serialize(Object obj, OutputStream out) throws IOException;
    
    /**
     * ������ obj ���л�Ϊ�ļ� pathname
     */
    public void serialize(Object obj, String pathname) throws IOException;

    /**
     * �������л�Ϊ����
     */
    public Object deserialize(InputStream in) throws IOException;
    
    /**
     * ���ļ� pathname �����л�Ϊ����
     */
    public Object deserialize(String  pathname) throws IOException;
}
