package com.cmcc.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.Predicate;

/**
 * 
 * @author chenke
 * @since 2006-6-11
 */
public class ListUtils {

    public static final List EMPTY_LIST = Collections
            .unmodifiableList(new ArrayList(0));

    private ListUtils() {

    }

    /**
     * 过滤掉 predicator.evaluate 为 false 的对象
     * @param list 用于过滤的原 list
     * @param predicate 定义 list 里的对象是否应该被过滤的接口
     * @see Predicate
     */
    public static List filter(List list, Predicate predicate) {
        List l = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (predicate.evaluate(obj)) {
                l.add(obj);
            }
        }
        return l;
    }

    /**
     * 遍历 list,  使用 comparator, 找出所有和 obj 相等的对象, 返回他们的索引
     */
    public static int[] indexOf(List list, Object obj, Comparator comparator) {
        List result = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object obj2 = list.get(i);
            if (comparator.compare(obj, obj2) == 0) {
                result.add(new Integer(i));
            }
        }
        int[] n = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Integer integer = (Integer) result.get(i);
            n[i] = integer.intValue();
        }
        return n;
    }

    /**
     * 由于 List 对象经过序列化后, 客户端只能反序列化 ArrayList, 因此这里提供一个方法
     * 用来将 l 转化为 ArrayList 对象
     * @param l 被转化的 List 对象
     * @return 一个 ArrayList 对象, 里面装了 l 里的所有对象句柄
     */
    public static List toArrayList(List l) {
        if (l == null || l.size() == 0) {
            return EMPTY_LIST;
        }
        if (l instanceof ArrayList) {
            return l;
        }
        List result = new ArrayList(l.size());
        for (int i = 0; i < l.size(); i++) {
            result.add(l.get(i));
        }
        return result;
    }

    /**
     * 复制 l 里的对象句柄到一个新的 List 对象里返回
     * @return null, 如果 l == null
     */
    public static List copy(List l) {
        if (l == null) {
            return null;
        }
        List result = new ArrayList(l.size());
        for (int i = 0; i < l.size(); i++) {
            result.add(l.get(i));
        }
        return result;
    }

}
