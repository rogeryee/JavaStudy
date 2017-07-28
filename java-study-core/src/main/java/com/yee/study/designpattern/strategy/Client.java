package com.yee.study.designpattern.strategy;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        MemberStrategy strategy1 = new PrimaryMemberStrategy();
        MemberStrategy strategy2 = new IntermediateMemberStrategy();
        MemberStrategy strategy3 = new AdvancedMemberStrategy();

        System.out.println(new Price(strategy1).quote(100.0));
        System.out.println(new Price(strategy2).quote(100.0));
        System.out.println(new Price(strategy3).quote(100.0));
    }
}
