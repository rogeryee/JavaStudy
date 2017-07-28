package com.yee.study.designpattern.state;

/**
 * 运行状态
 * @author Roger.Yi
 */
public class RunState extends AbstractLiftState
{
    public RunState(String name, Context context)
    {
        super(name, context);
    }

    @Override
    public void run()
    {
        System.out.println("Lift run.");
    }

    @Override
    public void stop()
    {
        context.updateState(Context.NAME_STOP_STATE);
        context.stop();
    }
}
