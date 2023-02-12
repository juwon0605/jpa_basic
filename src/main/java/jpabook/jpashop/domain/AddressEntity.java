package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
@Getter
@Setter
public class AddressEntity {

	@Id
	@GeneratedValue
	private Long id;

	public AddressEntity(String city, String street, String zipCode) {
		this.address = new Address(city, street, zipCode);
	}

	private Address address;
}
