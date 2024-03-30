package safari.carrier.entity;
import java.util.HashSet; 
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity 
@Data  
public class Location {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long locationId;
	private String locationName;
	private String locationAddress;
	private String locationCity;
	private String locationState;
	private String locationZip;
	private String locationPhone;
	
	@EqualsAndHashCode.Exclude 
	@ToString.Exclude 
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "location_customer", joinColumns = @JoinColumn(name = "location_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Driver> drivers = new HashSet<>();
}

