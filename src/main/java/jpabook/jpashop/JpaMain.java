package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.AddressEntity;
import jpabook.jpashop.domain.Member;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Member member = new Member();
			member.setName("member");
			member.setAddress(new Address("homeCity", "street", "10000"));

			member.getFavoriteFoods().add("치킨");
			member.getFavoriteFoods().add("족발");
			member.getFavoriteFoods().add("피자");

			member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
			member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

			em.persist(member);
			//영속성 전의(Cascade) + 고아 객체 제거 기능으로 인해 별도로 persist하거나 관리할 필요가 없다

			em.flush();
			em.clear();

			System.out.println("========================");
			Member findMember = em.find(Member.class, member.getId());
			System.out.println("========================");
			// @OneToMany는 디폴트로 지연 로딩된다

			// 일대다 엔티티 수정

			// 치킨 -> 한식
			findMember.getFavoriteFoods().remove("치킨");
			findMember.getFavoriteFoods().add("한식");
			/*
			Hibernate:
			delete collection row jpabook.jpashop.domain.Member.favoriteFoods
				delete
				from
					FAVORITE_FOOD
				where
					MEMBER_ID=?
					and FOOD_NAME=?
			 */
			/*
			Hibernate:
			insert collection row jpabook.jpashop.domain.Member.favoriteFoods
				insert
				into
					FAVORITE_FOOD
					(MEMBER_ID, FOOD_NAME)
				values
					(?, ?)
			 */

			// old1 -> newCity
			findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000"));
			findMember.getAddressHistory().add(new AddressEntity("newCity", "street", "10000"));
			/*
			Hibernate:
			insert jpabook.jpashop.domain.AddressEntity
				insert
				into
				ADDRESS
					(city, street, zipcode, id)
				values
					(?, ?, ?, ?)
			 */
			/*
			Hibernate:
			create one-to-many row jpabook.jpashop.domain.Member.addressHistory
				update
					ADDRESS
				set
					MEMBER_ID=?
				where
					id=?
			 */

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.clear();
		}

		emf.close();
	}
}
