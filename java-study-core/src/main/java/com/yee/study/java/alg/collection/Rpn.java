package com.yee.study.java.alg.collection;

import java.util.Stack;

/**
 * 逆波兰算法 （Reverse Polish notation）
 * <p>
 * 本示例展示了如何将中缀表达式转换成后缀表达式（RPN），并完成表达式计算
 *
 * @author Roger.Yi
 */
public class Rpn {

    private static final Operator OP_ADD = new Add('+', 1);

    private static final Operator OP_SUB = new Subtract('-', 1);

    private static final Operator OP_MUL = new Multiply('*', 2);

    private static final Operator OP_LB = new LeftBracket('(', 3);

    private static final Operator OP_RB = new RightBracket(')', 3);

    private static final Operator[] SUPPORT_OPERATORS = {OP_ADD, OP_SUB, OP_MUL, OP_LB, OP_RB};


    /**
     * 用于存放运算符
     */
    private Stack<Operator> operators = new Stack<>(); //运算符

    /**
     * 用于存放逆波兰表达式
     */
    private Stack output = new Stack();

    /**
     * 完成逆波兰表达式转换
     *
     * @param str
     */
    public void doRpn(String str) {
        char[] chars = str.toCharArray();

        int pre = 0;
        int len = chars.length;
        boolean digital = false; //是否为数字（只要不是运算符，都是数字），用于截取字符串

        for (int i = 0; i < len; ) {
            pre = i;
            while (i < len && findOperator(chars[i]) == null) {
                i++;
                digital = true;
            }

            if (digital) {
                output.push(str.substring(pre, i));
                digital = false;
                continue;
            }

            Operator operator = findOperator(chars[i++]);

            if (operator == OP_LB) {
                operators.push(operator);
            } else if (operator == OP_RB) {
                while (!operators.empty()) {
                    Operator op = operators.pop();
                    if (op == OP_LB) {
                        break;
                    }

                    output.push(op);
                }
            } else {

                while (!operators.empty() && operators.peek() != OP_LB && !operator.compare(operators.peek())) {
                    output.push(operators.pop());
                }

                operators.push(operator);
            }
        }
        //遍历结束，将运算符栈全部压入output
        while (!operators.empty()) {
            output.push(operators.pop());
        }
    }

    private Operator findOperator(char ch) {
        for (Operator operator : SUPPORT_OPERATORS) {
            if (operator.value == ch) {
                return operator;
            }
        }

        return null;
    }

    public static void main(String[] args) {

        Rpn rpn = new Rpn();
        rpn.doRpn("1+((2+3)*4)-5");
        System.out.println();
    }
}

abstract class Operator {

    char value;

    int priority;

    Operator(char value, int priority) {
        this.priority = priority;
        this.value = value;
    }

    /**
     * 比较优先级
     *
     * @param operator
     * @return
     */
    public boolean compare(Operator operator) {
        return this.priority > operator.priority;
    }

    /**
     * 计算
     *
     * @param value1
     * @param value2
     * @return
     */
    public abstract int calc(int value1, int value2);
}

class Add extends Operator {

    public Add(char value, int priority) {
        super(value, priority);
    }

    @Override
    public int calc(int value1, int value2) {
        return value1 + value2;
    }
}

class Subtract extends Operator {

    public Subtract(char value, int priority) {
        super(value, priority);
    }

    @Override
    public int calc(int value1, int value2) {
        return value1 - value2;
    }
}

class Multiply extends Operator {

    public Multiply(char value, int priority) {
        super(value, priority);
    }

    @Override
    public int calc(int value1, int value2) {
        return value1 * value2;
    }
}

class LeftBracket extends Operator {

    public LeftBracket(char value, int priority) {
        super(value, priority);
    }

    @Override
    public int calc(int value1, int value2) {
        throw new UnsupportedOperationException();
    }
}

class RightBracket extends Operator {

    public RightBracket(char value, int priority) {
        super(value, priority);
    }

    @Override
    public int calc(int value1, int value2) {
        throw new UnsupportedOperationException();
    }
}
