package com.yee.study.java.designpattern.state;

/**
 * 开门状态
 * @author Roger.Yi
 */
public class OpenState extends AbstractLiftState
{
    public OpenState(String name, Context context)
    {
        super(name, context);
    }

    @Override
    public void open()
    {
        System.out.println("Lift open.");
    }

    @Override
    public void close()
    {
        context.updateState(Context.NAME_CLOSE_STATE);
        context.close();
    }
}
