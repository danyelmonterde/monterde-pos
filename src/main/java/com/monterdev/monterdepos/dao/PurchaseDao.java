package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Purchase;
import com.monterdev.monterdepos.util.HibernateProdUtil;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
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

    public void updatePurchaseFromProd(Purchase purchase) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(purchase);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updatePurchaseInProd(Purchase purchase) {
        Transaction transaction = null;
        try (Session session = HibernateProdUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(purchase);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
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

    public List<Purchase> getPurchaseByTransactionNumberCategoryDateAndPaging(String transactionNumber, Date dateFrom,Date dateUntil, int beginIndex, int endIndex){
        Transaction transaction = null;

        List<Purchase> purchaseList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Purchase> cr = cb.createQuery(Purchase.class);
            Root<Purchase> root = cr.from(Purchase.class);


            Predicate hasItemCode = cb.equal(root.get("itemCode"),transactionNumber);
            Predicate hasItemName = cb.equal(root.get("transactionNumber"),transactionNumber);
            Predicate dateBoughtFromSupplier =cb.between(root.get("dateBoughtFromSupplier"),dateFrom,dateUntil);
            Predicate finalWhereClause = cb.or(hasItemCode,hasItemName,dateBoughtFromSupplier);

            cr.select(root).where(finalWhereClause);
            Query query = session.createQuery(cr)
                    .setFirstResult(beginIndex)
                    .setMaxResults(endIndex);

            purchaseList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return purchaseList;
    }

    public List<Purchase> getPurchaseListFromProd() {
        Transaction transaction = null;

        List<Purchase> purchaseList = null;
        try (Session session = HibernateProdUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Purchase> cr = cb.createQuery(Purchase.class);
            Root<Purchase> root = cr.from(Purchase.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            purchaseList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return purchaseList;
    }

    public List<Purchase> getPurchaseList() {
        Transaction transaction = null;

        List<Purchase> purchaseList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Purchase> cr = cb.createQuery(Purchase.class);
            Root<Purchase> root = cr.from(Purchase.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            purchaseList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return purchaseList;
    }
}
