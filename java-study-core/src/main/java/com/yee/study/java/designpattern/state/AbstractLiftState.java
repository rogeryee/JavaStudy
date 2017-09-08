package com.yee.study.java.designpattern.state;

/**
 * 抽象电梯状态
 * @author Roger.Yi
 */
public abstract class AbstractLiftState implements LiftState
{
    protected String name;

    protected Context context;

    public AbstractLiftState(String name, Context context)
    {
        this.name = name;
        this.context = context;
    }

    @Override
    public void close()
    {
        doNoting();
    }

    @Override
    public void open()
    {
        doNoting();
    }

    @Override
    public void run()
    {
        doNoting();
    }

    @Override
    public void stop()
    {
        doNoting();
    }

    private void doNoting()
    {
        System.out.println("Noting happen.");
    }
}
