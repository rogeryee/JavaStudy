package com.yee.study.zookeeper.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 使用原生Zookeeper包示例
 *
 * @author Roger.Yi
 */
public class ZkDemo implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkDemo.class);

    private static final CountDownLatch cdl = new CountDownLatch(1);

    private static ZooKeeper zk = null;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper("localhost:2181", 5000, new ZkDemo());
        cdl.await();

//        doCreateData();
//        doDataChange();

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 创建节点
     * @throws Exception
     */
    private static void doCreateData() throws Exception {
        String path1 = zk.create("/zk-test-", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        logger.info("create path=[{}] end.", path1);

        String path2 = zk.create("/zk-test-", "456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        logger.info("create path=[{}] end.", path2);
    }

    /**
     * 修改数据
     * @throws Exception
     */
    private static void doDataChange() throws Exception {
        String path = "/zk-test";
        zk.getData(path, true, stat);
        logger.info("get path=[{}] : czxid=[{}], mzxid=[{}], version=[{}]", path, stat.getCzxid(), stat.getMzxid(), stat
                .getVersion());
        logger.info("set path=[{}] start...", path);
        zk.setData("/zk-test", "123".getBytes(), -1);
    }

    @Override
    public void process(WatchedEvent event) {
        try {
            logger.info("Receive watched event: state=[{}] type=[{}]", event.getState(), event.getType());

            if (Event.KeeperState.SyncConnected == event.getState()) {
                if (Event.EventType.None == event.getType() && null == event.getPath()) {
                    cdl.countDown();
                } else if (event.getType() == Event.EventType.NodeDataChanged) {
                    zk.getData(event.getPath(), true, stat);
                    logger.info("set path=[{}] end : czxid=[{}], mzxid=[{}], version=[{}]", event.getPath(), stat.getCzxid(), stat
                            .getMzxid(), stat.getVersion());
                }
            }
        } catch (Exception e) {
            logger.error("process event meet error.", e);
        }
    }
}
