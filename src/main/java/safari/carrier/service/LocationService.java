package safari.carrier.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import safari.carrier.controller.model.LocationData;
import safari.carrier.controller.model.LocationData.LocationCustomer;
import safari.carrier.controller.model.LocationData.LocationDriver;
import safari.carrier.dao.CustomerDao;
import safari.carrier.dao.DriverDao;
import safari.carrier.dao.LocationDao;
import safari.carrier.entity.Customer;
import safari.carrier.entity.Driver;
import safari.carrier.entity.Location;

@Service
public class LocationService {

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private DriverDao driverDao;

	@Autowired
	private CustomerDao customerDao;

	public LocationData saveLocation(LocationData LocationData) {

		Location Location = findOrCreateLocation(LocationData.getLocationId());
		copyLocationFields(Location, LocationData);

		Location dbLocation = locationDao.save(Location);
		return new LocationData(dbLocation);
	}

	private void copyLocationFields(Location Location, LocationData LocationData) {
		Location.setLocationId(LocationData.getLocationId());
		Location.setLocationName(LocationData.getLocationName());
		Location.setLocationAddress(LocationData.getLocationAddress());
		Location.setLocationCity(LocationData.getLocationCity());
		Location.setLocationState(LocationData.getLocationState());
		Location.setLocationZip(LocationData.getLocationZip());
		Location.setLocationPhone(LocationData.getLocationPhone());
	}

	private Location findOrCreateLocation(Long LocationId) {
		Location Location;

		if (Objects.isNull(LocationId)) {
			Location = new Location();
		} else {
			Location = findLocationById(LocationId);
		}

		return Location;
	}

	private Location findLocationById(Long LocationId) {
		return locationDao.findById(LocationId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + LocationId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public LocationData retrieveLocationById(Long LocationId) {
		return new LocationData(findLocationById(LocationId));
	}

	private Driver findDriverById(Long locationId, Long driverId) {
		Driver dbDriver = driverDao.findById(driverId)
				.orElseThrow(() -> new NoSuchElementException("Driver with ID=" + driverId + " does not exist."));

		if (dbDriver.getLocation().getLocationId() != locationId) {
			throw new IllegalArgumentException(
					"Driver with ID=" + driverId + " is not an Driver at pet store with ID=" + locationId + ".");
		} else {
			return dbDriver;
		}
	}

	private Driver findOrCreateDriver(Long driverId, Long locationId) {
		Driver Driver;

		if (Objects.isNull(driverId)) {
			Driver = new Driver();
		} else {
			Driver = findDriverById(locationId, driverId);
		}
		return Driver;
	}

	private void copyDriverFields(Driver Driver, LocationDriver LocationDriver) {
		Driver.setDriverFirstName(LocationDriver.getDriverFirstName());
		Driver.setDriverLastName(LocationDriver.getDriverLastName());
		Driver.setDriverEndorsements(LocationDriver.getDriverEndorsements());
		Driver.setDriverPhone(LocationDriver.getDriverPhone());
		Driver.setDriverId(LocationDriver.getDriverId());
	}

	private void copyCustomerFields(Customer customer, LocationCustomer LocationCustomer) {
		customer.setCustomerFirstName(LocationCustomer.getCustomerFirstName());
		customer.setCustomerLastName(LocationCustomer.getCustomerLastName());
		customer.setCustomerEmail(LocationCustomer.getCustomerEmail());
		customer.setCustomerId(LocationCustomer.getCustomerId());
	}
	@Transactional(readOnly = false)
	public LocationDriver saveDriver(Long LocationId, LocationDriver LocationDriver) {
		Location Location = findLocationById(LocationId);

		Driver Driver = findOrCreateDriver(LocationDriver.getDriverId(), LocationId);
		copyDriverFields(Driver, LocationDriver);
		Driver.setLocation(Location);
		Location.getDrivers().add(Driver);
		Driver dbDriver = driverDao.save(Driver);
		return new LocationDriver(dbDriver);
	}

	private Customer findCustomerById(Long customerId, Long LocationId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));
		boolean found = false;
		for (Location Location : customer.getLocations()) {
			if (Location.getLocationId() == (LocationId)) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Pet Store with ID=" + LocationId + " not found for the Customer with ID=" + customerId);
		}
		return customer;
	}

	private Customer findOrCreateCustomer(Long customerId, Long LocationId) {
		Customer customer;

		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(customerId, LocationId);
		}
		return customer;
	}

	@Transactional(readOnly = false)
	public LocationCustomer saveCustomer(Long LocationId, LocationCustomer LocationCustomer) {
		Location Location = findLocationById(LocationId);
		Long customerId = LocationCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, LocationId);
		copyCustomerFields(customer, LocationCustomer);
		Location.getCustomers().add(customer);
		customer.getLocations().add(Location);
		Customer dbCustomer = customerDao.save(customer);
		return new LocationCustomer(dbCustomer);
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllLocations() {
		List<Location> Locations = locationDao.findAll();
		List<LocationData> result = new LinkedList<>();

		for (Location Location : Locations) {
			LocationData psd = new LocationData(Location);

			psd.getCustomers().clear();
			psd.getDrivers().clear();

			result.add(psd);
		}
		return result;
	}

	public LocationData returnLocationById(Long LocationId) {
		Location Location = findLocationById(LocationId);
		return new LocationData(Location);
	}

	public void deleteLocationById(Long LocationId) {
		Location Location = findLocationById(LocationId);
		locationDao.delete(Location);
	}

}