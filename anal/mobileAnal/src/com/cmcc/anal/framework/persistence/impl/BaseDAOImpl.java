package com.cmcc.anal.framework.persistence.impl;

import java.io.Serializable;

import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.framework.persistence.interfaces.IBaseDAO;

public abstract class BaseDAOImpl<T> extends GenericDAOImpl<T> implements IBaseDAO<T> {

}
