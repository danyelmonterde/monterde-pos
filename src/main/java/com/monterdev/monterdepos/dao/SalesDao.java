package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class SalesDao {

    private static SalesDao salesDao;

    private SalesDao(){

    }

    public static SalesDao getInstance(){
        if(salesDao == null){
            salesDao = new SalesDao();
        }
        return salesDao;
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

    public List<Sales> getSalesByTransactionNumberCategoryDateAndPaging(String transactionNumber, Date dateFrom, Date dateUntil, int beginIndex, int endIndex){
        Transaction transaction = null;

        List<Sales> salesList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            //SALES TRANSACTION
            CriteriaBuilder cbSales = session.getCriteriaBuilder();
            CriteriaQuery<Sales> crSales = cbSales.createQuery(Sales.class);
            Root<Sales> rootSales = crSales.from(Sales.class);
            Predicate salesHasItemCode = cbSales.equal(rootSales.get("transactionNumber"),transactionNumber);
            Predicate salesDates= cbSales.between(rootSales.get("dateTransacted"),dateFrom,dateUntil);
            Predicate salesWhereClause  = cbSales.or(salesHasItemCode,salesDates);

            crSales.select(rootSales).where(salesWhereClause);
            Query querySalesTransaction = session.createQuery(crSales)
                    .setFirstResult(beginIndex)
                    .setMaxResults(endIndex);

            salesList = querySalesTransaction.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return salesList;
    }

}
