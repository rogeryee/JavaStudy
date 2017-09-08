package com.yee.study.java.designpattern.state;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Context context = new Context(Context.NAME_CLOSE_STATE);
        context.close();
        context.open();
        context.run();
        context.stop();
        context.close();
        context.run();
    }
}
