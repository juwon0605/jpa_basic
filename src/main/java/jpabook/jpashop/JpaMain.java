package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Address address = new Address("city", "street", "10000");

			Member member1 = new Member();
			member1.setName("member1");
			member1.setAddress(address);

			Member member2 = new Member();
			member2.setName("member2");
			member2.setAddress(address);

			Member member3 = new Member();
			member3.setName("member3");
			member3.setAddress(new Address(address.getCity(), address.getStreet(), address.getZipcode()));

			member1.getAddress().setCity("newCity");

			System.out.println("member1.getAddress().getCity() = " + member1.getAddress().getCity());
			// member1.getAddress().getCity() = newCity

			System.out.println("member2.getAddress().getCity() = " + member2.getAddress().getCity());
			// member2.getAddress().getCity() = newCity

			System.out.println("member3.getAddress().getCity() = " + member3.getAddress().getCity());
			// member3.getAddress().getCity() = city

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
