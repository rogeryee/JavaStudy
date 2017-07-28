package com.yee.study.designpattern.command;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        CommandInvoker invoker = new CommandInvoker();
        invoker.execute(new SendSmsCmd());
        invoker.execute(new SendMailCmd());
    }
}
