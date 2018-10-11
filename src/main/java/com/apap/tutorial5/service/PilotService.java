package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.PilotModel;

/**
 * PilotService
 */
public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	List<PilotModel> getPilotList();
	void addPilot(PilotModel pilot);
	void deletePilot(long id);
}
