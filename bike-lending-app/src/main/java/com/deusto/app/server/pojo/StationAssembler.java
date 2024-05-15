package com.deusto.app.server.pojo;

import java.util.ArrayList;
import java.util.List;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Station;

public class StationAssembler {
	private static StationAssembler instance;

	private StationAssembler() { }
	
	public static StationAssembler getInstance() {
		if (instance == null) {
			instance = new StationAssembler();
		}
		
		return instance;
	}

	public StationData stationToPOJO(Station station) {
		StationData stationP = new StationData();		
		
		
		stationP.setId(station.getId());
		stationP.setLocation(station.getLocation());
		List<Bicycle> bikes= station.getBikes();
		List<Integer> ids=new ArrayList<Integer>();
		for (Bicycle bike : bikes) {
			ids.add(bike.getId());
		}
		stationP.setBikeIds(ids);
		
		
		return stationP;
	}

	public List<StationData> stationsToPOJO(List<Station> stations) {		
		List<StationData> stationPs = new ArrayList<>();
		
		for (Station station : stations) {
			stationPs.add(this.stationToPOJO(station));
		}
		
		return stationPs;
	}
}
