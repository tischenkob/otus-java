package ru.otus.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);
    private HwListener<String, Integer> notifier = new HwListener<>() {
        @Override
        public void notify(String key, Integer value, String action) {
            logger.info("key:{}, value:{}, action: {}", key, value, action);
        }
    };

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HwCache<String, Integer> cache = new MyCache<>();
        cache.addListener(notifier);
        cache.put("1", 1);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        cache.removeListener(notifier);

        // TEST

        final int sizeBefore = 10;
        for (int i = 0; i < sizeBefore; i++) {
            cache.put(String.format("%d -> ", i), i);
        }

        System.gc();
        Thread.sleep(100);

        int sizeAfter = 0;
        for (int i = 0; i < sizeBefore; i++) {
            var value = cache.get(String.format("%d -> ", i));
            if (value != null) sizeAfter += 1;
        }

        logger.info("Before:{}\t After:{}", sizeBefore, sizeAfter);

    }
}
