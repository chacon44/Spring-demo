package com.demo.service;

import com.demo.exceptions.CustomizedException;
import com.demo.exceptions.ErrorCode;
import com.demo.interfaces.IdManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service("IdManagement")
public class IdManagementService implements IdManagement {
    private final AtomicLong currentId = new AtomicLong();
    private final long maxId;

    public IdManagementService(@Value("${maxId:100}") long maxId) { this.maxId = maxId; }


    @Override
    public long incrementId() {
        if (currentId.get() >= maxId) {
            throw new CustomizedException("Maximum id value reached", ErrorCode.OUT_OF_IDS);
        }
        return currentId.incrementAndGet();
    }
}
