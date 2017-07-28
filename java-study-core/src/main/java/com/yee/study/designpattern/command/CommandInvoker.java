package com.yee.study.designpattern.command;

/**
 * 命令调用类
 * @author Roger.Yi
 */
public class CommandInvoker
{
    public void execute(Command command)
    {
        command.execute();
    }
}
