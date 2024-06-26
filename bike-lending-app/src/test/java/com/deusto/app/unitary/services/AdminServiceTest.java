package com.deusto.app.unitary.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
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
    private PersistenceManagerFactory pmf;

    @Mock
    private PersistenceManager pm;

    @Mock
    private Transaction tx;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        when(pmf.getPersistenceManager()).thenReturn(pm);
        when(pm.currentTransaction()).thenReturn(tx);
    }

    @Test
    public void testAddBike_Success() {
        BicycleData bikeData = new BicycleData();
        bikeData.setType("Mountain");
        bikeData.setStationId(1);
        bikeData.setAcquisitionDate("a");

        Bicycle bike = new Bicycle();
        when(pm.getObjectById(Bicycle.class, bike.getId())).thenThrow(new JDOObjectNotFoundException());
        Station station = mock(Station.class);
        when(pm.getObjectById(Station.class, bikeData.getStationId())).thenReturn(station);
        when(station.getBikes()).thenReturn(new ArrayList<>());

        boolean result = adminService.addBike(bikeData);

        assertTrue(result);
        verify(pm).makePersistent(any(Bicycle.class));
        verify(pm).makePersistent(any(Station.class));
    }

    @Test
    public void testDeleteBike_Success() {
        Bicycle bike = mock(Bicycle.class);
        when(pm.getObjectById(Bicycle.class, 1)).thenReturn(bike);

        boolean result = adminService.deleteBike(1);

        verify(tx).begin();
        verify(pm).deletePersistent(bike);
        verify(tx).commit();
        assertTrue(result);
    }

    @Test
    public void testDeleteBike_NotFound() {
        doThrow(new JDOObjectNotFoundException()).when(pm).getObjectById(Bicycle.class, 1);

        boolean result = adminService.deleteBike(1);

        verify(tx).begin();
        verify(pm, never()).deletePersistent(any(Bicycle.class));
        verify(tx).commit();
        assertFalse(result);
    }

    @Test
    public void testDisableBike_Success() {
        Bicycle bike = mock(Bicycle.class);
        when(pm.getObjectById(Bicycle.class, 1)).thenReturn(bike);

        boolean result = adminService.disableBike(1);

        verify(bike).setAvailable(false);
        verify(pm).makePersistent(bike);
        verify(tx).commit();
        assertTrue(result);
    }

    @Test
    public void testAbleBike_Success() {
        Bicycle bike = mock(Bicycle.class);
        when(pm.getObjectById(Bicycle.class, 1)).thenReturn(bike);

        boolean result = adminService.ableBike(1);

        verify(bike).setAvailable(true);
        verify(pm).makePersistent(bike);
        verify(tx).commit();
        assertTrue(result);
    }
}
