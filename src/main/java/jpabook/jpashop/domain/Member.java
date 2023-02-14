package jpabook.jpashop.domain;

import static javax.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	private int age;

	@Enumerated(EnumType.STRING)
	private MemberType type;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			'}';
	}
}
