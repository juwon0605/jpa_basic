package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
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
			member.setName("teamA");
			member.setAge(29);
			member.setType(MemberType.ADMIN);
			member.setTeam(team);

			em.persist(member);

			em.flush();
			em.clear();

			String query1 =
				"select m.name, 'HELLO', true " +
					"from Member m " +
					"where m.type = :userType and m.age between 20 and 29";

			List<Object[]> result1 = em.createQuery(query1)
				.setParameter("userType", MemberType.ADMIN)
				.getResultList();

			for (Object[] objects : result1) {
				for (Object object : objects) {
					System.out.println("object = " + object);
				}
			}
			// object = teamA
			// object = HELLO
			// object = true

			Book book = new Book();
			book.setName("JPA");
			book.setAuthor("김영한");
			em.persist(book);

			String query2 = "select i from Item i where type(i) = Book";

			List<Item> result2 = em.createQuery(query2, Item.class)
				.getResultList();

			for (Item item : result2) {
				System.out.println("item.getName() = " + item.getName());
			}
			// item.getName() = JPA

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
