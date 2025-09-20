package com.devoops.client;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class OdsayApiKeys {

    private final List<String> apiKeys;
    private final AtomicInteger counter = new AtomicInteger(0);

    public OdsayApiKeys(String[] apiKeys) {
        this.apiKeys = Stream.of(apiKeys)
                .map(key -> URLEncoder.encode(key, StandardCharsets.UTF_8))
                .toList();
    }

    public String getNextKey() {
        return apiKeys.get(counter.getAndIncrement() % apiKeys.size());
    }
}
