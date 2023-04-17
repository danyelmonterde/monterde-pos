package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.HibernateProdUtil;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static com.monterdev.monterdepos.constants.StockAlertTypes.*;

public class ItemDao {

    private static ItemDao itemDao;

    private ItemDao() {

    }

    public static ItemDao getInstance() {
        if (itemDao == null) {
            itemDao = new ItemDao();
        }
        return itemDao;
    }

    public void saveItem(Item item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateItem(Item item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(item);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Item getItemByItemCode(String itemCode) {
        Transaction transaction = null;
        Item item1 = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            item1 = session.get(Item.class, itemCode);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return item1;
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItemsByItemCode(String itemCode, int beginIndex, int endIndex) {
        Transaction transaction = null;

        List<Item> itemList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> cr = cb.createQuery(Item.class);
            Root<Item> root = cr.from(Item.class);

            Predicate hasItemName = cb.like(root.get("itemName"), "%" + itemCode + "%");
            Predicate hasItemCode = cb.like(root.get("itemCode"), "%" + itemCode + "%");
            Predicate requiredWhereClause = cb.or(hasItemCode, hasItemName);


            cr.select(root).where(requiredWhereClause);

            Query query = session.createQuery(cr)
                    .setFirstResult(beginIndex)
                    .setMaxResults(endIndex);
            itemList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return itemList;
    }

    public List<Item> getItemsByNameCategoryStockAndPaging(String itemName, int category, String stockAlertType, int beginIndex, int endIndex) {
        Transaction transaction = null;

        List<Item> itemList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> cr = cb.createQuery(Item.class);
            Root<Item> root = cr.from(Item.class);

            Predicate[] requiredWhereClause = new Predicate[3];
            Predicate hasItemCode = cb.like(root.get("itemCode"), "%" + itemName + "%");
            Predicate hasItemName = cb.like(root.get("itemName"), "%" + itemName + "%");
            if (category == 0) {
                requiredWhereClause[0] = cb.gt(root.get("categoryId"), 0);
            } else {
                requiredWhereClause[0] = cb.equal(root.get("categoryId"), category);
            }
            if (stockAlertType.equals(ALL_STOCKS)) {
                requiredWhereClause[1] = cb.gt(root.get("inStock"), 0);
            } else if (stockAlertType.equals(LOW_STOCK)) {
                requiredWhereClause[1] = cb.lt(root.get("inStock"), 11);
            } else if (stockAlertType.equals(NO_STOCK)) {
                requiredWhereClause[1] = cb.lt(root.get("inStock"), 1);
            }
            requiredWhereClause[2] = cb.or(hasItemCode, hasItemName);
            cr.orderBy(cb.asc(root.get("inStock")));

            cr.select(root).where(requiredWhereClause);
            Query query = session.createQuery(cr)
                    .setFirstResult(beginIndex)
                    .setMaxResults(endIndex);

            itemList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return itemList;
    }

    public void deleteItemByItemCode(String itemCode) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaDelete<Item> criteriaDelete = cb.createCriteriaDelete(Item.class);
            Root<Item> root = criteriaDelete.from(Item.class);
            criteriaDelete.where(cb.equal(root.get("itemCode"), itemCode));

            session.createQuery(criteriaDelete).executeUpdate();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public List<Item> getItemListFromProd() {
        Transaction transaction = null;

        List<Item> itemList = null;
        try (Session session = HibernateProdUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> cr = cb.createQuery(Item.class);
            Root<Item> root = cr.from(Item.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            itemList = query.getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return itemList;
    }
}
