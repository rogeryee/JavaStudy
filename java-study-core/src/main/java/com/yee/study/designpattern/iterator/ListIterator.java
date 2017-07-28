package com.yee.study.designpattern.iterator;

/**
 * 列表迭代器类
 *
 * @author Roger.Yi
 */
public class ListIterator<E> implements Iterator<E>
{
    private List<E> list;

    private int index;

    public ListIterator(List<E> list)
    {
        this.list = list;
    }

    @Override
    public boolean hasNext()
    {
        if(index >= list.size())
            return false;
        else
            return true;
    }

    @Override
    public E next()
    {
        E e = list.get(index);
        index++;
        return e;
    }
}
