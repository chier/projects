/* 
 * 文件名称：ContentInfo.java
 * 
 * 创建时间：2009-2-20 上午09:34:12
 *
 * 原始作者：曹巍
 */
package com.cmcc.anal.framework.model.content;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;

/**
 * 功能描述：发布内容数据表信息对应的Java类
 * 
 * @author 曹巍 创建时间：2009-2-20 上午09:34:12
 */

@Entity
@Table(name = "gweb_content_list")
public class ContentInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5254789842386698890L;

	/**
	 * ID
	 */

	private Integer identifier;

	/**
	 * 企业ID 暂时设为Integer 不做关联
	 * 
	 */
	private Integer eid;

	/**
	 * 内容标题
	 */

	private String title;

	/**
	 * 内容描述
	 */
	private String desc;

	/**
	 * 
	 * 内容状态,以数字标识
	 * 
	 * 0：未发布， 1：等待审批， 2：审批中， 3：审批失败， 4：已发布， 5：内容过期 -1: 逻辑删除
	 */
	private Integer state;

	/**
	 * 内容作者
	 */
	private String author;

	/**
	 * 排序号
	 */
	private Integer conorder;

	/**
	 * 是否置顶
	 */
	private Integer isTop;

	/**
	 * 有效标记（删除标记） 值为1为表示有效（未删除，可以浏览到的内容）， 值为0表示无效（浏览不到的内容）
	 */
	private Integer avail_flg;

	/**
	 * 内容来源
	 */
	private String contentSource;

	/**
	 * 内容来源链接地址
	 */
	private String contentSourceLink;

	/**
	 * 内容发布日期
	 */
	private Date releasedate;

	/**
	 * 内容类别 1 公司新闻 2 通知公告 3 电子调查
	 */
	private Integer contype;

	/**
	 * 生成html文件的路径
	 */
	private String htmlurl;

	/**
	 * 图片新闻路径
	 */
	private String picpath;

	/**
	 * 试点id
	 */
	private Integer sid;

	/**
	 * 试点下类别id
	 */
	private Integer tid;

	/**
	 * 内容附件集合
	 */
	private Set<ContentAttach> contentAttachs;

	// private Set<ContentVersion> contentVersions;

	/**
	 * 内容主体
	 */
	private ContentBody contentbody;

	/**
	 * 管理员id
	 */
	private Integer adminId;

	/** 最后修改日期 */
	private Date updatetime = new Date();

	/**
	 * 内容所在栏目 即gweb_content_type表信息
	 */
	private Integer ctId;

	/**
	 * 附件 id 集合 以 , 号分开
	 */
	private String attIds;

	/**
	 * 浏览次数
	 */
	private long viewNumber;

	/**
	 * 附件下载次数
	 */
	private long attachNumber;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contentid")
	public Integer getIdentifier() {

		return this.identifier;
	}

	/**
	 * 设置 identifier
	 * 
	 * @param identifier
	 *            要设置的 identifier。
	 */
	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;
	}

	public ContentInfo() {

		contentbody = new ContentBody();
	}

	public ContentInfo(Integer id) {
		identifier = id;
		contentbody = new ContentBody();
	}

	/**
	 * 获取内容标题
	 * 
	 * @return 标题
	 */
	@Column(name = "contitle", length = 256)
	public String getTitle() {

		return title;
	}

	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {

		return updatetime;
	}

	/**
	 * @param updatetime
	 *            the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {

		this.updatetime = updatetime;
	}

	/**
	 * 设置栏目标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {

		if (title != null)
			this.title = title.trim();
	}

	/**
	 * 获取内容描述
	 * 
	 * @return 内容描述
	 */
	@Column(name = "condesc", length = 256)
	public String getDesc() {

		return desc;
	}

	/**
	 * 设置内容描述
	 * 
	 * @param desc
	 *            内容描述
	 */

	public void setDesc(String desc) {

		this.desc = desc;
	}

	/**
	 * 获取内容状态
	 * 
	 * @return 内容状态
	 */
	@Column(name = "constate", length = 1)
	public Integer getState() {

		return state;
	}

	/**
	 * 设置内容状态
	 * 
	 * @param 内容状态
	 */
	public void setState(Integer state) {

		this.state = state;
	}

	/**
	 * 获取内容作者
	 * 
	 * @return 内容作者
	 */
	@Column(name = "conauthor", length = 50)
	public String getAuthor() {

		return author;
	}

	/**
	 * 设置内容作者
	 * 
	 * @param 内容作者
	 */
	public void setAuthor(String author) {

		if (author != null)
			this.author = author.trim();
	}

	/**
	 * 获取内容发布日期
	 * 
	 * @return 内容发布日期
	 */
	@Column(name = "conreleasedate")
	public Date getReleasedate() {

		return releasedate;
	}

	/**
	 * 设置内容发布日期
	 * 
	 * @param 内容发布日期
	 */

	public void setReleasedate(Date releasedate) {

		this.releasedate = releasedate;
	}

	/**
	 * 获取内容在栏目中排序号
	 * 
	 * @return 在栏目中排序号
	 */
	@Column(name = "conorder")
	public Integer getConorder() {

		return conorder;
	}

	/**
	 * 设置内容在栏目中排序号
	 * 
	 * @param 内容在栏目中排序号
	 */
	public void setConorder(Integer conorder) {

		this.conorder = conorder;
	}

	/**
	 * 获取有效标记
	 * 
	 * @return 有效标记
	 */
	@Column(name = "conavailflg", length = 1)
	public Integer getAvail_flg() {

		return avail_flg;
	}

	/**
	 * 设置有效标记
	 * 
	 * @param 有效标记
	 */
	public void setAvail_flg(Integer avail_flg) {

		this.avail_flg = avail_flg;
	}

	/**
	 * 附件
	 * 
	 * @return
	 */
	@OneToMany(targetEntity = ContentAttach.class, mappedBy = "content", fetch = FetchType.LAZY)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	public Set<ContentAttach> getContentAttachs() {

		return contentAttachs;
	}

	/**
	 * 设置系统附件
	 * 
	 * @param contentAttachs
	 */
	public void setContentAttachs(Set<ContentAttach> contentAttachs) {

		this.contentAttachs = contentAttachs;
	}

	@OneToOne(targetEntity = ContentBody.class, cascade = { CascadeType.ALL })
	@JoinColumn(name = "conbodyid")
	public ContentBody getContentbody() {
		return contentbody;
	}

	public void setContentbody(ContentBody contentbody) {
		this.contentbody = contentbody;
	}

	/**
	 * 获取内容主体字符串 将数据库中的二进制数据转换成字符串,返回
	 * 
	 * @return 内容主体字符串
	 */
	@Transient
	public String getContent() {
		String r = null;
		if (this.getContentbody() != null) {
			if (this.getContentbody().getBody() != null) {
				try {
					r = new String(this.getContentbody().getBody(), "gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// r = new String(this.getContentbody().getBody());
			} else {
				r = "";
			}
		} else {
			r = "";
		}
		return r;
	}

	public void setContent(String body) {

		if (body != null && !body.equals("")) {

			try {
				this.getContentbody().setBody(body.getBytes("gb2312"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 获取内容来源
	 * 
	 * @return 内容来源
	 */
	@Column(name = "consource", length = 128)
	public String getContentSource() {

		return contentSource;
	}

	public void setContentSource(String contentSource) {

		if (contentSource != null)
			this.contentSource = contentSource.trim();
	}

	/**
	 * 获取内容来源链接
	 * 
	 * @return 来源链接
	 */
	@Column(name = "consourcelink", length = 256)
	public String getContentSourceLink() {

		return contentSourceLink;
	}

	public void setContentSourceLink(String contentSourceLink) {

		if (contentSourceLink != null) {
			this.contentSourceLink = contentSourceLink.trim();
		}

	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */

	@Column(name = "istop", length = 1)
	public Integer getIsTop() {

		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param 是否置顶
	 */

	public void setIsTop(Integer isTop) {

		this.isTop = isTop;
	}

	// @OneToMany(targetEntity = ContentVersion.class, mappedBy = "contentinfo",
	// fetch = FetchType.LAZY)
	// @Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	// public Set<ContentVersion> getContentVersions() {
	//
	// return contentVersions;
	// }
	//
	// public void setContentVersions(Set<ContentVersion> contentVersions) {
	//
	// this.contentVersions = contentVersions;
	// }

	@Column(name = "eid")
	public Integer getEid() {

		return eid;
	}

	public void setEid(Integer eid) {

		this.eid = eid;
	}

	/**
	 * 获取 内容类别
	 * 
	 * @return 内容类别
	 */
	@Column(name = "contype", length = 1)
	public Integer getContype() {

		return contype;
	}

	/**
	 * 设置 内容类别
	 * 
	 * @param 内容类别
	 */
	public void setContype(Integer contype) {

		this.contype = contype;
	}

	/**
	 * 获取 html路径
	 * 
	 * @return html路径
	 */
	@Column(name = "htmlurl", length = 256)
	public String getHtmlurl() {

		return htmlurl;
	}

	/**
	 * 设置 html路径
	 * 
	 * @param html路径
	 */

	public void setHtmlurl(String htmlurl) {

		if (htmlurl != null)
			this.htmlurl = htmlurl.trim();
	}

	/**
	 * 获取图片新闻路径
	 * 
	 * @return 图片新闻路径
	 */
	@Column(name = "picpath", length = 256)
	public String getPicpath() {

		return picpath;
	}

	/**
	 * 设置图片新闻路径
	 * 
	 * @param 图片新闻路径
	 */
	public void setPicpath(String picpath) {

		this.picpath = picpath;
	}

	public String toString() {

		ToStringBuilder strBuilder = new ToStringBuilder(this);
		strBuilder.append(super.toString());
		if (this.getAuthor() != null) {
			strBuilder.append("author", this.getAuthor());
		}
		if (this.getAvail_flg() != null) {
			strBuilder.append("avail_flg", this.getAvail_flg());
		}
		if (this.getConorder() != null) {
			strBuilder.append("conolder", this.getConorder());
		}
		if (this.getTitle() != null) {
			strBuilder.append("title", this.getTitle());
		}
		if (this.getReleasedate() != null) {
			strBuilder.append("releasedate", this.getReleasedate().toString());
		}
		if (this.getContentSource() != null) {
			strBuilder.append("contentsource", this.getContentSource());
		}
		if (this.getContentSourceLink() != null) {
			strBuilder.append("contentsourcelink", this.getContentSourceLink());
		}
		if (this.getContype() != null) {
			strBuilder.append("contenttype", this.getContype());
		}
		if (this.getDesc() != null) {
			strBuilder.append("condesc", this.getDesc());
		}
		if (this.getEid() != null) {
			strBuilder.append("eid", this.getEid());
		}
		if (this.getHtmlurl() != null) {
			strBuilder.append("htmlurl", this.getHtmlurl());
		}
		if (this.getIsTop() != null) {
			strBuilder.append("istop", this.getIsTop());
		}
		if (this.getPicpath() != null) {
			strBuilder.append("picpath", this.getPicpath());
		}

		return strBuilder.toString();
	}

	// public boolean equals(Object o) {
	// if (this == o)
	// return true;
	// if (!(o instanceof Department))
	// return false;
	// if (super.equals(o)) {
	// if (this.getIdentifier() != null) {
	// return this.getIdentifier().equals(
	// ((Department) o).getIdentifier());
	// } else {
	// return (((Department) o).getIdentifier() == null);
	// }
	// } else {
	// return false;
	// }
	// }
	/**
	 * 管理员外键
	 * 
	 * @return
	 */
	@Column(name = "adminid", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getAdminId() {

		return adminId;
	}

	public void setAdminId(Integer adminId) {

		this.adminId = adminId;
	}

	@Column(name = "ctId", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getCtId() {
		return ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}

	public String getAttIds() {
		return attIds;
	}

	public void setAttIds(String attIds) {
		this.attIds = attIds;
	}

	@Column(name = "attachNumber")
	public long getAttachNumber() {
		return attachNumber;
	}

	public void setAttachNumber(long attachNumber) {
		this.attachNumber = attachNumber;
	}

	@Column(name = "viewNumber")
	public long getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(long viewNumber) {
		this.viewNumber = viewNumber;
	}
	@Column(name = "sid")
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
	@Column(name = "tid")
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}
