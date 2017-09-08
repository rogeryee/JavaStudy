package com.yee.study.java.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 分店类 (可以有加盟店)
 * @author Roger.Yi
 */
public class BranchStore extends Store
{
    private List<Store> stores = new ArrayList<>();

    public BranchStore(String name)
    {
        setName(name);
    }

    @Override
    public void remove(Store store)
    {
        stores.remove(store);
    }

    @Override
    public void add(Store store)
    {
        stores.add(store);
    }

    @Override
    public void pay()
    {
        System.out.println(getName() + "消费,积分已累加入该会员卡");
        for(Store store : stores)
        {
            store.pay();
        }
    }
}
