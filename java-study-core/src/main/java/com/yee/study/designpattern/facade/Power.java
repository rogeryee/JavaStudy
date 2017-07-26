package com.yee.study.designpattern.facade;

/**
 * 电源
 * @author Roger.Yi
 */
public class Power
{
    private Light light;

    private Television television;

    private AirCondition airCondition;

    public Power()
    {
        this.light = new Light();
        this.airCondition = new AirCondition();
        this.television = new Television();
    }

    /**
     * 开电源
     */
    public void on()
    {
        this.light.turnOn();
        this.airCondition.turnOn();
        this.television.turnOn();
    }

    /**
     * 关电源
     */
    public void off()
    {
        this.light.turnOff();
        this.airCondition.turnOff();
        this.television.turnOff();
    }
}
