package safari.carrier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import safari.carrier.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {
	
}