package com.yee.study.designpattern.composite;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Store root = new BranchStore("总店");
        Store luwanStore = new BranchStore("卢湾区分店");
        Store wuliStore = new MemberStore("卢湾区-五里街道店");
        Store nanmenStore = new MemberStore("卢湾区-小南门店");

        luwanStore.add(wuliStore);
        luwanStore.add(nanmenStore);
        root.add(luwanStore);

        wuliStore.pay();
        System.out.println("-------------------");
        luwanStore.pay();
        System.out.println("-------------------");
        root.pay();
    }
}
