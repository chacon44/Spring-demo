package com.demo.service;

import com.demo.exceptions.ErrorCode;
import com.demo.exceptions.CustomizedException;
import com.demo.interfaces.IdManagement;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service("IdManagement")
public class IdManagementService implements IdManagement {
    private final AtomicLong currentId = new AtomicLong();
    private static final long MAX_ID = 1;

    public static long getMaxId(){
        return MAX_ID;
    }
    @Override
    public long incrementId() {
        if (currentId.get() >= MAX_ID) {
            throw new CustomizedException("Maximum id value reached", ErrorCode.OUT_OF_IDS);
        }
        return currentId.incrementAndGet();
    }
}
