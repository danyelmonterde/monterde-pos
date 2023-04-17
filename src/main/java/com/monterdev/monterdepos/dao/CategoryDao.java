package com.monterdev.monterdepos.dao;

import com.monterdev.monterdepos.constants.CategoryTypes;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.HibernateProdUtil;
import com.monterdev.monterdepos.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CategoryDao {

    private static CategoryDao categoryDao;

    private CategoryDao() {

    }

    public static CategoryDao getInstance() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public void saveCategory(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateCategory(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(category);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Category getCategoryById(int categoryId) {
        Transaction transaction = null;
        Category category = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            category = session.get(Category.class, categoryId);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return category;
    }

    public Category getCategoryByName(String categoryName) {

        Transaction transaction = null;
        Category category = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Category> cr = cb.createQuery(Category.class);
            Root<Category> root = cr.from(Category.class);
            cr.select(root).where(cb.like(root.get("category"),categoryName));
            Query query = session.createQuery(cr);
            Optional<Category> optionalCategory = query.getResultList().stream().findFirst();
            if(optionalCategory.isPresent()){
                category = optionalCategory.get();
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return category;
    }

    public List<Category> getCategoryList(){
        Transaction transaction = null;

        List<Category> categoryList = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Category> cr = cb.createQuery(Category.class);
            Root<Category> root = cr.from(Category.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            categoryList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return categoryList;
    }

    public List<Category> getCategoryListFromProd(){
        Transaction transaction = null;

        List<Category> categoryList = null;
        try(Session session = HibernateProdUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Category> cr = cb.createQuery(Category.class);
            Root<Category> root = cr.from(Category.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            categoryList = query.getResultList();

            transaction.commit();
        }catch (Exception ex){
            if(transaction!= null){
                transaction.rollback();
            }
        }
        return categoryList;
    }
}
