package com.winju.learning.jpa.JPAsample.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.winju.learning.jpa.JPAsample.entity.User;

@Repository
@Transactional
public class UserDAOService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long insert(User user) { 
		entityManager.persist(user);//User is stored in DB and is now trancked by JPA as it also comes
									//it's persistanceContext
		return user.getId();
	}
}
