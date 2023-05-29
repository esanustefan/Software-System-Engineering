package com.example.databases;

import com.example.Programator;
import com.example.Utils.JdbcUtils;
import com.example.interfaces.ProgramatorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ProgramatorDB implements ProgramatorRepository {

    private final JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public ProgramatorDB(Properties props) {
        logger.info("Initializing ProgramatorDB with properties: {} ", props);
        System.out.println("Initializing ProgramatorDB with properties: {} "+ props);
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Programator getAccount(String username, String parola) {
        logger.traceEntry("finding programator with {}", username);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from programator where username= ? and parola = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, parola);
//            preStmt.setString(3, functie);
            try(ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    Long id = result.getLong("id");
                    Programator programator = new Programator(username, parola);
                    programator.setId(id);
                    logger.traceExit(programator);
                    return programator;
                }
            } catch (SQLException e) {
                logger.error(e);
                System.err.println("Error DB " + e);
            }
        } catch (Exception e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        return null;
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


}
