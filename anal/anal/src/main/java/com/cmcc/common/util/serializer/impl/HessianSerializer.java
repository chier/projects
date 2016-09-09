package com.cmcc.common.util.serializer.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian 序列化对象
 * 
 * @author chenke
 * @since 2006-5-10
 */
public class HessianSerializer extends AbstractCauchoSerializer {

    public void serialize(Object obj, OutputStream out) throws IOException {
        HessianOutput hout = new HessianOutput(out);
        hout.writeObject(obj);
    }

    public Object deserialize(InputStream in) throws IOException {
        HessianInput hin = new HessianInput(in);
        return hin.readObject(null);
    }

}
