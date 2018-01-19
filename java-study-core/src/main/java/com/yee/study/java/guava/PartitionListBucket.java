package com.yee.study.java.guava;

import com.google.common.collect.Lists;
import com.yee.study.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 基于Guava的List分片桶
 * 
 * Roger Yi
 **/
public class PartitionListBucket<E>
{
    /**
     * 分片大小
     */
    private int partitionSize;

    /**
     * 桶元素
     */
    private List<E> elementList;

    /**
     * 处理单元
     */
    private Consumer<List<E>> partitionListConsumer;

    public PartitionListBucket(int partitionSize, Consumer<List<E>> partitionListConsumer)
    {
        if (partitionSize < 1)
        {
            throw new IllegalArgumentException("illegal partitionSize [" + partitionSize + "].");
        }
        this.partitionSize = partitionSize;
        this.elementList = new ArrayList<>(partitionSize);
        this.partitionListConsumer = partitionListConsumer;
    }

    /**
     * 添加元素
     *
     * @param element
     * @return
     */
    public void add(E element)
    {
        if (element == null)
        {
            return;
        }
        this.elementList.add(element);
        flush(false);

    }

    /**
     * 批量添加元素
     * @param elements
     */
    public void addAll(Collection<E> elements)
    {
        if (CollectionUtil.isEmpty(elements))
        {
            return;
        }
        this.elementList.addAll(elements);
        flush(false);
    }

    /**
     * 获取桶中元素个数
     * @return
     */
    public int size()
    {
        return this.elementList.size();
    }

    /**
     * 获取桶中的元素集合
     * @return
     */
    public List<E> getElementList()
    {
        return Collections.unmodifiableList(this.elementList);
    }

    /**
     * 处理元素
     * @param elements
     */
    private void consume(List<E> elements)
    {
        partitionListConsumer.accept(Collections.unmodifiableList(elements));
    }

    /**
     * 刷新桶（用于触发桶分片和处理元素）
     * @param force
     */
    public void flush(boolean force)
    {
        if (force)
        {
            consume(getElementList());
            return;
        }
        if (elementList.size() >= partitionSize)
        {
            List<E> remainElements = null;

            for (List<E> splitList : Lists.partition(elementList, partitionSize))
            {
                if (splitList.size() < partitionSize)
                {
                    remainElements = splitList;
                    break;
                }
                else
                {
                    consume(splitList);
                }
            }

            if (remainElements != null)
            {
                elementList = new ArrayList<>(remainElements);
            }
            else
            {
                elementList.clear();
            }
        }
    }
}
