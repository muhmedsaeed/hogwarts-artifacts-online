package com.mosa.hogwartsartifactsonline.utils;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    private final AtomicInteger counter = new AtomicInteger(3);

    public int generateId() {
        return counter.incrementAndGet();
    }
}

