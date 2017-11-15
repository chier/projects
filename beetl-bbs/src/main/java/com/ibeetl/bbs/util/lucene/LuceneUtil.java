package com.ibeetl.bbs.util.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.beetl.sql.core.engine.PageQuery;

import com.ibeetl.bbs.util.lucene.entity.IKAnalyzer5x;
import com.ibeetl.bbs.util.lucene.entity.IndexObject;

/**
 * 生成查询索引工具类
 * 
 * 
 */
public class LuceneUtil {
	private Directory directory = null;
	private Analyzer analyzer = null;
	private String indexDer = null;// 索引存放目录
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 索引存放目录

	public LuceneUtil() {
		super();
	}

	public String getIndexDer() {
		return indexDer;
	}

	public void setIndexDer(String indexDer) {
		this.indexDer = indexDer;
	}

	public Analyzer getAnalyzer() {
		if (analyzer == null) {
			// analyzer = new SmartChineseAnalyzer();//中文分词器
			// 创建IKAnalyzer中文分词对象
			analyzer = new IKAnalyzer5x(true);
		}
		return analyzer;
	}

	public Directory getDirectory() throws IOException {
		if (directory == null) {
			File indexrepository_file = new File(this.indexDer);
			Path path = indexrepository_file.toPath();
			directory = FSDirectory.open(path);
		}
		return directory;
	}

