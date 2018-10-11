package com.apap.tutorial5.controller;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("home", true);
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("addPilot", true);
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("addPilot", true);
		return "add";
	}
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		try {
			PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);	
			model.addAttribute("pilot", pilot);
			model.addAttribute("flightList", pilot.getPilotFlight());
			model.addAttribute("viewPilot", true);
			return "view-pilot";
		} catch (NullPointerException e) {
			return "fragments/error-404";
		}
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> pilotList = pilotService.getPilotList();
		model.addAttribute("pilotList", pilotList);
		model.addAttribute("daftarPilot", true);
		return "list-pilot";
	}
	
	@RequestMapping(value = "/pilot/delete/{id}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "id") long id, Model model) {
		pilotService.deletePilot(id);
		model.addAttribute("deletePilot", true);
		return "delete";
	}
	
	@RequestMapping("/pilot/update")
	public String updatePilotSubmit(@RequestParam("licenseNumber") String licenseNumber, @RequestParam("newName") String newName, @RequestParam("newFlyHour") int newFlyHour, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);	
		archive.setName(newName);
		archive.setFlyHour(newFlyHour);
		pilotService.addPilot(archive);
		model.addAttribute("updatePilot", true);
		return "update";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		model.addAttribute("licenseNumber", licenseNumber);
		model.addAttribute("updatePilot", true);
		return "updatePilot";
	}
}
