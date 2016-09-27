package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.Press;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PressDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Press press) {
        getSession().save(press);
    }

    public void delete(Press press) {
        getSession().delete(press);
    }

    @SuppressWarnings("unchecked")
    public List <Press> getAll() {
        return getSession().createQuery("from Press").list();
    }

    /*Get articles of a day in the past
    * How far in the past is determined by offset
    * Ex: get today's -> offset = 0
    * get yesterday's -> offset = 1
    * get 5 days ago's articles -> offset = 5*/
    public List <Press> getArticlesByOffset(int offset){
        if(offset == 0){
            return getSession().createQuery("from Press WHERE timestamp between CURDATE() and CURDATE()+1").list();
        } else {
            return getSession().createQuery("from Press WHERE timestamp between CURDATE()-" + offset + " and CURDATE()").list();
        }
    }

    public List <Press> search(String term) {
        return getSession().createQuery("from Press where title LIKE '%" + term + "%'").list();
    }

    public List <Press> getById(int id) {
        return getSession().createQuery("from Press where id = " + id + "").list();
    }

    public void update(Press press){
        getSession().update(press);
    }

}
