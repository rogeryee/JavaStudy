package com.yee.study.java.designpattern.iterator;

/**
 * 列表实现类
 * @author Roger.Yi
 */
public class ConcreteList<E> implements List<E>
{
    private Object[] array;

    private int index;

    public ConcreteList(int size)
    {
        array = new Object[size];
        index = 0;
    }

    @Override
    public void add(E e)
    {
        array[index++] = e;
    }

    @Override
    public E get(int index)
    {
        return (E)array[index];
    }

    @Override
    public int size()
    {
        return array.length;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ListIterator<>(this);
    }
}
