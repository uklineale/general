package com.seniordesigndbgt.dashboard.dao;


import com.seniordesigndbgt.dashboard.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        getSession().save(user);
    }

    public void delete(User user) {
        getSession().delete(user);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from User").list();
    }

    public User getByUsername(String username) {
        return (User) getSession().createQuery(
                "from User where username = :username")
                .setParameter("username", username)
                .uniqueResult();
    }

    public User getById(long id) {
        return (User) getSession().load(User.class, id);
    }

    public void update(User user) {
        getSession().update(user);
    }

}