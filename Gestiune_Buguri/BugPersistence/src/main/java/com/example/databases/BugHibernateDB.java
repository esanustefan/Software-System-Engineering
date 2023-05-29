package com.example.databases;

import com.example.Bug;
import com.example.interfaces.BugRepository;
//import com.jogamp.common.nio.StructAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Properties;

public class BugHibernateDB implements BugRepository {
    static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public BugHibernateDB(Properties props) {
    }

    @Override
    public Bug findOne(Long aLong) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Bug bug = session.createQuery("from Bug where id = :id", Bug.class)
                        .setParameter("id", aLong)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return bug;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Iterable<Bug> findAll() throws SQLException {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Iterable<Bug> bugs = session.createQuery("from Bug", Bug.class)
                        .list();
                tx.commit();
                System.out.println(bugs);
                return bugs;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Bug save(Bug entity) throws FileNotFoundException {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return entity;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Bug delete(Long aLong) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Bug bug = session.createQuery("from Bug where id = :id", Bug.class)
                        .setParameter("id", aLong)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(bug);
                tx.commit();
                return bug;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public Bug update(Bug entity1, Bug entity2) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                // implement here, to update entity1 with values from entity2
                entity1.setBugStatus(entity2.getBugStatus());
                session.update(entity1);
                tx.commit();
                return entity1;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }
}
