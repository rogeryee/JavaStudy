package com.yee.study.designpattern.iterator;

/**
 * 列表接口类
 * @author Roger.Yi
 */
public interface List<E>
{
    /**
     * 添加元素
     * @param e
     */
    void add(E e);

    /**
     * 获取元素
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 获取列表元素总数
     * @return
     */
    int size();

    /**
     * 获取迭代器
     * @return
     */
    Iterator iterator();
}
