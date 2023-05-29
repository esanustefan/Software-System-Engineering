package com.example.databases;

import com.example.Programator;
import com.example.interfaces.ProgramatorRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Properties;

public class ProgramatorHibernateDB implements ProgramatorRepository {
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

    public ProgramatorHibernateDB(Properties props) {
    }

    @Override
    public Programator findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Programator> findAll() throws SQLException {
        return null;
    }

    @Override
    public Programator save(Programator entity) throws FileNotFoundException {
        return null;
    }

    @Override
    public Programator delete(Long aLong) {
        return null;
    }

    @Override
    public Programator update(Programator entity1, Programator entity2) {
        return null;
    }

    @Override
    public Programator getAccount(String username, String parola) {
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Programator programator = session.createQuery("from Programator where username = :username and parola = :parola", Programator.class)
                        .setParameter("username", username)
                        .setParameter("parola", parola)
//                        .setParameter("functie", functie)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return programator;
            } catch (RuntimeException ex) {
                System.out.println("Exceptie " + ex);
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
        }
        close();
        return null;
    }
}
