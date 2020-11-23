package ru.list.surkovr;

import localhost.rsurkov.JavaInfo;
import localhost.rsurkov.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class JavaInfoImpl implements JavaInfo {
    private final static Logger log = LoggerFactory.getLogger(JavaInfoImpl.class);

    public static final String OS_NAME_PROPERTY = "os.name";
    public static final String OS_VERSION_PROPERTY = "os.version";
    public static final String OS_ARCH_PROPERTY = "os.arch";
    public static final String JAVA_VENDOR_PROPERTY = "java.vendor";
    public static final String JAVA_VENDOR_URL_PROPERTY = "java.vendor.url";
    public static final String JAVA_VERSION_PROPERTY = "java.version";

    @Override
    public GetJavaInfoResponse getJavaInfo(GetJavaInfoRequest request) {
        String javaVendor = System.getProperty(JAVA_VENDOR_PROPERTY);
        String javaVendorUrl = System.getProperty(JAVA_VENDOR_URL_PROPERTY);
        String javaVersion = System.getProperty(JAVA_VERSION_PROPERTY);

        GetJavaInfoResponse response = new GetJavaInfoResponse();
        response.setJavaVendor(javaVendor);
        response.setJavaVendorUrl(javaVendorUrl);
        response.setJavaVersion(javaVersion);

        String message = "### In getJavaInfo get java.vendor: " + javaVendor + ", java.vendor.url: "
                + javaVendorUrl + ", java.version: " + javaVersion;
        log.info(message);
        return response;
    }

    @Override
    public int getAvailableProcessors(GetAvailableProcessorsRequest request) {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("### In getAvailableProcessors available processors: {}", availableProcessors);
        return availableProcessors;
    }

    @Override
    public GetGcInfoByNameResponse getGcInfoByName(String name) {
        log.info("### In getGcInfoByName starts looking for GC by name: {}", name);
        final List<HashMap<String, Object>> garbageCollectors = MemoryUtil.getGarbageCollectors();
        log.info("### In getGcInfoByName get garbageCollectors map size: {}",
                nonNull(garbageCollectors) ? garbageCollectors.size() : null);

        log.info("### In getGcInfoByName get garbageCollectors map size: {}",
                (nonNull(garbageCollectors) ? garbageCollectors.size() : null));
        log.info("### In getGcInfoByName get garbageCollectors: {}",
                garbageCollectors.stream()
                        .map(m -> String.valueOf(m.get("name")))
                        .collect(Collectors.joining(", ")));

        final HashMap<String, Object> found = garbageCollectors.stream()
                .filter(m -> m.get("name").equals(name))
                .findFirst().orElse(null);

        GetGcInfoByNameResponse response = new GetGcInfoByNameResponse();
        if (nonNull(found)) {
            response.setId((long) found.get("id"));
            response.setName((String) found.get("name"));
            response.setCollectionCount((long) found.get("collectionCount"));
            response.setCollectionTime((long) found.get("collectionTime"));
            response.setLastGcStartTime((long) found.get("lastGcStartTime"));
            response.setLastGcEndTime((long) found.get("lastGcEndTime"));
            response.setLastGcDuration((long) found.get("lastGcDuration"));
            response.setMemoryUsageBeforeGc((String) found.get("memoryUsageBeforeGc"));
            response.setMemoryUsageAfterGc((String) found.get("memoryUsageAfterGc"));
        }

        log.info("### In getGcInfoByName get GetGcInfoByNameResponse: {}", response);
        return response;
    }

    @Override
    public long getTotalMemory(GetTotalMemoryRequest request) {
        final long totalMemory = Runtime.getRuntime().totalMemory();
        log.info("### In getTotalMemory found 'totalmemory': {}", totalMemory);
        return totalMemory;
    }

    @Override
    public long getFreeMemory(GetFreeMemoryRequest request) {
        final long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("### In getFreeMemory found 'freememory': {}", freeMemory);
        return freeMemory;
    }

    @Override
    public GetOsInfoResponse getOsInfo(GetOsInfoRequest request) {
        String osName = System.getProperty(OS_NAME_PROPERTY);
        String osVersion = System.getProperty(OS_VERSION_PROPERTY);
        String osArch = System.getProperty(OS_ARCH_PROPERTY);

        GetOsInfoResponse response = new GetOsInfoResponse();
        response.setOsArch(osArch);
        response.setOsName(osName);
        response.setOsVersion(osVersion);

        String message = "### In getOsInfo get os.name: " + osName + ", os.version: "
                + osVersion + ", os.arch: " + osArch;
        log.info(message);
        return response;
    }

    @Override
    public GetGcNamesResponse getGcNames(GetGcNamesRequest request) {
        final List<HashMap<String, Object>> garbageCollectors = MemoryUtil.getGarbageCollectors();
        log.info("### In getGcNames get garbageCollectors map size: {}",
                nonNull(garbageCollectors) ? garbageCollectors.size() : null);
        List<String> gcNames = garbageCollectors.stream().map(m -> String.valueOf(m.get("name")))
                .collect(Collectors.toList());
        log.info("### In getGcNames found gcNames: {}", gcNames);

        GetGcNamesResponse response = new GetGcNamesResponse();
        response.getGcName().addAll(gcNames);
        return response;
    }
}
