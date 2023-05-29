package com.example.service;

import com.example.Bug;
import com.example.Programator;
import com.example.Tester;
import com.example.interfaces.BugRepository;
import com.example.interfaces.ProgramatorRepository;
import com.example.interfaces.TesterRepository;
import com.example.observer.BugObserverInterface;
import com.example.observer.BugServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements BugServiceInterface {
    private final TesterRepository testerRepository;
    private final ProgramatorRepository programatorRepository;
    private final BugRepository bugRepository;

    private Map<Long, BugObserverInterface> loggedTesteri;
    private Map<Long, BugObserverInterface> loggedProgramatori;

    public Service(TesterRepository testerRepository, ProgramatorRepository programatorRepository, BugRepository bugRepository) {
        this.testerRepository = testerRepository;
        this.programatorRepository = programatorRepository;
        this.bugRepository = bugRepository;
        loggedTesteri = new ConcurrentHashMap<>();
        loggedProgramatori = new ConcurrentHashMap<>();
    }

    public synchronized Tester loginTester(Tester tester, BugObserverInterface client) throws Exception {
        Tester tester1 = testerRepository.getAccount(tester.getUsername(), tester.getParola());
        if (tester1 != null) {
            if (loggedTesteri.get(tester1.getId()) != null)
                throw new Exception("Tester already logged in.");
            loggedTesteri.put(tester1.getId(), client);
            System.out.println("Logged users " + loggedTesteri);
        } else
            throw new Exception("Authentication failed.");
        return tester1;
    }

    public synchronized Programator loginProgramator(Programator programator, BugObserverInterface client) throws Exception {
        Programator programator1 = programatorRepository.getAccount(programator.getUsername(), programator.getParola());
        if (programator1 != null) {
            if (loggedProgramatori.get(programator1.getId()) != null)
                throw new Exception("Programator already logged in.");
            loggedProgramatori.put(programator1.getId(), client);
            System.out.println("Logged users " + loggedProgramatori);
        } else
            throw new Exception("Authentication failed.");
        return programator1;
    }



    @Override
    public synchronized void addBug(Bug bug, BugObserverInterface client) throws Exception {
        bugRepository.save(bug);
        notifyClients();
    }

    @Override
    public List<Bug> getAllBugs(BugObserverInterface client) throws Exception {
        List<Bug> bugs = new ArrayList<>();
        Iterable<Bug> bugs1 = bugRepository.findAll();
        bugs1.forEach(bugs::add);
        return bugs;
    }

    @Override
    public synchronized void updateBug(Bug bug, BugObserverInterface client) {
        Bug bug1 = bugRepository.findOne(bug.getId());
        bugRepository.update(bug1, bug);
        notifyClients();
    }

    private synchronized void notifyClients() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(Long key : loggedTesteri.keySet()) {
            BugObserverInterface clientT = loggedTesteri.get(key);
            executor.execute(() -> {
                try {
                    clientT.updateBug();
                } catch (Exception e) {
                    System.err.println("Error notifying client " + e);
                }
            });
        }
        for(Long key : loggedProgramatori.keySet()) {
            BugObserverInterface clientP = loggedProgramatori.get(key);
            executor.execute(() -> {
                try {
                    clientP.updateBug();
                } catch (Exception e) {
                    System.err.println("Error notifying client " + e);
                }
            });
        }
    }

    @Override
    public synchronized void logoutTester(Tester tester, BugObserverInterface client) throws Exception {
        Tester tester1 = testerRepository.getAccount(tester.getUsername(), tester.getParola());
        if(loggedTesteri.get(tester1.getId()) != null)
            loggedTesteri.remove(tester1.getId());
        else {
            throw new Exception("Tester not logged in.");
        }
    }

    @Override
    public void logoutProgramator(Programator programator, BugObserverInterface client) throws Exception {
        Programator programator1 = programatorRepository.getAccount(programator.getUsername(), programator.getParola());
        if(loggedProgramatori.get(programator1.getId()) != null)
            loggedProgramatori.remove(programator1.getId());
        else {
            throw new Exception("Programator not logged in.");
        }
    }

    @Override
    public void deleteBug(Bug bug, BugObserverInterface client) {
        bugRepository.delete(bug.getId());
        notifyClients();
    }
}
