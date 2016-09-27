package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.model.StockHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StockHistoryDAO {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(StockHistory history) {
        getSession().save(history);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from StockHistory").list();
    }


}
