package safari.carrier.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import safari.carrier.entity.Customer;
import safari.carrier.entity.Location;
import safari.carrier.entity.Driver;
@Data
@NoArgsConstructor
public class LocationData {
	
	private Long LocationId;
	private String LocationName;
	private String LocationAddress;
	private String LocationCity;
	private String LocationState;
	private String LocationZip;
	private String LocationPhone;

	private Set<LocationCustomer> customers = new HashSet<>();
	private Set<LocationDriver> drivers = new HashSet<>();

	public LocationData(Location Location) {
		LocationId = Location.getLocationId();
		LocationName = Location.getLocationName();
		LocationAddress = Location.getLocationAddress();
		LocationCity = Location.getLocationCity();
		LocationState = Location.getLocationState();
		LocationZip = Location.getLocationZip();
		LocationPhone = Location.getLocationPhone();

		for (Customer customer : Location.getCustomers()) {
			customers.add(new LocationCustomer(customer));
		}

		for (Driver driver : Location.getDrivers()) {
			drivers.add(new LocationDriver(driver));
		}
	}

	@Data
	@NoArgsConstructor
	public static class LocationCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;

		public LocationCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();

		}
	}

	@Data
	@NoArgsConstructor
	public static class LocationDriver {
		private Long driverId;
		private String driverFirstName;
		private String driverLastName;
		private String driverPhone;
		private String driverEndorsements;

		public LocationDriver(Driver driver) {
			driverId = driver.getDriverId();
			driverFirstName = driver.getDriverFirstName();
			driverLastName = driver.getDriverLastName();
			driverPhone = driver.getDriverPhone();
			driverEndorsements = driver.getDriverEndorsements();
			
		}
	}

}
