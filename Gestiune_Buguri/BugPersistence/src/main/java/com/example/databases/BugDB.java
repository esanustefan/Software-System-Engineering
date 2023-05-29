package com.example.databases;

import com.example.Bug;
import com.example.BugRisk;
import com.example.BugStatus;
import com.example.Utils.JdbcUtils;
import com.example.interfaces.BugRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BugDB implements BugRepository {
    private final JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public BugDB(Properties props) {
        logger.info("Initializing BugDB with properties: {} ", props);
        System.out.println("Initializing BugDB with properties: {} "+ props);
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Bug findOne(Long aLong) {
        logger.traceEntry("finding bug with id {}", aLong);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from bug where id= ?")) {
            preStmt.setLong(1, aLong);
            try(ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    String denumire = result.getString("denumire");
                    String descriere = result.getString("descriere");
                    String risk = result.getString("risk");
                    String status = result.getString("status");
                    Bug bug = new Bug(denumire, descriere, risk, status);
                    bug.setId(aLong);
                    logger.traceExit(bug);
                    return bug;
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
    public Iterable<Bug> findAll() throws SQLException {
        logger.traceEntry("finding all bugs");
        Connection con = jdbcUtils.getConnection();
        List<Bug> bugs = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from bug")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    Long id = result.getLong("id");
                    String denumire = result.getString("denumire");
                    String descriere = result.getString("descriere");
                    String risk = result.getString("risk");
                    String status = result.getString("status");
                    Bug bug = new Bug(denumire, descriere, risk, status);
                    bug.setId(id);
                    bugs.add(bug);
                    logger.traceExit(bugs);
                }

            } catch (SQLException e) {
                logger.error(e);
                System.err.println("Error DB " + e);
            }
            return bugs;
        }
    }

    @Override
    public Bug save(Bug entity) throws FileNotFoundException {
        logger.traceEntry("saving bug {}", entity);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into bug(denumire, descriere, risk, status) values (?,?,?,?)")) {
            preStmt.setString(1, entity.getDenumire());
            preStmt.setString(2, entity.getDescriere());
            preStmt.setString(3, entity.getBugRisk().toString());
            preStmt.setString(4, entity.getBugStatus().toString());
            int result = preStmt.executeUpdate();
            logger.traceExit(result);
            return entity;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Bug delete(Long aLong) {
        return null;
    }

    @Override
    public Bug update(Bug entity1, Bug entity2) {
        logger.traceEntry("updating bug {} with {}", entity1, entity2);
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update bug set denumire=?, descriere=?, risk=?, status=? where id=?")) {
            preStmt.setString(1, entity2.getDenumire());
            preStmt.setString(2, entity2.getDescriere());
            preStmt.setString(3, entity2.getBugRisk().toString());
            preStmt.setString(4, entity2.getBugStatus().toString());
            preStmt.setLong(5, entity1.getId());
            int result = preStmt.executeUpdate();
            logger.traceExit(result);
            return entity2;
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        return null;
    }
}
