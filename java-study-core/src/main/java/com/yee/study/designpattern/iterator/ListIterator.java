package com.yee.study.designpattern.iterator;

/**
 * 列表迭代器类
 *
 * @author Roger.Yi
 */
public class ListIterator implements Iterator
{
    private List list;

    private int index;

    public ListIterator(List list)
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
    public Object next()
    {
        Object obj = list.get(index);
        index++;
        return obj;
    }
}
