package com.deusto.app.unitary.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.services.AdminService;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private PersistenceManager pm;

    @Mock
    private Transaction tx;

    @InjectMocks
    private AdminService adminService;


    @Test
    public void testAddBike_Success() {
        BicycleData bikeData = new BicycleData();
        bikeData.setId(1);
        bikeData.setType("Mountain");
        bikeData.setStationId(1);

        Station station = new Station();
        station.setId(1);
        
        boolean result = adminService.addBike(bikeData);

        verify(tx).commit();
        assertTrue(result);
    }

    
}
