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
			teamA.setName("teamB");
			em.persist(teamB);

			Member member1 = new Member();
			member1.setName("member1");
			member1.setTeam(teamA);
			em.persist(member1);

			Member member2 = new Member();
			member2.setName("member2");
			member2.setTeam(teamB);
			em.persist(member2);

			em.flush();
			em.clear();

			List<Member> members = em.createQuery("select m from Member m", Member.class)
				.getResultList();
			/*
			Hibernate:
			select m from Member m
			select
				member0_.MEMBER_ID as member_i1_4_,
				member0_.createBy as createby2_4_,
				member0_.createDate as createda3_4_,
				member0_.lastModifiedBy as lastmodi4_4_,
				member0_.lastModifiedDate as lastmodi5_4_,
				member0_.city as city6_4_,
				member0_.name as name7_4_,
				member0_.street as street8_4_,
				member0_.TEAM_ID as team_id10_4_,
				member0_.zipcode as zipcode9_4_
			from
				Member member0_
			 */
			/*
			Hibernate:
				select
					team0_.id as id1_7_0_,
					team0_.name as name2_7_0_
				from
					Team team0_
				where
					team0_.id=?
			 */
			/*
			Hibernate:
				select
					team0_.id as id1_7_0_,
					team0_.name as name2_7_0_
				from
					Team team0_
				where
					team0_.id=?
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
