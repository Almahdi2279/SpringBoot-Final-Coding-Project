package safari.carrier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import safari.carrier.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {


}
