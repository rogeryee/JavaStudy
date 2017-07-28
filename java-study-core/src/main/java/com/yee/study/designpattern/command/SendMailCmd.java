package com.yee.study.designpattern.command;

/**
 * 发送邮件命令类
 * @author Roger.Yi
 */
public class SendMailCmd implements Command
{
    @Override
    public void execute()
    {
        System.out.println("Send mail.");
    }
}
