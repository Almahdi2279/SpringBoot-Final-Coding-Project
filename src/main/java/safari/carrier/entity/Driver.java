package safari.carrier.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity 
@Data 
public class Driver {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long driverId;
	private String driverFirstName;
	private String driverLastName;
	private String driverPhone;
	private String driverEndorsements;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private Location location;
}
