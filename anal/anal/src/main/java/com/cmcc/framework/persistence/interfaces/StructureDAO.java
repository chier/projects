package com.cmcc.framework.persistence.interfaces;

import java.util.List;

import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.framework.model.Structure;

/**
 * 结构的DAO接口
 * 
 * @filename: com.cmcc.framework.persistence.interfaces.StructureDAO
 * @copyright: Copyright (c)2015
 * @company: 北京睿思恒信科技有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2015-9-16 下午3:36:34
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px">
 *         <thead style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2015-9-16</td>
 *         <td>张占亮</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody>
 *         </table>
 */
public interface StructureDAO<T extends Structure> extends IBaseDAO<Structure> {

	List<Structure> findByPid(Integer pid);
	
	List<Structure> findByPid(Integer pid,String pcenter);

}
