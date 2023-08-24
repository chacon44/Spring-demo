package com.demo.service;

import com.demo.interfaces.IdManagement;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
@Service("IdManagement")
public class IdManagementService implements IdManagement {
    AtomicLong id = new AtomicLong(0);
    @Override
    public long getNewId() {return id.getAndIncrement(); }
}
