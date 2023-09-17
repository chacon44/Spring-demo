package com.demo.service;

import com.demo.exceptions.CustomizedException;
import com.demo.exceptions.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdManagementServiceTest {
    private IdManagementService idManagementService;

    @BeforeEach
    public void setUp() {
        idManagementService = new IdManagementService();
    }

    @Test
    public void testIncrementId() {

        //make some increments to check if it works, 5 at maximum
        long testValue = Math.min(IdManagementService.getMaxId(), 5);
        for (int i = 1; i < testValue; i++)
            assertEquals(i, idManagementService.incrementId());
    }

    @Test
    public void testIncrementIdThrowsException() {
        for (int i = 0; i < IdManagementService.getMaxId(); i++)
            idManagementService.incrementId();


        CustomizedException exception = assertThrows(CustomizedException.class, () -> idManagementService.incrementId());

        assertEquals("Maximum id value reached", exception.getMessage());
        assertEquals(ErrorCode.OUT_OF_IDS, exception.getCode());
    }
}