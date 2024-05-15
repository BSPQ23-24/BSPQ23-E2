package com.deusto.app.server.pojo;

import java.util.ArrayList;
import java.util.List;

import com.deusto.app.server.data.domain.Bicycle;

public class BicycleAssembler {
	private static BicycleAssembler instance;

	private BicycleAssembler() { }
	
	public static BicycleAssembler getInstance() {
		if (instance == null) {
			instance = new BicycleAssembler();
		}
		
		return instance;
	}

	public BicycleData bikeToPOJO(Bicycle bike) {
		BicycleData bikeP = new BicycleData();		
		
		
		bikeP.setId(bike.getId());
		bikeP.setAcquisitionDate(bike.getAcquisitionDate());
		bikeP.setType(bike.getType());
		bikeP.setStationId(bike.getStation().getId());
		bikeP.setAvailable(bike.isAvailable());
		
		return bikeP;
	}

	public List<BicycleData> bikesToPOJO(List<Bicycle> bikes) {		
		List<BicycleData> bikePs = new ArrayList<>();
		
		for (Bicycle bike : bikes) {
			bikePs.add(this.bikeToPOJO(bike));
		}
		
		return bikePs;
	}
}
