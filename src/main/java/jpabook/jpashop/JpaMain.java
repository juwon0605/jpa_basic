package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberDTO;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Member member = new Member();
			member.setName("member");
			member.setAge(29);
			em.persist(member);

			//
			List resultList1 = em.createQuery("select m.name, m.age from Member m")
				.getResultList();

			Object o = resultList1.get(0);
			Object[] result1 = (Object[])o;
			System.out.println("username = " + result1[0]); // username = member
			System.out.println("age = " + result1[1]); // age = 29

			//
			List<Object[]> resultList2 = em.createQuery("select m.name, m.age from Member m")
				.getResultList();

			Object[] result2 = (Object[])o;
			System.out.println("username = " + result2[0]); // username = member
			System.out.println("age = " + result2[1]); // age = 29

			//
			List<MemberDTO> resultList3 = em.createQuery(
					"select new jpabook.jpashop.dto.MemberDTO(m.name, m.age) from Member m",
					MemberDTO.class)
				.getResultList();

			MemberDTO memberDTO = resultList3.get(0);
			System.out.println("username = " + memberDTO.getName()); // username = member
			System.out.println("age = " + memberDTO.getAge()); // age = 29

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
