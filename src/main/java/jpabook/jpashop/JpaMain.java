package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Child;
import jpabook.jpashop.domain.Parent;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Child child1 = new Child();
			Child child2 = new Child();

			Parent parent = new Parent();
			parent.addChild(child1);
			parent.addChild(child2);

			em.persist(parent);
			// em.persist(child1); // -> CascadeType.ALL로 생략 가능
			// em.persist(child2); // -> CascadeType.ALL로 생략 가능

			em.flush();
			em.clear();

			Parent findParent = em.find(Parent.class, parent.getId());

			// Child findChild1 = em.find(Child.class, findParent.getChildList().get(0).getId());
			// em.remove(findChild1); // -> orphanRemoval = true로 생략 가능

			// findParent.getChildList().remove(0); // ->  orphanRemoval = true로 사용 가능

			// Child findChild2 = em.find(Child.class, findParent.getChildList().get(1).getId());
			// em.remove(findChild2); // -> orphanRemoval = true

			// findParent.getChildList().remove(1); // ->  orphanRemoval = true로 사용 가능(연관관계의 주인이 아니어도 동작 함)

			em.remove(findParent);

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
