package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Member;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			for (int i = 0; i < 100; ++i) {
				Member member = new Member();
				member.setName("member" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();

			List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
				.setFirstResult(1)
				.setMaxResults(10)
				.getResultList();

			System.out.println("result.size() = " + result.size());
			// result.size() = 10

			for (Member member : result) {
				System.out.println("member = " + member);
			}
			/*
			member = Member{id=99, name='member98', age=98}
			member = Member{id=98, name='member97', age=97}
			member = Member{id=97, name='member96', age=96}
			member = Member{id=96, name='member95', age=95}
			member = Member{id=95, name='member94', age=94}
			member = Member{id=94, name='member93', age=93}
			member = Member{id=93, name='member92', age=92}
			member = Member{id=92, name='member91', age=91}
			member = Member{id=91, name='member90', age=90}
			member = Member{id=90, name='member89', age=89}
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
