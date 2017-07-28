package com.yee.study.designpattern.command;

/**
 * 发送短信命令类
 * @author Roger.Yi
 */
public class SendSmsCmd implements Command
{
    @Override
    public void execute()
    {
        System.out.println("Send sms.");
    }
}
