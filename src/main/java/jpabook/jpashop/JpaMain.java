package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberType;
import jpabook.jpashop.domain.Team;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			// member.setName(null);
			member.setName("관리자");
			member.setAge(29);
			member.setType(MemberType.ADMIN);
			em.persist(member);

			em.flush();
			em.clear();

			String query1 =
				"	 select " +
					"	case when m.age <= 19 then '학생요금' " +
					"		 when m.age >= 60 then '경로요금' " +
					"		 else '일반요금'" +
					"	end " +
					"from Member m";
			// s = 일반요금

			String query2 = "select coalesce(m.name, '이름 없는 회원') from Member m";
			// s = 이름 없는 회원

			String query3 = "select nullif(m.name, '관리자') from Member m";
			// s = 관리자2

			List<String> result1 = em.createQuery(query3, String.class)
				.getResultList();

			for (String s : result1) {
				System.out.println("s = " + s);
			}

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
