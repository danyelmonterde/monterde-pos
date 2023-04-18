package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.SalesTransaction;
import com.monterdev.monterdepos.util.HibernateProdUtil;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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

    public void updateSalesTransaction(SalesTransaction salesTransaction) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(salesTransaction);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateSalesTransactionInProd(SalesTransaction salesTransaction) {
        Transaction transaction = null;
        try (Session session = HibernateProdUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(salesTransaction);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<SalesTransaction> getSalesTransactionListFromProd() {
        Transaction transaction = null;

        List<SalesTransaction> salesTransactionList = null;
        try (Session session = HibernateProdUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SalesTransaction> cr = cb.createQuery(SalesTransaction.class);
            Root<SalesTransaction> root = cr.from(SalesTransaction.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            salesTransactionList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return salesTransactionList;
    }

    public List<SalesTransaction> getSalesTransactionList() {
        Transaction transaction = null;

        List<SalesTransaction> salesTransactionList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SalesTransaction> cr = cb.createQuery(SalesTransaction.class);
            Root<SalesTransaction> root = cr.from(SalesTransaction.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            salesTransactionList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return salesTransactionList;
    }
}
