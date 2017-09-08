package com.yee.study.java.designpattern.bridge;

/**
 * 抽象汽车类
 * @author Roger.Yi
 */
public abstract class AbstractCar implements Car
{
    private Engine engine;

    @Override
    public void start()
    {
        this.engine.start();
    }

    @Override
    public void stop()
    {
        this.engine.stop();
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }
}
