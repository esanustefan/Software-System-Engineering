package com.example.databases;

import com.example.Tester;
import com.example.Utils.JdbcUtils;
import com.example.interfaces.TesterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
public class TesterDB implements TesterRepository {

    private final JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public TesterDB(Properties props) {
        logger.info("Initializing TesterDB with properties: {} ", props);
        System.out.println("Initializing TesterDB with properties: {} "+ props);
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Tester getAccount(String username, String parola) {
        logger.traceEntry("finding tester with {}", username);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from tester where username= ? and parola = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, parola);
//            preStmt.setString(3, functie);
            try(ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    Long id = result.getLong("id");
                    Tester tester = new Tester(username, parola);
                    tester.setId(id);
                    logger.traceExit(tester);
                    return tester;
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


}
