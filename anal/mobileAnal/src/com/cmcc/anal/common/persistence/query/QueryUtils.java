package com.cmcc.anal.common.persistence.query;

import java.util.List;

/**
 * 
 * @author chenke
 * @since 2006-6-20
 */
public class QueryUtils {

    private QueryUtils() {

    }

    /**
     * �Ӳ�ѯ���б��в��ұ���Ϊ tableAlias �ı�ı����
     * @param queryTableList List<QueryTableDTO>
     * @return �����; null, ������Ϊ tableAlias �ı?����
     */
    public static String getTableCode(List queryTableList, String tableAlias) {
        for (int i = 0; i < queryTableList.size(); i++) {
            QueryTableDTO qt = (QueryTableDTO) queryTableList.get(i);
            String ta = qt.getTableAlias();
            if (tableAlias.equalsIgnoreCase(ta)) {
                return qt.getTableCode();
            }
        }
        return null;
    }

    /**
     * �Ӳ�ѯ���б��в��ұ���Ϊ tableAlias �Ĳ�ѯ�����
     * @param queryTableList List<QueryTableDTO>
     * @return ��ѯ�����; null, ������Ϊ tableAlias �ı?����
     */
    public static QueryTableDTO getQueryTable(List queryTableList,
            String tableAlias) {
        for (int i = 0; i < queryTableList.size(); i++) {
            QueryTableDTO qt = (QueryTableDTO) queryTableList.get(i);
            String ta = qt.getTableAlias();
            if (tableAlias.equalsIgnoreCase(ta)) {
                return qt;
            }
        }
        return null;
    }

    /**
     * ����ֶεı��� alias �� queryColumnList ����ҵ����ֶε� code
     * @param queryColumnList List<QueryColumnDTO>
     * @param alias �ֶα���
     * @return �ֶδ���; null, ���û�и��ֶα���
     */
    public static String getColumnCode(List queryColumnList, String alias) {
        for (int i = 0; i < queryColumnList.size(); i++) {
            QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
            String s = qc.getColumnAlias();
            if (alias.equals(s)) {
                return qc.getColumnCode();
            }
        }
        return null;
    }

    /**
     * ������в�ѯ��ı���
     */
    public static String[] getTableAlias(List queryTableList) {
        String[] result = new String[queryTableList.size()];
        for (int i = 0; i < queryTableList.size(); i++) {
            QueryTableDTO qt = (QueryTableDTO) queryTableList.get(i);
            result[i] = qt.getTableAlias();
        }
        return result;
    }

    /**
     * ����ֶα������ QueryColumnDTO
     * @return null, ���û���ҵ�
     */
    public static QueryColumnDTO getQueryColumn(List queryColumnList, String alias) {
        for (int i = 0; i < queryColumnList.size(); i++) {
            QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
            String s = qc.getColumnAlias();
            if (alias.equalsIgnoreCase(s)) {
                return qc;
            }
        }
        return null;
    }

    
}
