package com.yee.study.designpattern.composite;

/**
 * 加盟店类 (不能有加盟店)
 * @author Roger.Yi
 */
public class MemberStore extends Store
{
    public MemberStore(String name)
    {
        setName(name);
    }

    @Override
    public void pay()
    {
        System.out.println(getName() + "消费,积分已累加入该会员卡");
    }
}
