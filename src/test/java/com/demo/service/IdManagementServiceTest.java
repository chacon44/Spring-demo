package com.demo.service;

import com.demo.exceptions.CustomizedException;
import com.demo.exceptions.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdManagementServiceTest {

    public long maxId = 5L;
    IdManagementService idManagementService;
    @BeforeEach
    public void setUp() {
        idManagementService = new IdManagementService(maxId);
    }
    @Test
    public void testIncrementId() {
        for (int i = 1; i < maxId; i++)
            assertEquals(i, idManagementService.incrementId());
    }

    @Test
    public void testIncrementIdMaxIsReached() {

        for (int i = 0; i < maxId; i++)
            idManagementService.incrementId();


        CustomizedException exception = assertThrows(CustomizedException.class, idManagementService::incrementId);

        assertEquals("Maximum id value reached", exception.getMessage());
        assertEquals(ErrorCode.OUT_OF_IDS, exception.getCode());
    }
}