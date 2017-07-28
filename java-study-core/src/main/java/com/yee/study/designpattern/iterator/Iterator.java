package com.yee.study.designpattern.iterator;

/**
 * 迭代器接口
 * @author Roger.Yi
 */
public interface Iterator<E>
{
    boolean hasNext();

    E next();
}
