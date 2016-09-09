/*
 * 文件名： FilterFileName.java
 * 
 * 创建日期： 2008-2-26
 *
 * Copyright(C) 2008, by Along.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.cmcc.common.util.StringUtil;

/**
 * 按照规则过滤文件.
 *
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-2-26
 */
@SuppressWarnings("unchecked")
public class FileNameFilter implements FilenameFilter{

	private String suffixs = ""; //后缀列表

	private List suffixList;

    /**
     * 构造器.
     */
    public FileNameFilter()
    {
    }

    /**
     * 构造器,按照后缀构造.
     * 
     * @param aSuffixList 后缀列表,用分号连接
     */
    public FileNameFilter(String aSuffixList)
    {
        suffixs = aSuffixList;
        suffixList = new ArrayList();
        suffixList = StringUtil.splitStrToList("/;/", suffixs);
    }

    /**
     * 分析文件的后缀,如果负责列表,则返回true.
     * 
     * @param dir 目录
     * @param name 文件名
     * @return 匹配返回true
     */
    public boolean accept(File dir, String name)
    {
        int dotIndex = name.lastIndexOf(".");
        if ((dotIndex > -1) && (dotIndex < name.length()))
        {
            String aSuf = name.substring(dotIndex + 1);

            if (suffixList.contains(aSuf))
            {
                return true;
            }
        }
        else
        {
            return false;
        }

        return false;
    }
}
