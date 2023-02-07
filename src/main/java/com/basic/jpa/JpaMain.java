package com.basic.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			//영속
			Member member = em.find(Member.class, 150L);
			member.setName("AAAAA");

			em.detach(member); //ZZZZZ에서 AAAAA로 업데이트 되지 않음
			// em.clear(); // 영속성 컨텍스트 초기화
			// em.clear(); // 영속성 컨텍스트 종료

			Member member2 = em.find(Member.class, 150L);

			System.out.println("=====================");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.clear();
		}

		emf.close();
	}
}
