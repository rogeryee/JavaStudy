package com.yee.study.zookeeper.zkorigin;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.EventType;
import static org.apache.zookeeper.Watcher.Event.KeeperState;
import static org.apache.zookeeper.ZooDefs.Ids;

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

        doCreateData("/zk-test-");
//        doDataChange("/zk-test");
//        doChildNodeData("/zk-test");


        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 创建节点
     *
     * @throws Exception
     */
    private static void doCreateData(String path) throws Exception {
        String path1 = zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        logger.info("create path=[{}] end.", path1);

        String path2 = zk.create(path, "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        logger.info("create path=[{}] end.", path2);

        zk.create(path, "789".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                logger.info("create path result : " + rc + ", path = " + path + ", ctx = " + ctx + ", name = " + name);
            }
        }, "I am context");
    }

    /**
     * 修改数据
     *
     * @throws Exception
     */
    private static void doDataChange(String path) throws Exception {
        zk.getData(path, true, stat);
        logger.info("get path=[{}] : czxid=[{}], mzxid=[{}], version=[{}]", path, stat.getCzxid(), stat.getMzxid(), stat
                .getVersion());
        logger.info("set path=[{}] start...", path);
        zk.setData("/zk-test", "123".getBytes(), -1);
    }

    /**
     * 操作子节点
     *
     * @throws Exception
     */
    private static void doChildNodeData(String path) throws Exception {
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        int size = 3;
        for (int i = 1; i <= size; i++) {
            String childPath = path + "/" + "c" + i;
            logger.info("create child path=[{}]", childPath);
            zk.create(childPath, String.valueOf(i).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        List<String> list = zk.getChildren(path, true);
        for (String str : list) {
            logger.info("get child value=[{}]", str);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("Receive watched event: state=[{}] type=[{}]", event.getState(), event.getType());

        try {
            if (event.getState() == KeeperState.SyncConnected) {
                if (event.getType() == EventType.None && null == event.getPath()) {
                    cdl.countDown();
                } else if (event.getType() == EventType.NodeDataChanged) {
                    zk.getData(event.getPath(), true, stat);
                    logger.info("set path=[{}] end : czxid=[{}], mzxid=[{}], version=[{}]", event.getPath(), stat.getCzxid(), stat
                            .getMzxid(), stat.getVersion());
                } else if (event.getType() == EventType.NodeChildrenChanged) {
                    zk.getChildren(event.getPath(), true, stat);
                    logger.info("get child path=[{}] end : czxid=[{}], mzxid=[{}], version=[{}]", event.getPath(), stat.getCzxid(), stat
                            .getMzxid(), stat.getVersion());
                }
            }
        } catch (Exception e) {
            logger.error("process event meet error.", e);
        }
    }
}
