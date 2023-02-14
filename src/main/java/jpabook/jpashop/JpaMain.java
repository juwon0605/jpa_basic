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
			Team teamA = new Team();
			teamA.setName("teamA");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("teamB");
			em.persist(teamB);

			Member member1 = new Member();
			member1.setName("회원1");
			member1.changeTeam(teamA);
			em.persist(member1);

			Member member2 = new Member();
			member2.setName("회원2");
			member2.changeTeam(teamA);
			em.persist(member2);

			Member member3 = new Member();
			member3.setName("회원3");
			member3.changeTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			String query1 = "select t from Team t join t.members m";

			List<Team> result1 = em.createQuery(query1, Team.class)
				.getResultList();

			for (Team t : result1) {
				System.out.println("team= " + t.getName() + " | member= " + t.getMembers().size());
				for (Member member : t.getMembers()) {
					System.out.println("-> member = " + member);
				}
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