	/**
	 * 创建索引
	 * 
	 * @param bbsTopicList
	 * @param bbsPostList
	 */
	public void createDataIndexer(List<IndexObject> bbsContentList) {

		IndexWriter indexWriter = null;
		try {
			// 创建一个分析器对象
			// 创建一个IndexwriterConfig对象
			IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
			// 创建一个IndexWriter对象，对于索引库进行写操作
			indexWriter = new IndexWriter(getDirectory(), config);
			// 删除以前的索引
			// indexWriter.deleteAll();

			for (IndexObject t : bbsContentList) {
				// 创建一个Document对象
				Document document = new Document();

				Field contentField = null;
				Field contentStoreField = null;

				if (t.getIndexType().equals(1)) { // 主题贴

					if (StringUtils.isBlank(t.getTopicContent())) { // 如果为空结束该次循环
						continue;
					}

					// 向Document对象中添加域信息
					// 参数：1、域的名称；2、域的值；3、是否存储；
					contentField = new TextField("content", labelformat(t.getTopicContent()), Store.YES);
					// storedFiled默认存储
					contentStoreField = new StoredField("postContent", t.getPostContent());
				} else if (t.getIndexType().equals(2)) { // 回复贴
					if (StringUtils.isBlank(t.getPostContent())) {// 如果为空结束该次循环
						continue;
					}
					if (StringUtils.isBlank(t.getTopicContent())) {// 如果为空结束该次循环
						continue;
					}

					contentField = new TextField("content", labelformat(t.getPostContent()), Store.YES);
					contentStoreField = new StoredField("topicContent", t.getTopicContent());
				}

				Field topicIdField = new StoredField("topicId", t.getTopicId());
				Field indexTypeField = new StoredField("indexType", t.getIndexType());
				Field isUpField = new StoredField("isUp", t.getIsUp());
				Field isNiceField = new StoredField("isNice", t.getIsNice());
				Field userIdField = new StoredField("userId", t.getUserId());
				Field userNameField = new StoredField("userName", t.getUserName());
				Field createTimeField = new StoredField("createTime", t.getCreateTime().getTime());
				Field postCountField = new StoredField("postCount", t.getPostCount());
				Field pvField = new StoredField("pv", t.getPv());
				Field moduleIdField = new StoredField("moduleId", t.getModuleId());
				Field moduleNameField = new StoredField("moduleName", t.getModuleName());

				// 将域添加到document对象中
				document.add(topicIdField);
				document.add(indexTypeField);
				document.add(contentStoreField);
				document.add(contentField);
				document.add(isUpField);
				document.add(isNiceField);
				document.add(userIdField);
				document.add(userNameField);
				document.add(createTimeField);
				document.add(postCountField);
				document.add(pvField);
				document.add(moduleIdField);
				document.add(moduleNameField);

				// 将信息写入到索引库中
				indexWriter.addDocument(document);
			}
			if (bbsContentList.size() > 0) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				indexWriter.rollback();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				indexWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 查询索引
	 * 
	 * @param keyword
	 *            关键字
	 * @param pageSize
	 *            页面大小
	 * @param currentPage
	 *            当前页
	 */
	public PageQuery<IndexObject> searcherKeyword(String keyword, Integer pageSize, Integer currentPage) {
		if (pageSize == 0)
			pageSize = 10;
		IndexReader indexReader = null;
		PageQuery<IndexObject> pageQuery = null;
		List<IndexObject> searchResults = new ArrayList<>();
		try {
			// 打开索引库
			indexReader = DirectoryReader.open(getDirectory());
			// 创建一个IndexSearcher对象
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			// 建立查询解析器
			// 第一个参数是要查询的字段；
			// 第二个参数是分析器Analyzer
			QueryParser parser = new QueryParser("content", getAnalyzer());
			// 创建一个查询对象
			// 特殊字符转义
			keyword = QueryParser.escape(keyword);
			// 根据传进来的par查找
			Query query = parser.parse(keyword);
			// 执行查询
			// 返回的最大值，在分页的时候使用
			// TopDocs topDocs = indexSearcher.search(query, 5);

			// 获取上一页的最后一个元素
			ScoreDoc lastSd = getLastScoreDoc(currentPage, pageSize, query, indexSearcher);
			// 通过最后一个元素去搜索下一页的元素
			TopDocs topDocs = indexSearcher.searchAfter(lastSd, query, pageSize);

			// 高亮显示
			Highlighter highlighter = addStringHighlighter(query);

			// 取查询结果总数量
			// System.out.println("总共的查询结果：" + topDocs.totalHits);
			// 查询结果，就是documentID列表
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				// 取对象document的对象id
				int docID = scoreDoc.doc;
				// 相关度得分
				float score = scoreDoc.score;
				// 根据ID去document对象
				Document document = indexSearcher.doc(docID);
				String content = stringFormatHighlighterOut(getAnalyzer(), highlighter, document, "content");

				if (document.get("indexType").equals("1")) { // 主题贴
					searchResults.add(new IndexObject(Integer.valueOf(document.get("topicId")),
							Integer.valueOf(document.get("isUp")), Integer.valueOf(document.get("isNice")),
							Integer.valueOf(document.get("userId")), document.get("userName"),
							new Date(Long.valueOf(document.get("createTime"))),
							Integer.valueOf(document.get("postCount")), Integer.valueOf(document.get("pv")),
							Integer.valueOf(document.get("moduleId")), document.get("moduleName"), content,
							document.get("postContent"), 1, score));
				} else if (document.get("indexType").equals("2")) { // 回复贴
					searchResults.add(new IndexObject(Integer.valueOf(document.get("topicId")),
							Integer.valueOf(document.get("isUp")), Integer.valueOf(document.get("isNice")),
							Integer.valueOf(document.get("userId")), document.get("userName"),
							new Date(Long.valueOf(document.get("createTime"))),
							Integer.valueOf(document.get("postCount")), Integer.valueOf(document.get("pv")),
							Integer.valueOf(document.get("moduleId")), document.get("moduleName"),
							document.get("topicContent"), content, 2, score));
				}

				// System.out.println("相关度得分：" + score);
				// System.out.println("content:"+stringFormatHighlighterOut(getAnalyzer(),
				// highlighter, document,"content"));
				// System.out.println("tid:"+document.get("tid"));
				// System.out.println("=======================");
			}

			pageQuery = new PageQuery<>(currentPage, null);
			pageQuery.setPageSize(pageSize);
			pageQuery.setTotalRow(topDocs.totalHits);
			Collections.sort(searchResults);
			pageQuery.setList(searchResults);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pageQuery;
	}

	/**
	 * 根据页码和分页大小获取上一次的最后一个scoredoc
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param query
	 * @param searcher
	 * @return ScoreDoc
	 * @throws IOException
	 */
	private ScoreDoc getLastScoreDoc(Integer currentPage, Integer pageSize, Query query, IndexSearcher searcher)
			throws IOException {
		if (currentPage == 1)
			return null;// 如果是第一页就返回空
		int num = pageSize * (currentPage - 1);// 获取上一页的最后数量
		TopDocs tds = searcher.search(query, num);
		return tds.scoreDocs[num - 1];
	}

	/**
	 * 设置字符串高亮
	 * 
	 * @param query
	 * @return
	 */
	private Highlighter addStringHighlighter(Query query) {
		// 算分
		QueryScorer scorer = new QueryScorer(query);
		// 显示得分高的片段
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		// 设置标签内部关键字的颜色
		// 第一个参数：标签的前半部分；第二个参数：标签的后半部分。
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		// 第一个参数是对查到的结果进行实例化；第二个是片段得分（显示得分高的片段，即摘要）
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		// 设置片段
		highlighter.setTextFragmenter(fragmenter);
		return highlighter;
	}

	private String stringFormatHighlighterOut(Analyzer analyzer, Highlighter highlighter, Document document,
			String field) throws Exception {
		String fieldValue = document.get(field);
		if (fieldValue != null) {
			// 把全部得分高的摘要给显示出来
			// 第一个参数是对哪个参数进行设置；第二个是以流的方式读入
			TokenStream tokenStream = analyzer.tokenStream(field, new StringReader(fieldValue));
			// 获取最高的片段
			return highlighter.getBestFragment(tokenStream, fieldValue);
		}
		return null;
	}

	/**
	 * 转译a标签
	 * 
	 * @param content
	 * @return
	 */
	private String labelformat(String content) {
		if (StringUtils.isBlank(content))
			return "";
		return content.replaceAll("<a", "&lt;a").replaceAll("</a>", "&lt;/a&gt;");
	}

	/**
	 * date 转 localdate
	 * 
	 * @param date
	 * @return
	 */
	public LocalDate getLocalDate(Date date) {
		if (date == null)
			return null;
		return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
	}

	/**
	 * 日期比较（只比较日期部分） date1 > date2 返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static boolean dateCompare(Date date1, Date date2) {
		if (date1 == null)
			return false;
		if (date2 == null)
			return true;
		if (date1.getTime() > date2.getTime())
			return true;
		return false;
	}
}
