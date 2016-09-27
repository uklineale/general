package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.User;
import com.seniordesigndbgt.dashboard.model.View;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ViewDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(View view) {
        getSession().save(view);
    }

    public void delete(View view) {
        getSession().delete(view);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from Views").list();
    }

    public List getByUser(User user) {
        return  getSession().createQuery(
                "from Views view join Users usr on usr.id = view.id ")
                .list();
    }

    public View getByViewName(String view) {
        return (View) getSession().createQuery(
                "from Views where name ="+view+"");
    }

    public View getById(long id) {
        return (View) getSession().load(View.class, id);
    }

    public void update(View view) {
        getSession().update(view);
    }
}
