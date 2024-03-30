package safari.carrier.controller;

	import java.util.List;
	import java.util.Map;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.web.bind.annotation.RestController;
	import lombok.extern.slf4j.Slf4j;
	import safari.carrier.controller.model.LocationData;
	import safari.carrier.service.LocationService;
	import safari.carrier.controller.model.LocationData.LocationCustomer;
	import safari.carrier.controller.model.LocationData.LocationDriver;

	@RestController
	@RequestMapping("/location")
	@Slf4j
	public class LocationController {

	  @Autowired
	   private LocationService LocationService;
	  
	  @PostMapping("/location")
	  @ResponseStatus(code = HttpStatus.CREATED)
	  public LocationData findOrCreateLocation(@RequestBody LocationData locationData) {
	    log.info("Creating location {}", locationData);
	    return LocationService.saveLocation(locationData);
	  }
	  
	  @PutMapping("{locationId}")
	  public LocationData createLocation(@PathVariable Long LocationId,
	      @RequestBody LocationData locationData) {
	    locationData.setLocationId(LocationId);
	    log.info("Creating location {}", locationData);
	    return LocationService.saveLocation(locationData);
	  }  
	  
	  @PostMapping("{LocationId}/driver")
	  @ResponseStatus(code = HttpStatus.CREATED)
	  public LocationDriver addDriver(@PathVariable Long LocationId,
	      @RequestBody LocationDriver driverId) {
	    log.info("Creating driver for location {}", LocationId);
	    return LocationService.saveDriver(LocationId, driverId);
	  
	  }
	  @PostMapping("{LocationId}/customer")
	  @ResponseStatus(code = HttpStatus.CREATED)
	  public LocationCustomer insertLocationCustomer(@PathVariable Long LocationId,
	          @RequestBody LocationCustomer LocationCustomer) {
	      log.info("Creating a customer for location {}", LocationId);

	      return LocationService.saveCustomer(LocationId, LocationCustomer);
	    }
	  
	  @GetMapping("/location")
	    public List<LocationData> retrieveAllLocations() {
	    log.info("retrieving all location");
	    return LocationService.retrieveAllLocations();
	  }
	  @GetMapping("/location/{LocationId}")
	    public LocationData retrieveLocationById(@PathVariable Long LocationId) {
	    log.info("Retreiveing location with ID={}", LocationId);
	    return LocationService.retrieveLocationById(LocationId);
	  }

	@DeleteMapping("{LocationId}")
	    public Map<String, String> deleteLocationById(@PathVariable Long LocationId) {
	    log.info("Deleting location with id ={}", LocationId);
	    LocationService.deleteLocationById(LocationId);
	    
	    return Map.of("Message", "Deletion of Location with ID=" + LocationId + " was successful.");
	}


	}
	

