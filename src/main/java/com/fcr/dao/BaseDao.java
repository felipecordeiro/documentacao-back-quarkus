package com.fcr.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

@SuppressWarnings("unchecked")
public abstract class BaseDao<E, P> {

    @Inject
    protected EntityManager entityManager;

    private Class<E> clazz;

    @Inject
    private Logger logger;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityTransaction tx;
    private Session session;

    public BaseDao() {
        clazz = getGenericTypeClass();
    }

    /**
     * Persist a entity e executa flush.
     *
     * @param entity Entity
     * @return
     * @throws Exception
     */
    public E persist(E entity) throws Exception {
        try {
            this.entityManager.persist(entity);
            flush();
            return entity;
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Merge a entity e executa flush.
     *
     * @param entity Entity
     * @return
     * @throws Exception
     */
    public void merge(E entity) throws Exception {
        try {
            this.entityManager.merge(entity);
            flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remove a entity pela pk e executa flush.
     *
     * @param pk pk
     * @return
     * @throws Exception
     */
    public void remove(P pk) throws Exception {
        try {
            this.entityManager.remove(find(pk));
            flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Find pela pk.
     *
     * @param pk Pk
     * @return
     * @throws Exception
     */
    public E find(P pk) throws Exception {
        try {
            return entityManager.find(clazz, pk);
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Lista todos os dados.
     *
     * @return
     * @throws Exception
     */
    public List<E> findAll() throws Exception {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> criteria = builder.createQuery(clazz);
            Root<E> root = criteria.from(clazz);
            criteria.select(root);
            Query query = entityManager.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Lista todos os dados.
     *
     * @param attributesOrdering Atributos para ordenação
     * @param ascendingOrdering  ordenação ASC
     * @return
     * @throws Exception
     */
    public List<E> findAll(Collection<String> attributesOrdering, boolean ascendingOrdering) throws Exception {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> criteria = builder.createQuery(clazz);
            Root<E> root = criteria.from(clazz);
            criteria.select(root);
            addCriteriaOrdering(builder, criteria, root, attributesOrdering, ascendingOrdering);
            Query query = entityManager.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Lista todos os dados.
     *
     * @param start índice de início
     * @param end   índice de término
     * @return
     * @throws Exception
     */
    public List<E> findAll(int start, int end) throws Exception {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> criteria = builder.createQuery(clazz);
            Root<E> root = criteria.from(clazz);
            criteria.select(root);
            Query query = entityManager.createQuery(criteria);
            query.setFirstResult(start);
            query.setMaxResults(end);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Lista todos os dados.
     *
     * @param start              índice de início
     * @param end                índice de término
     * @param attributesOrdering Atributos para ordenação
     * @param ascendingOrdering  Ordenação ASC
     * @return
     * @throws Exception
     */
    public List<E> findAll(int start, int end, Collection<String> attributesOrdering, boolean ascendingOrdering) throws Exception {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<E> criteria = builder.createQuery(clazz);
            Root<E> root = criteria.from(clazz);
            criteria.select(root);
            addCriteriaOrdering(builder, criteria, root, attributesOrdering, ascendingOrdering);
            Query query = entityManager.createQuery(criteria);
            query.setFirstResult(start);
            query.setMaxResults(end);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, getStackTrace(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    private void addCriteriaOrdering(CriteriaBuilder builder, CriteriaQuery<E> criteria, Root<E> root,
            Collection<String> attributesOrdering, boolean ascendingOrdering) {
        List<Order> ordering = new ArrayList<>();
        for (String attributeOrdering : attributesOrdering) {
            if (ascendingOrdering) {
                ordering.add(builder.asc(root.get(attributeOrdering)));
            } else {
                ordering.add(builder.desc(root.get(attributeOrdering)));
            }
        }
        criteria.orderBy(ordering);
    }

    /**
     * Lista pelos atributos preenchidos.
     *
     * @param example Objeto exemplo
     * @param useLike Usar like
     * @return
     */
    public void makeEntity() {
        entityManager = entityManagerFactory.createEntityManager();
        this.tx = entityManager.getTransaction();
        session = (Session) entityManager.getDelegate();
    }

    public List<E> findByExample(E entity, boolean ignoreCase) throws RuntimeException {
        makeEntity();
        List<E> lista = null;
        Example example = null;
        try {

            example = Example.create(entity).enableLike(MatchMode.ANYWHERE);
            if (ignoreCase) {
                example = example.ignoreCase();
            }
            Criteria crit = session.createCriteria(entity.getClass());
            crit.add(example);

            lista = crit.list();

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao realizar consulta!" + e.getMessage());
        }
    }

    public List<E> findByExample(E filtro, MatchMode matchMode, boolean ignoreCase) {
        makeEntity();
        org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(filtro);

        if (matchMode != null) {
            example = example.enableLike(matchMode);
        }

        if (ignoreCase) {
            example = example.ignoreCase();
        }

        return session.createCriteria(this.clazz).add(example).list();
    }

    /**
     * Lista pelos atributos preenchidos.
     *
     * @param example            Objeto exemplo
     * @param useLike            Usar like
     * @param attributesOrdering Atributos para ordenação
     * @param ascendingOrdering  Ordenação ASC
     * @return
     */

    public List<E> findByExample(final E entity, boolean useLike, Collection<String> attributesOrdering,
            boolean ascendingOrdering) {
        makeEntity();
        List<E> lista = null;
        Example example = null;
        try {
            example = Example.create(entity).enableLike(MatchMode.ANYWHERE);
            if (useLike) {
                example = example.enableLike();
            }

            Criteria crit = session.createCriteria(entity.getClass());
            crit.add(example);
            orderAttributesExample(attributesOrdering, ascendingOrdering, crit);

            lista = crit.list();

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao realizar consulta!" + e.getMessage());
        }
    }

    private void orderAttributesExample(Collection<String> attributesOrdering, boolean ascendingOrdering,
            Criteria crit) {
        for (String order : attributesOrdering) {
            if (ascendingOrdering) {
                crit.addOrder(org.hibernate.criterion.Order.asc(order));
            } else {
                crit.addOrder(org.hibernate.criterion.Order.desc(order));
            }
        }
    }

    private Class<E> getGenericTypeClass() {
        Type genericSuperClass = getClass().getGenericSuperclass();
        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }
        return (Class<E>) parametrizedType.getActualTypeArguments()[0];
    }

    public void refresh(E object) {
        this.entityManager.refresh(object);
    }

    public void flush() {
        this.entityManager.flush();
    }

    public void clear() {
        flush();
        this.entityManager.clear();
    }

    private String getStackTrace(Exception e) {
        StringWriter errorStackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(errorStackTrace));
        return "Erro da camada DAO: " + errorStackTrace.toString();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
