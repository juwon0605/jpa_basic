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

			//1.경로 표현식 - 상태 필드

			String query1 = "select m.name from Member m";
			// s = 관리자1
			// s = 관리자2

			//2.경로 표현식 - 단일 값 연관 경로

			// 묵시적 내부 조인(inner join) 발생
			String query2 = "select m.team.name from Member m";
			// s = team
			// s = team
			/*
			Hibernate:
			select
				team1_.name as col_0_0_
			from
				Member member0_ cross
			join
				Team team1_
			where
				member0_.TEAM_ID=team1_.id
			 */

			// join 튜닝 등의 이유로 파악하기 쉽게 명시적으로 표현
			String query3 = "select t.name from Member m join m.team t";
			// s = team
			// s = team
			/*
			Hibernate:
			select
				team1_.name as col_0_0_
			from
				Member member0_
			inner join
				Team team1_
			on
				member0_.TEAM_ID=team1_.id
			*/

			//3.경로 표현식 - 컬렉션 값 연관 경로

			// 컬렉션 자체를 가리키기 때문에 탐색이 안 됨.
			// 읽기나 사이즈 정도만 사용 가능
			String query4 = "select t.members from Team t";
			// s = Member{id=2, name='관리자1', age=0}
			// s = Member{id=3, name='관리자2', age=0}
			/*
			Hibernate:
			select
				members1_.MEMBER_ID as member_i1_6_,
				members1_.city as city6_6_,
				members1_.street as street7_6_,
				members1_.zipcode as zipcode8_6_,
				members1_.age as age9_6_,
				members1_.name as name10_6_,
				members1_.TEAM_ID as team_id12_6_,
				members1_.type as type11_6_
			from
				Team team0_
			inner join
				Member members1_
			on
				team0_.id=members1_.TEAM_ID
			*/

			String query5 = "select t.members.size from Team t";
			// s = 2
			/*
			Hibernate:
			select
				(select
					count(members1_.TEAM_ID)
				 from
					Member members1_
				 where
					team0_.id=members1_.TEAM_ID) as col_0_0_
			from
				Team team0_
			 */

			// 명시적 join으로 별칭을 줘서 값을 탐색할 수 있다
			String query6 = "select m.name from Team t join t.members m";
			// s = 관리자1
			// s = 관리자2
			/*
			Hibernate:
			select
				members1_.name as col_0_0_
			from
				Team team0_
			inner join
				Member members1_
			on
				team0_.id=members1_.TEAM_ID
			*/

			List<String> result1 = em.createQuery(query6, String.class)
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
