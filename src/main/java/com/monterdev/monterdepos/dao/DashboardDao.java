package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DashboardDao {

    private static DashboardDao dashboardDao;


    public static DashboardDao getInstance(){
        if (dashboardDao == null) {
            return new DashboardDao();
        }else return dashboardDao;
    }

    public void saveItem(Item item){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }

    public void updateItem(Item item){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(item);
            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
    }

    public Item getItemByItemCode(String itemCode){
        Transaction transaction = null;
        Item item1 = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            item1 = session.get(Item.class,itemCode);

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return item1;
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItems(String itemCode){
        Transaction transaction = null;

        List<Item> itemList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> cr = cb.createQuery(Item.class);
            Root<Item> root = cr.from(Item.class);
            cr.select(root).where(cb.like(root.get("itemCode"),"%"+itemCode+"%"));
            Query query = session.createQuery(cr)
                    .setFirstResult(0)
                    .setMaxResults(10);
            itemList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return itemList;
    }

    public Item deleteItemByItemCode(String itemCode){
        Transaction transaction = null;
        Item item = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            item = session.get(Item.class,itemCode);
            session.delete(item);

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return item;
    }
}
