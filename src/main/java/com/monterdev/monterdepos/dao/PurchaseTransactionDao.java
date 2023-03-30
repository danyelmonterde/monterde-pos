package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Purchase;
import com.monterdev.monterdepos.model.PurchaseTransaction;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseTransactionDao {

    private static PurchaseTransactionDao purchaseTransactionDao;

    private PurchaseTransactionDao(){

    }

    public static PurchaseTransactionDao getInstance(){
        if(purchaseTransactionDao == null){
            purchaseTransactionDao = new PurchaseTransactionDao();
        }
        return purchaseTransactionDao;
    }

    public void savePurchaseTransaction(PurchaseTransaction purchase){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(purchase);
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }

    public void updatePurchaseTransaction(PurchaseTransaction purchaseTransaction){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<PurchaseTransaction> criteriaUpdate = cb.createCriteriaUpdate(PurchaseTransaction.class);
            Root<PurchaseTransaction> root = criteriaUpdate.from(PurchaseTransaction.class);
            criteriaUpdate.set("grandTotal", purchaseTransaction.getGrandTotal());
            criteriaUpdate.set("totalItems", purchaseTransaction.getTotalItems());
            criteriaUpdate.set("supplier", purchaseTransaction.getSupplier());
            criteriaUpdate.set("supplierLocation", purchaseTransaction.getSupplierLocation());
            criteriaUpdate.set("dateTransacted", purchaseTransaction.getDateTransacted());
            criteriaUpdate.where(cb.equal(root.get("transactionNumber"), purchaseTransaction.getTransactionNumber()));
            session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }

    public PurchaseTransaction getPurchaseTransactionByTransactionNumber(String transactionNumber){
        Transaction transaction = null;
        PurchaseTransaction purchaseTransaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            purchaseTransaction = session.get(PurchaseTransaction.class,transactionNumber);

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return purchaseTransaction;
    }
}
