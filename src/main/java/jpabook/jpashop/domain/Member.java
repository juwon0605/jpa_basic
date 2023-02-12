package jpabook.jpashop.domain;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	@Embedded
	Address address;

	@ElementCollection
	@CollectionTable(
		name = "FAVORITE_FOOD",
		joinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	@Column(name = "FOOD_NAME")
	private Set<String> favoriteFoods = new HashSet<>();

	// @ElementCollection
	// @CollectionTable(
	// 	name = "ADDRESSS",
	// 	joinColumns = @JoinColumn(name = "MEMBER_ID")
	// )
	// private List<Address> addressHistory = new ArrayList<>();

	@OneToMany(cascade = ALL, orphanRemoval = true)
	@JoinColumn(name = "MEMBER_ID")
	private List<AddressEntity> addressHistory = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
}
