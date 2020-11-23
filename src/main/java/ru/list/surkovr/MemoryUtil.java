package ru.list.surkovr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.util.Objects.isNull;

public class MemoryUtil {

    // Для получения информации по конкретному сборщику мусора используются моковые данные,
    // поскольку GcInfo используемый ранее, входит в пакет com.sun.management.
    // Однако не получается чтобы нужный пакет - com.sun.management добавился в бандл
    private static final Logger log = LoggerFactory.getLogger(MemoryUtil.class);

    public static List<HashMap<String, Object>> getGarbageCollectors() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            list.add(getGarbageCollector(bean));
        }
        log.info("In getGarbageCollectors found: {}", list);
        return list;
    }

    private static HashMap<String, Object> getGarbageCollector(GarbageCollectorMXBean bean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isNull(bean)) return hashMap;
        hashMap.put("name", bean.getName());
        hashMap.put("collectionCount", bean.getCollectionCount());
        hashMap.put("collectionTime", bean.getCollectionTime());

        // Моковые данные
        final Integer nextInt = Math.abs(new Random().nextInt(10 * 1000 * 60));
        final int mockMem = 256000000;
        hashMap.put("lastGcStartTime", System.currentTimeMillis() - nextInt.longValue());
        hashMap.put("lastGcEndTime", System.currentTimeMillis());
        hashMap.put("lastGcDuration", nextInt.longValue());
        hashMap.put("memoryUsageBeforeGc", "memoryUsageBeforeGc:"
                + Integer.toUnsignedLong(Math.abs(new Random().nextInt(mockMem))));
        hashMap.put("memoryUsageAfterGc", "memoryUsageAfterGc:"
                + Integer.toUnsignedLong(Math.abs(new Random().nextInt(mockMem))));
        hashMap.put("id", new Random().nextLong());

        log.info("In getGarbageCollector found: {}, by bean: {}", hashMap, bean.getName());
        return hashMap;
    }
}
