package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.Module;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ModuleDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Module module) {
        getSession().save(module);
    }

    public void delete(Module module) {
        getSession().delete(module);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from Press").list();
    }

    public void update(Module module){
        getSession().update(module);
    }

}
