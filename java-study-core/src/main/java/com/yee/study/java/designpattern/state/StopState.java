package com.yee.study.java.designpattern.state;

/**
 * 停止状态
 * @author Roger.Yi
 */
public class StopState extends AbstractLiftState
{
    public StopState(String name, Context context)
    {
        super(name, context);
    }

    @Override
    public void open()
    {
        context.updateState(Context.NAME_OPEN_STATE);
        context.open();
    }

    @Override
    public void run()
    {
        context.updateState(Context.NAME_RUN_STATE);
        context.run();
    }

    @Override
    public void stop()
    {
        System.out.println("Lift stop.");
    }
}
