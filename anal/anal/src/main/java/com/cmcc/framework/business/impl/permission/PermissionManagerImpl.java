package com.cmcc.framework.business.impl.permission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.business.interfaces.custom.ICustomManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.business.interfaces.queries.IQueriesManager;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.content.ContentInfo;

/**
 * 权限管理 manager 类
 * 
 * @author Administrator
 * 
 */
public class PermissionManagerImpl extends GenericDAOImpl implements
		IPermissionManager {

	private Logger logger = Logger.getLogger(PermissionManagerImpl.class);

	private ICustomManager customManager;

	private IQueriesManager queriesManager;

	private IContentInfoManager contentInfoManager;

	public void setContentInfoManager(IContentInfoManager contentInfoManager) {
		this.contentInfoManager = contentInfoManager;
	}

	public ICustomManager getCustomManager() {
		return customManager;
	}

	public void setCustomManager(ICustomManager customManager) {
		this.customManager = customManager;
	}

	public IQueriesManager getQueriesManager() {
		return queriesManager;
	}

	public void setQueriesManager(IQueriesManager queriesManager) {
		this.queriesManager = queriesManager;
	}

	public List<Integer> findPermissionByRid(int rid) {

		String q = "SELECT pid  FROM  rolepermission  where rid = ? ";
		List<Integer> countlist = getHibernate_Anal().createSQLQuery(q, rid);
		return countlist;
	}

	public int saveAndUpdatePermission(int rid, String pcenter, int mid,
			int type) {
		// 判断数据库是否存在此类型
		int pid = isRolePermission(rid, type);
		if (pid == 0) {
			pid = savePermission(pcenter, mid, type);
			saveRolePermission(rid, type, pid);
		} else {
			updatePermission(pcenter, pid);
		}
		return pid;
	}

	/**
	 * 判断此角色的某类型是否存在
	 * 
	 * @param rid
	 * @param type
	 * @return
	 */
	private int isRolePermission(int rid, int type) {
		String q = "SELECT pid  FROM  rolepermission  where rid = ? and ptype = ?";
		List<Integer> cl = getHibernate_Anal().createSQLQuery(q, rid, type);
		if (cl != null && cl.size() > 0) {
			return cl.get(0);
		}
		return 0;
	}

	/**
	 * 保存此类型的角色信息
	 * 
	 * @param rid
	 * @param type
	 * @return
	 */
	public int saveRolePermission(int rid, int ptype, int pid) {
		String q = "insert into rolepermission(rid,ptype,pid,dmltime,dmlflog) values(?,?,?,?,?)";
		int c = getHibernate_Anal()
				.sqlUpdate(q, rid, ptype, pid, new Date(), 1);
		return c;
	}

	private int savePermission(String pcenter, int mid, int type) {
		long pid = 0;
		List l = new ArrayList();
		l.add(type);
		l.add("权限名称");
		l.add("权限备注");
		l.add(new Date());
		l.add(new Date());
		l.add(1);
		l.add("权限的技术说明");
		l.add(pcenter != null ? pcenter : "");
		l.add(0);
		l.add(mid);
		String q = "insert into permission(ptype,pname,pdesc,createtime,dmltime,dmlflog,developdesc,pcenter,pdefault,mid) values(?,?,?,?,?,?,?,?,?,?)";
		pid = getHibernate_Anal().sqlInsert(q, l.toArray());

		return (int) pid;
	}

	private int updatePermission(String pcenter, int pid) {
		pcenter = pcenter != null ? pcenter : "";
		String q = "UPDATE `permission` SET `pcenter`=? WHERE (`pid`=?)";
		int c = getHibernate_Anal().sqlUpdate(q, pcenter, pid);
		return c;
	}

	public List<ZtreeVO> findCustomByTree(int rid) {
		List<ZtreeVO> l = this.customManager.findByAll();
		String pcenter = this.findPcenterByRid(rid,
				PermissionMark.CustomTree_permission.getValue());
		if (!StringUtil.isBlank(pcenter)) {
			String[] sl = pcenter.split(",");
			int cid = 0;
			for (ZtreeVO vo : l) {
				for (int i = 0; i < sl.length; i++) {
					try {
						cid = Integer.valueOf(sl[i]);
					} catch (NumberFormatException e) {
						logger.error("数据存在错误!", e);
					}
					if (vo.getId() == cid) {
						vo.setChecked(true);
					}
				}
			}
		}
		return l;
	}

	public String findPcenterByRid(int rid, int ptype) {
		int pid = isRolePermission(rid, ptype);
		if (pid == 0) {
			logger.info("rid = " + rid + " | ptype=" + ptype
					+ "|msg = pid is 0");
		} else {
			String q = "SELECT pcenter  FROM  permission  where pid = ? ";
			List<String> l = getHibernate_Anal().createSQLQuery(q, pid);
			if (l == null || l.size() == 0) {
				logger.error("pid is null");
			} else {
				return l.get(0);
			}
		}
		return null;
	}

	public List<ZtreeVO> findQueriesByTree(int rid) {
		List<ZtreeVO> voList = new ArrayList<ZtreeVO>();
		StringBuilder whereBui = new StringBuilder();
		// 获取节点信息
		String pcenter = findPcenterByRid(rid,
				PermissionMark.QueriesTree_permission.getValue());
		String[] arrStr = null;
		if (StringUtil.isBlank(pcenter)) {
			arrStr = new String[0];
		} else {
			pcenter = pcenter.replaceAll("'", "");// 对数据库中 单引号去除
			arrStr = pcenter.split(",");
		}
		Map<String, Integer> idMap = new HashMap<String, Integer>();
		// group 类型
		List<QueriesVO> jlist = this.queriesManager.findByGroup();
		int type = 1;
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);

		// object 类型
		jlist = null;
		type = 2;
		jlist = this.queriesManager.findByObject(whereBui.toString(), "9", 2,
				idMap);
		whereBui.delete(0, whereBui.length());
		idMap = new HashMap<String, Integer>();
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);
		// table 类型
		jlist = null;
		type = 3;
		jlist = this.queriesManager.findByTable(whereBui.toString(), "999", 3,
				idMap);
		whereBui.delete(0, whereBui.length());
		idMap = new HashMap<String, Integer>();
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);

		return voList;
	}

	/**
	 * 转换 将 queries vo 成 ztree vo
	 * 
	 * @param zlist
	 *            集合
	 * @param qlist
	 *            集合
	 * @param arrStr
	 *            是否有权限集合
	 * @param whereBui
	 * 
	 * @param type
	 */
	private void transformQueriesVOToZtreeVo(List<ZtreeVO> zlist,
			List<QueriesVO> qlist, String[] arrStr, Map<String, Integer> map,
			StringBuilder whereBui, int type) {
		ZtreeVO zvo = null;
		QueriesVO qvo = null;
		for (int c = 0; c < qlist.size(); c++) {
			qvo = qlist.get(c);
			map.put(qvo.getTableCode(), qvo.getIdentifier());
			zvo = new ZtreeVO();
			boolean isCheck = false;
			for (int i = 0, l = arrStr.length; i < l; i++) {
				if (!StringUtil.isBlank(arrStr[i]))
					if (arrStr[i].equalsIgnoreCase(qvo.getTableCode())) {
						isCheck = true;
					}
			}
			zvo.setId(qvo.getIdentifier());
			zvo.setpId(qvo.getParentId());
			zvo.setName(qvo.getModelName());
			zvo.setType(type);
			zvo.setTypeValue(qvo.getTableCode());
			zvo.setChecked(isCheck);
			whereBui.append("'").append(qvo.getTableCode()).append("'");
			if (c != qlist.size() - 1) {
				whereBui.append(",");
			}
			zlist.add(zvo);
		}
	}

	private List<ZtreeVO> findJrxmlinfoBy() {

		List<ZtreeVO> l = new ArrayList<ZtreeVO>();
		// 创建查询
		String q = "SELECT `id`,`pid`,`node_name`  FROM  jrxmlinfo;";

		List<Object[]> countlist = this.getHibernate_Anal().createSQLQuery(q);
		ZtreeVO vo = null;
		Object[] obj = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				obj = countlist.get(i);
				vo = new ZtreeVO();
				vo.setId(Integer.valueOf(obj[0].toString()));
				vo.setpId(Integer.valueOf(obj[1].toString()));
				vo.setName(String.valueOf(obj[2]));
				l.add(vo);
			}
		}
		return l;
	}

	/**
	 * 根据条件 返回有限制的目录名
	 * 
	 * @param condition
	 * @return
	 */
	private List<String> findJrxmlinfoBy(String condition) {

		// 创建查询
		String q = "SELECT `node_name`  FROM  `jrxmlinfo` where id in ("
				+ condition + ")";
		List<String> countlist = this.getHibernate_Anal().createSQLQuery(q);
		return countlist;
	}

	public List<ZtreeVO> findLoapByTree(int rid) {
		List<ZtreeVO> voList = new ArrayList<ZtreeVO>();
		StringBuilder whereBui = new StringBuilder();
		// 获取节点信息
		String pcenter = findPcenterByRid(rid,
				PermissionMark.QueriesTree_permission.getValue());
		String[] arrStr = null;
		if (StringUtil.isBlank(pcenter)) {
			arrStr = new String[0];
		} else {
			pcenter = pcenter.replaceAll("'", "");// 对数据库中 单引号去除
			arrStr = pcenter.split(",");
		}
		Map<String, Integer> idMap = new HashMap<String, Integer>();
		// group 类型
		List<QueriesVO> jlist = this.queriesManager.findByGroup();
		int type = 1;
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);

		// object 类型
		jlist = null;
		type = 2;
		jlist = this.queriesManager.findByObject(whereBui.toString(), "9", 2,
				idMap);
		whereBui.delete(0, whereBui.length());
		idMap = new HashMap<String, Integer>();
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);
		// table 类型
		jlist = null;
		type = 3;
		jlist = this.queriesManager.findByTable(whereBui.toString(), "999", 3,
				idMap);
		whereBui.delete(0, whereBui.length());
		idMap = new HashMap<String, Integer>();
		transformQueriesVOToZtreeVo(voList, jlist, arrStr, idMap, whereBui,
				type);

		return voList;
	}

	public List<ZtreeVO> findRowDataByTree(int rid) {
		List<ZtreeVO> l = this.findJrxmlinfoBy();
		String pcenter = this.findPcenterByRid(rid,
				PermissionMark.RawDataTree_permission.getValue());
		if (StringUtil.isBlank(pcenter)) {
			return l;
		}
		String[] arrStr = pcenter.split(",");
		if (arrStr == null || arrStr.length == 0) {
			return l;
		}
		for (ZtreeVO vo : l) {
			boolean isChecked = false;
			for (int i = 0, s = arrStr.length; i < s; i++) {
				if (!StringUtil.isBlank(arrStr[i])) {
					if (Integer.valueOf(arrStr[i]).intValue() == vo.getId()) {
						isChecked = true;
						break;
					}
				}
			}
			vo.setChecked(isChecked);
		}
		return l;
	}

	public List<String> findRawDataPerByRid(int rid) {
		String pcenter = this.findPcenterByRid(rid,
				PermissionMark.RawDataTree_permission.getValue());
		List<String> l = findJrxmlinfoBy(pcenter);
		return l;
	}

	public List<ZtreeVO> findCenterByTree(int rid) {
		List<ZtreeVO> zl = new ArrayList<ZtreeVO>();
		String pcenter = this.findPcenterByRid(rid,
				PermissionMark.CenterTree_permission.getValue());
		// 获得需要分配权限的信息类型
		List<ContentType> typeList = this.contentInfoManager
				.findContnetType(PermissionMark.CenterTree_permission);
		ContentType tt = null;
		ZtreeVO vo = null;
		// 添加到信息库 并查询是否要显示的内容
		for (int i = 0; i < typeList.size(); i++) {
			tt = typeList.get(i);
			vo = new ZtreeVO();
			vo.setId(tt.getCtId());
			vo.setName(tt.getCtName());
			vo.setOpen(true);
			vo.setpId(0);
			zl.add(vo);

			List<ContentInfo> infoList = this.contentInfoManager
					.findAllInfoByctId(tt.getCtId());
			for (int c = 0; c < infoList.size(); c++) {
				ContentInfo info = infoList.get(c);
				vo = new ZtreeVO();
				vo.setId(info.getIdentifier());
				vo.setName(info.getTitle());
				vo.setOpen(false);
				vo.setpId(tt.getCtId());
				zl.add(vo);
			}
		}
		// 判断用户是否勾选
		if (StringUtil.isBlank(pcenter)) {
			return zl;
		}
		String[] arrStr = pcenter.split(",");
		if (arrStr == null || arrStr.length == 0) {
			return zl;
		}
		for (ZtreeVO zvo : zl) {
			boolean isChecked = false;
			for (int i = 0, s = arrStr.length; i < s; i++) {
				if (!StringUtil.isBlank(arrStr[i])) {
					if (Integer.valueOf(arrStr[i]).intValue() == zvo.getId()) {
						isChecked = true;
						break;
					}
				}
			}
			zvo.setChecked(isChecked);
		}
		return zl;
	}

	public List<ZtreeVO> findDataDownByTree(int rid) {
		// 获取所有的试点信息
		String hql = "SELECT `id`,`pid`,`name` FROM `date_type` WHERE `pid` =0;";
		List<Object[]> list = this.getHibernate_Anal().createSQLQuery(hql);
		// 已经试点拥有的权限
		String pcenter = this.findPcenterByRid(rid,
				PermissionMark.DataDownTree_permission.getValue());
		String[] arrStr = null;
		// DataDownTree_permission
		if (!StringUtil.isBlank(pcenter)) {
			arrStr = pcenter.split(",");
		}
		List<ZtreeVO> zl = new ArrayList<ZtreeVO>();
		
		if(list == null || list.size() == 0){
			return zl;
		}
		ZtreeVO vo = null;
		for(int i=0;i<list.size();i++){
			vo = new ZtreeVO();
			Object[] objs = list.get(i);
			vo.setId(Integer.valueOf(objs [0].toString()));
			vo.setpId(Integer.valueOf(objs[1].toString()));
			vo.setName(objs[2].toString());
			if( arrStr !=null && arrStr.length > 0){
				for(int j=0;j<arrStr.length;j++){
					if(Integer.valueOf(arrStr[j]).intValue() == vo.getId()){
						vo.setChecked(true);
					}
				}
			}
			zl.add(vo);
		}
		return zl;
	}
}
