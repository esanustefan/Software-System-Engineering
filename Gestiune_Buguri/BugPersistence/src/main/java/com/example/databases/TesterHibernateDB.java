package com.example.databases;

import com.example.Tester;
import com.example.interfaces.TesterRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Properties;

public class TesterHibernateDB  implements TesterRepository {
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

    public TesterHibernateDB(Properties props) {
    }


    @Override
    public Tester findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Tester> findAll() throws SQLException {
        return null;
    }

    @Override
    public Tester save(Tester entity) throws FileNotFoundException {
        return null;
    }

    @Override
    public Tester delete(Long aLong) {
        return null;
    }

    @Override
    public Tester update(Tester entity1, Tester entity2) {
        return null;
    }

    @Override
    public Tester getAccount(String username, String parola) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Tester tester = session.createQuery("from Tester where username = :username and parola = :parola", Tester.class)
                        .setParameter("username", username)
                        .setParameter("parola", parola)
//                        .setParameter("functie", functie)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return tester;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        close();
        return null;
    }
}
