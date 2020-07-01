package com.yee.study.java.core.collection;

import com.yee.study.util.MapUtil;
import org.junit.Assert;

import java.util.WeakHashMap;

/**
 * WeakHashMap 使用示例
 *
 * @author Roger.Yi
 */
public class WeakHashMapSample {

    /**
     * Java 对引用的概念进行了扩充，将引用分为了：强引用、软引用、弱引用、虚引用4 种。而我们的WeakHashMap就是基于弱引用。
     * <p>
     * （1）强引用
     * 如果一个对象具有强引用，它就不会被垃圾回收器回收。即使当前内存空间不足，JVM也不会回收它，而是抛出 OutOfMemoryError 错误，使程序异常终止。
     * 比如String str = "hello"这时候str就是一个强引用。
     * <p>
     * （2）软引用
     * 内存足够的时候，软引用对象不会被回收，只有在内存不足时，系统则会回收软引用对象，如果回收了软引用对象之后仍然没有足够的内存，才会抛出内存溢出异常。
     * <p>
     * （3）弱引用
     * 如果一个对象具有弱引用，在垃圾回收时候，一旦发现弱引用对象，无论当前内存空间是否充足，都会将弱引用回收。
     * <p>
     * （4）虚引用
     * 如果一个对象具有虚引用，就相当于没有引用，在任何时候都有可能被回收。使用虚引用的目的就是为了得知对象被GC的时机，所以可以利用虚引用来进行销毁前的一些操作，比如说资源释放等。
     *
     * WeakHashMap是基于弱引用，其对象可能随时被回收，适用于缓存的场景
     * 不要使用基础类型作为WeakHashMap的key（基础类型比如Integer 会固定在一个大小，而不会收到gc的影响）
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        WeakHashMap<ImageName, Image> cache = new WeakHashMap<>();
        cache.put(new ImageName("image_001"), new Image("image_001", "image_001.com"));
        cache.put(new ImageName("image_002"), new Image("image_002", "image_002.com"));

        Assert.assertFalse(cache.isEmpty());
        Assert.assertEquals(2, cache.size());

        System.gc(); // 回收过后，cache对象中的2个数据都被清除了
        Thread.sleep(10000);

        Assert.assertFalse(cache.isEmpty());
    }

    static class ImageName {

        private String name;

        public ImageName() {
        }

        public ImageName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class Image {

        private String name;

        private String url;

        public Image() {
        }

        public Image(String name, String url) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
