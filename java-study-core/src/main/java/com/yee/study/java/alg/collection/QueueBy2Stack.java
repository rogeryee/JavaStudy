package com.yee.study.java.alg.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * 两个栈实现一个队列
 * <p>
 * 第一个栈只负责添加元素，第二个栈在弹出元素时，首先判断当前栈是否为空，若为空就直接将其第一个栈中的数据全部压入第二个栈中，然后输出栈顶元素，即可实现队列效果；
 * 若第二个栈中有数据，添加直接将其数据压入第一个栈中，输出时直接输出第二个栈顶的元素即可！
 *
 * @author Roger.Yi
 */
public class QueueBy2Stack {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueBy2Stack.class);

    private Stack<Integer> stack1 = new Stack<Integer>();

    private Stack<Integer> stack2 = new Stack<Integer>();

    /*
     * 队列的数据压入过程
     */
    public void add(Integer element) {
        stack1.push(element);
    }

    /*
     * 队列的数据弹出过程
     */
    public Integer poll() {
        if (stack2.size() <= 0) {    //第二个栈为空
            while (stack1.size() > 0) {    //第一个栈不为空
                stack2.push(stack1.pop());    //将其第一个栈的数据压入第二个栈中
            }
        }
        if (stack2.isEmpty()) {
            try {
                throw new Exception("queue is empty");
            } catch (Exception e) {
            }
        }
        Integer head = stack2.pop();
        return head;
    }

    public static void main(String[] args) {
        QueueBy2Stack sq = new QueueBy2Stack();
        sq.add(1);
        sq.add(3);
        sq.add(5);
        sq.add(4);
        sq.add(2);

        LOGGER.info(String.valueOf(sq.poll()));
        LOGGER.info(String.valueOf(sq.poll()));

        sq.add(7);
        LOGGER.info(String.valueOf(sq.poll()));
        LOGGER.info(String.valueOf(sq.poll()));
        LOGGER.info(String.valueOf(sq.poll()));
        LOGGER.info(String.valueOf(sq.poll()));
    }
}
