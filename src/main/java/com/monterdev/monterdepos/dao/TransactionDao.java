package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.SalesTransaction;
import com.monterdev.monterdepos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionDao {

    private static  TransactionDao transactionDao;

    private TransactionDao(){

    }

    public static TransactionDao getInstance(){
        if(transactionDao == null){
            transactionDao = new TransactionDao();
        }

        return transactionDao;
    }

    public void saveTransaction(SalesTransaction systemSalesTransaction){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(systemSalesTransaction);
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }
}
