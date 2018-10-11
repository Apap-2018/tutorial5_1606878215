package com.apap.tutorial5.service;

import java.util.List;
import com.apap.tutorial5.model.FlightModel;

public interface FlightService {
	FlightModel getFlightDetailByFlightNumber(String flightNumber);
	List<FlightModel> getFlightList();
	void deleteFlight(long id);
	void addFlight(FlightModel flight);
	void saveFlight(FlightModel flight);
}
