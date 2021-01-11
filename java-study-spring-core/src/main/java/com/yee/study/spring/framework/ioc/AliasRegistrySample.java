package com.yee.study.spring.framework.ioc;

import com.yee.study.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.AliasRegistry;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.util.StringUtils;

/**
 * AliasRegistry 示例
 *
 * @author Roger.Yi
 */
public class AliasRegistrySample {

    private static final Logger log = LoggerFactory.getLogger(AliasRegistrySample.class);

    /**
     *
     */
    @Test
    public void testIocSimple() {
        AliasRegistry aliasRegistry = new SimpleAliasRegistry();

        // 注册一个别名，比如张三的小名就狗二，张三就是本名
        aliasRegistry.registerAlias("张三", "狗二");
        // 张三还有一个别名，叫 神枪手
        aliasRegistry.registerAlias("张三", "神枪手");

        // 此时，如果获取神枪手，就是张三了
        log.info("神枪手本名是" + ((SimpleAliasRegistry)aliasRegistry).canonicalName("神枪手"));
        // 获取一下张三的所有别名
        log.info("张三的别名有：" + StringUtil.join(aliasRegistry.getAliases("张三"), ","));

        // 别名的别名
        // 有人管 狗二 就 全蛋
        aliasRegistry.registerAlias("狗二", "全蛋");
        // 有人管 全蛋 叫 富士康质检员
        aliasRegistry.registerAlias("全蛋", "富士康质检员");
        // 有人管 神枪手 叫 猎人
        aliasRegistry.registerAlias("神枪手", "猎人");
        // 不能循取别名
        // aliasRegistry.registerAlias("猎人", "神枪手"); // 报错
        // aliasRegistry.registerAlias("猎人", "张三");  // 报错

        // 不能将不同的名字叫同一个别名，会将之前的覆盖掉。比如两个别名设置，守夜人就会覆盖猎人，
        // 会割裂了 张三 与 矮人火枪手 的联系，导致 张三的别名中没有 矮人火枪手
        // aliasRegistry.registerAlias("猎人", "矮人火枪手");
        // aliasRegistry.registerAlias("守夜人", "矮人火枪手");

        // 此时，如果获取猎人，就是张三了
        log.info("猎人本名是" + ((SimpleAliasRegistry)aliasRegistry).canonicalName("猎人"));
        // 获取一下张三的所有别名
        log.info("张三的别名有：" + StringUtil.join(aliasRegistry.getAliases("张三"), ","));
    }
}
