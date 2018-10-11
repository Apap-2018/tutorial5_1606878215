package com.apap.tutorial5.controller;
import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * FlightController
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		model.addAttribute("flight", flight);
		model.addAttribute("addFlight", true);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("addFlight", true);
		return "add";
	}
	
	@RequestMapping("/flight/view")
	public String view(@RequestParam("flightNumber") String flightNumber, Model model) {
		try {
			FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber);
			model.addAttribute("flight", archive);
			model.addAttribute("viewFlight", true);
			return "view-flight";
		} catch (NullPointerException e) {
			return "fragments/error-400";
		}
	}
	
	@RequestMapping("/flight/viewall")
	public String viewall(Model model) {
		List<FlightModel> flightList = flightService.getFlightList();
		model.addAttribute("flightList", flightList);
		model.addAttribute("daftarFlight", true);
		return "list-flight";
	}
	
	@RequestMapping(value = "/flight/delete{licenseNumber}", method = RequestMethod.POST)
	private String deleteFlight(@PathVariable(value = "licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot, Model model, RedirectAttributes rm) {
		if (pilot.getPilotFlight().size() == 0) {
			rm.addFlashAttribute("noneSelected", true);
			return "redirect:/pilot/view?licenseNumber=" + licenseNumber;
		}
		for (FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight.getId());			
		}
		return "delete";
	}
	
	@RequestMapping("/flight/update")
	public String updatePilotSubmit(@RequestParam("newFlightNumber") String newFlightNumber, @RequestParam("newOrigin") String newOrigin, 
			@RequestParam("newDestination") String newDestination, @RequestParam("newTime") Date newTime, Model model) {
		FlightModel archive = flightService.getFlightDetailByFlightNumber(newFlightNumber);
		archive.setFlightNumber(newFlightNumber);
		archive.setOrigin(newOrigin);
		archive.setDestination(newDestination);
		archive.setTime(newTime);
		flightService.saveFlight(archive);
		model.addAttribute("updateFlight", true);
		return "update";
	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		model.addAttribute("flightNumber", flightNumber);
		model.addAttribute("updateFlight", true);
		return "updateFlight";
	}
	
}
