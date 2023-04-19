package com.kellot.student_management_api.repository;

import com.kellot.student_management_api.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepository  {
    @PersistenceContext
    EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> findById(long id){
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public List<User> findAll(){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("find_all_users", User.class);
        return namedQuery.getResultList();
    }

    public User save(User user){
        return entityManager.merge(user);
    }

    public User update(User user){
       return entityManager.merge(user);
    }

    public void deleteById(long id){
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
    }

    public void deleteAll(){
        for(User user: findAll()){
            entityManager.remove(user);
        }
    }
}