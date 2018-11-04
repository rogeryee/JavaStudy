package com.yee.study.java.alg.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 * <p>
 * 两个队列添加元素，哪个队列为空，由于在输出元素时，要进行相应元素的移动（除去尾部元素），所以要在对应不为空的队列进行元素的添加；
 * 在输出数据时，要进行两个队列的变相操作，不为空的队列要依次向为空的队列中添加元素，直到尾元素输出即可！
 *
 * @author Roger.Yi
 */
public class StackBy2Queue {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackBy2Queue.class);
    
    private Queue<Integer> queue1 = new ArrayDeque<Integer>();

    private Queue<Integer> queue2 = new ArrayDeque<Integer>();

    /*
     * 向栈中压入数据
     */
    public void push(Integer element) {
        //两个队列都为空时，优先考虑 queue1
        if (queue1.isEmpty() && queue2.isEmpty()) {
            queue1.add(element);
            return;
        }

        //如果queue1为空，queue2有数据，直接放入queue2
        if (queue1.isEmpty()) {
            queue2.add(element);
            return;
        }

        //如果queue2为空，queue1有数据，直接放入queue1中
        if (queue2.isEmpty()) {
            queue1.add(element);
            return;
        }
    }

    /*
     * 从栈中弹出一个数据
     */
    public Integer pop() {
        
        //如果两个栈都为空，则没有元素可以弹出，异常
        if (queue1.isEmpty() && queue2.isEmpty()) {
            try {
                throw new Exception("Stack is empty!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果queue1中没有元素，queue2中有元素，将其queue2中的元素依次放入queue1中，直到最后一个元素，弹出即可
        if (queue1.isEmpty()) {
            while (queue2.size() > 1) {
                queue1.add(queue2.poll());
            }
            return queue2.poll();
        }

        //如果queue2中没有元素，queue1中有元素，将其queue1中的元素依次放入queue2中，直到最后一个元素，弹出即可
        if (queue2.isEmpty()) {
            while (queue1.size() > 1) {
                queue2.add(queue1.poll());
            }
            return queue1.poll();
        }

        return (Integer) null;
    }

    public static void main(String[] args) {
        StackBy2Queue qs = new StackBy2Queue();
        qs.push(2);
        qs.push(4);
        qs.push(7);
        qs.push(5);

        LOGGER.info(String.valueOf(qs.pop()));
        LOGGER.info(String.valueOf(qs.pop()));

        qs.push(1);
        LOGGER.info(String.valueOf(qs.pop()));
        LOGGER.info(String.valueOf(qs.pop()));
        LOGGER.info(String.valueOf(qs.pop()));
    }
}
