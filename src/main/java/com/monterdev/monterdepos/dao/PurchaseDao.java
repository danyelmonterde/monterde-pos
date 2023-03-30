package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Purchase;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseDao {

    private static PurchaseDao purchaseDao;

    private PurchaseDao(){

    }

    public static PurchaseDao getInstance(){
        if(purchaseDao == null){
            purchaseDao = new PurchaseDao();
        }
        return purchaseDao;
    }

    public void savePurchase(Purchase purchase){
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

    public void updatePurchase(Purchase purchase){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<Purchase> criteriaUpdate = cb.createCriteriaUpdate(Purchase.class);
            Root<Purchase> root = criteriaUpdate.from(Purchase.class);
            criteriaUpdate.set("cost", purchase.getCost());
            criteriaUpdate.set("quantity", purchase.getQuantity());
            criteriaUpdate.set("total", purchase.getTotal());
            criteriaUpdate.set("dateBoughtFromSupplier", purchase.getDateBoughtFromSupplier());
            criteriaUpdate.where(cb.equal(root.get("transactionNumber"), purchase.getTransactionNumber()));
            session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }

    public List<Purchase> getPurchaseListByItemCode(String itemCode){
        Transaction transaction = null;
        List<Purchase> purchaseList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Purchase> cr = cb.createQuery(Purchase.class);
            Root<Purchase> root = cr.from(Purchase.class);
            cr.select(root).where(cb.like(root.get("itemCode"),itemCode));
            cr.orderBy(
                    cb.desc(root.get("dateBoughtFromSupplier")));


            Query query = session.createQuery(cr)
                    .setFirstResult(0)
                    .setMaxResults(10);
            purchaseList = query.getResultList();
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return purchaseList;
    }
}
