package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SalesTransactionDao {

    private static SalesTransactionDao salesTransactionDao;

    private SalesTransactionDao(){

    }

    public static SalesTransactionDao getInstance(){
        if(salesTransactionDao == null){
            salesTransactionDao = new SalesTransactionDao();
        }
        return salesTransactionDao;
    }

    public void saveSalesTransaction(Sales sales){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(sales);
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }
}
