package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Team team = new Team();
			team.setName("team");
			em.persist(team);

			Member member1 = new Member();
			member1.setName("관리자1");
			member1.changeTeam(team);
			em.persist(member1);

			Member member2 = new Member();
			member2.setName("관리자2");
			member2.changeTeam(team);
			em.persist(member2);

			em.flush();
			em.clear();

			String query1 = "select 'a' || 'b' from Member m";
			String query2 = "select concat('a', 'b') from Member m";
			// s = ab
			// s = ab

			String query3 = "select substring(m.name, 0, 2) from Member m";
			// s = 관리
			// s = 관리

			String query4 = "select locate('de', 'abcdefg') from Member m";
			// s = 4
			// s = 4

			String query5 = "select size(t.members) From Team t";
			// s = 2

			String query6 = "select index(t.members) From Team t";
			// s = 0

			// 사용자 정의 함수 호출
			String query7 = "select group_concat(m.name) From Member m";
			// s = 관리자1,관리자2

			List<String> result1 = em.createQuery(query7, String.class)
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
