package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.ExpirationTag;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ExpirationTagDao {

    private static ExpirationTagDao expirationTagDao;

    private ExpirationTagDao(){

    }

    public static ExpirationTagDao getInstance(){
        if(expirationTagDao == null){
            expirationTagDao = new ExpirationTagDao();
        }
        return expirationTagDao;
    }

    public void saveExpirationTag(ExpirationTag expirationTag) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(expirationTag);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public ExpirationTag getExpirationTagById(int expirationTag) {
        Transaction transaction = null;
        ExpirationTag expirationTag1 = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            expirationTag1 = session.get(ExpirationTag.class, expirationTag);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return expirationTag1;
    }

    public List<ExpirationTag> getExpirationTagList(){
        Transaction transaction = null;

        List<ExpirationTag> expirationTagList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ExpirationTag> cr = cb.createQuery(ExpirationTag.class);
            Root<ExpirationTag> root = cr.from(ExpirationTag.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            expirationTagList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return expirationTagList;
    }
}
