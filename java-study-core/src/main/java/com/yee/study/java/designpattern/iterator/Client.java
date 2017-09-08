package com.yee.study.java.designpattern.iterator;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        List<String> list = new ConcreteList<>(5);
        list.add("No.1");
        list.add("No.2");
        list.add("No.3");
        list.add("No.4");
        list.add("No.5");

        for(Iterator<String> iterator = list.iterator();iterator.hasNext();)
        {
            System.out.println(iterator.next());
        }
    }
}
