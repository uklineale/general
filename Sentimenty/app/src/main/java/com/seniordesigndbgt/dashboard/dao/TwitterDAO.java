package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.Twitter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomaspatterson on 2/14/16.
 */
@Repository
@Transactional
public class TwitterDAO {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Twitter twitter) {
        getSession().save(twitter);
    }


    public void delete(Twitter twitter) {
        getSession().delete(twitter);
    }

    @SuppressWarnings("unchecked")
    public List<Twitter> getAll() {
        return getSession().createQuery("from Twitter").list();
    }

    public void update(Twitter twitter){
        getSession().update(twitter);
    }

}
