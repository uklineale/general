package com.seniordesigndbgt.dashboard.dao;

import com.seniordesigndbgt.dashboard.analytics.TrendAnalyzer;
import com.seniordesigndbgt.dashboard.model.Trend;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TrendDAO {

    @Autowired
    private SessionFactory _sessionFactory;
    private static final int NUMBER_OF_TRENDS = TrendAnalyzer.NUM_OF_KEYWORDS_TO_DISPLAY;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Trend trend) {
        //System.out.println("\nTrend title: "+trend.getTrendTitle()+" is this long: "+trend.getTrendTitle().length());
        getSession().save(trend);
    }

    public void delete(Trend trend) {
        getSession().delete(trend);
    }

    @SuppressWarnings("unchecked")
    public List<Trend> getAll() {
        return getSession().createQuery("from Trend").list();
    }

    public List<Trend> getMostRecent(){

        List<Trend> trendList = getSession().createQuery("from Trend").list();
        if (trendList.size() > NUMBER_OF_TRENDS)
                return trendList.subList(trendList.size()-NUMBER_OF_TRENDS, trendList.size());
        else
            return trendList;
    }

    public void update(Trend trend){
        getSession().update(trend);
    }

}
