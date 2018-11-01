package com.yee.study.java.core.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 使用java.nio中Buffer对象的示例
 *
 * @author Roger.Yi
 */
public class BufferSample
{
    private static final Logger logger = LoggerFactory.getLogger(BufferSample.class);

    public static void main(String[] args)
    {
//        testBufferAllocation();
        testBufferUsage();
    }

    public static void testBufferAllocation()
    {
        System.out.println("----------Test allocate--------");
        System.out.println("before alocate:" + Runtime.getRuntime().freeMemory());

        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
        // 要超过多少内存大小JVM才能感觉到？
        ByteBuffer buffer = ByteBuffer.allocate(1024000);
        System.out.println("buffer = " + buffer);

        System.out.println("after alocate:" + Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024000);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct alocate:" + Runtime.getRuntime().freeMemory());

        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);
    }

    /**
     * ByteBuffer是一个保存byte的缓冲区，它有如下几个属性：
     * capacity：作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.
     * 你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
     * <p>
     * position：当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后，
     * position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
     * 当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0.
     * 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
     * <p>
     * limit：在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
     * 当切换Buffer到读模式时，limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。
     * 换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
     * <p>
     * mark：一个临时存放的标志位，可以通过mark()、reset()方法来设置
     * <p>
     * 上述几个属性之间的关系如下：mark <= position <= limit <= capacity
     *
     * 常用方法：
     * flip：flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
     * 换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等 —— 现在能读取多少个byte、char等。
     * <p>
     * rewind：将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
     * 一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。
     * <p>
     * clear：如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer被清空了。
     * Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
     * 如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
     * <p>
     * compact：如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
     * compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
     */
    public static void testBufferUsage()
    {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("abcd".getBytes());
        System.out.println(new String(buffer.array()));

        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        //        System.out.println((char) buffer.get()); // 会因为position > limit抛出java.nio.BufferUnderflowException

        buffer.rewind();
        System.out.println("after rewind:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        buffer.clear();
        System.out.println("after clear:" + buffer);

        buffer.put("efgh".getBytes());
        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        buffer.compact();
        System.out.println("after compact:" + buffer);

        buffer.put("ijkl".getBytes());
        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
    }
}
