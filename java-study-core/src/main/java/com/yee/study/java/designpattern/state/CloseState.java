package com.yee.study.java.designpattern.state;

/**
 * 关门状态
 * @author Roger.Yi
 */
public class CloseState extends AbstractLiftState
{
    public CloseState(String name, Context context)
    {
        super(name, context);
    }

    @Override
    public void run()
    {
        context.updateState(Context.NAME_RUN_STATE);
        context.run();
    }

    @Override
    public void close()
    {
        System.out.println("Lift close.");
    }

    @Override
    public void open()
    {
        context.updateState(Context.NAME_OPEN_STATE);
        context.open();
    }

    @Override
    public void stop()
    {
        context.updateState(Context.NAME_STOP_STATE);
        context.stop();
    }
}
