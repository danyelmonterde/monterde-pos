package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.ExpirationTag;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

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
            session.saveOrUpdate(expirationTag);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateExpirationTag(ExpirationTag expirationTag) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<ExpirationTag> criteriaUpdate = cb.createCriteriaUpdate(ExpirationTag.class);
            Root<ExpirationTag> root = criteriaUpdate.from(ExpirationTag.class);
            criteriaUpdate.set("dateOfExpiration", expirationTag.getDateOfExpiration());
            criteriaUpdate.set("itemCount", expirationTag.getItemCount());
            criteriaUpdate.where(cb.equal(root.get("expirationTag"), expirationTag.getExpirationTag()));
            session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public ExpirationTag getExpirationTagById(String expirationTag) {
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

    public ExpirationTag getExpirationTagByItemCode(String itemCode){
        Transaction transaction = null;

        ExpirationTag expirationTag = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ExpirationTag> cr = cb.createQuery(ExpirationTag.class);
            Root<ExpirationTag> root = cr.from(ExpirationTag.class);

            Predicate hasItemCode = cb.equal(root.get("itemCode"),itemCode);
            cr.select(root).where(hasItemCode);

            Query query = session.createQuery(cr);
            List<ExpirationTag> expirationTagList =query.getResultList();
            Optional<ExpirationTag> optionalExpirationTag = expirationTagList.stream()
                    .filter(e->e.getItemCode().equalsIgnoreCase(itemCode)).findFirst();
            expirationTag = optionalExpirationTag.isPresent() ? optionalExpirationTag.get():null ;

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return expirationTag;
    }
}
