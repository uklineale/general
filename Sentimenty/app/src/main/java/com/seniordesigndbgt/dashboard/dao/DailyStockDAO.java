package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.DailyStock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DailyStockDAO {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(DailyStock stock){
        getSession().save(stock);
    }

    public void delete(DailyStock stock){
        getSession().delete(stock);
    }

    @SuppressWarnings("unchecked")
    public List<DailyStock> getAll() {
        return getSession().createQuery("from DailyStock").list();
    }

    public void clearDaily() {
        for (DailyStock stock : getAll()){
            delete(stock);
        }
    }

    public DailyStock getLatest() { return (DailyStock) getSession().createQuery("from DailyStock").list().get(0); }


}
