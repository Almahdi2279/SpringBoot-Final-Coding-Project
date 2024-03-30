package safari.carrier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import safari.carrier.entity.Driver;

public interface DriverDao extends JpaRepository<Driver, Long> {

}
