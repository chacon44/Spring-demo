package com.demo.service;

import com.demo.exceptions.ErrorCode;
import com.demo.exceptions.CustomizedException;
import com.demo.interfaces.IdManagement;
import org.springframework.stereotype.Service;

@Service("IdManagement")
public class IdManagementService implements IdManagement {
    private long currentId = 0;
    private static final long MAX_ID = 2;

    @Override
    public long incrementId() {
        if (currentId >= MAX_ID) {
            throw new CustomizedException("Maximum id value reached", ErrorCode.OUT_OF_IDS);
        }
        return ++currentId;
    }
}
