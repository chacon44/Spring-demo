package com.demo.service;

import com.demo.exceptions.ErrorCodeEnums;
import com.demo.exceptions.OutOfIdException;
import com.demo.interfaces.IdManagement;
import org.springframework.stereotype.Service;

@Service("IdManagement")
public class IdManagementService implements IdManagement {
    private long currentId = 0;
    private static final long MAX_ID = 2;

    @Override
    public long incrementId() {
        if (currentId >= MAX_ID) {
            throw new OutOfIdException("Maximum id value reached", ErrorCodeEnums.OUT_OF_IDS);
        }
        return ++currentId;
    }
}
