package com.yee.study.designpattern.state;

/**
 * 状态上下文
 * @author Roger.Yi
 */
public class Context
{
    public static final String NAME_OPEN_STATE = "O";

    public static final String NAME_CLOSE_STATE = "C";

    public static final String NAME_RUN_STATE = "R";

    public static final String NAME_STOP_STATE = "S";

    private final LiftState openState = new OpenState(NAME_OPEN_STATE, this);

    private final LiftState closeState = new CloseState(NAME_CLOSE_STATE, this);

    private final LiftState runState = new RunState(NAME_RUN_STATE, this);

    private final LiftState stopState = new StopState(NAME_STOP_STATE, this);

    private LiftState currentState = null;

    public Context(String name)
    {
        updateState(name);
        if(currentState == null)
            throw new IllegalArgumentException("Incorrect state name = " + name);
    }

    public void updateState(String name)
    {
        switch (name)
        {
            case NAME_OPEN_STATE:
                currentState = openState;
                break;
            case NAME_CLOSE_STATE:
                currentState = closeState;
                break;
            case NAME_RUN_STATE:
                currentState = runState;
                break;
            case NAME_STOP_STATE:
                currentState = stopState;
                break;
        }
    }

    /**
     * 开门
     */
    public void open()
    {
        currentState.open();
    }

    /**
     * 关门
     */
    public void close()
    {
        currentState.close();
    }

    /**
     * 运行
     */
    public void run()
    {
        currentState.run();
    }

    /**
     * 停止
     */
    public void stop()
    {
        currentState.stop();
    }
}
