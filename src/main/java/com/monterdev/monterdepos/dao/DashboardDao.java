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

    public Item getItemById(int id){
        Transaction transaction = null;
        Item item1 = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            item1 = session.get(Item.class,id);

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return item1;
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItems(String itemName){
        Transaction transaction = null;

        List<Item> itemList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> cr = cb.createQuery(Item.class);
            Root<Item> root = cr.from(Item.class);
            cr.select(root).where(cb.like(root.get("itemName"),"%"+itemName+"%"));
            Query query = session.createQuery(cr);
            itemList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return itemList;
    }

    public Item deleteItemById(int id){
        Transaction transaction = null;
        Item item = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            item = session.get(Item.class,id);
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
