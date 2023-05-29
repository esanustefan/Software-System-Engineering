package com.example.observer;


import com.example.Bug;
import com.example.Programator;
import com.example.Tester;

import java.util.List;

public interface BugServiceInterface {
    Tester loginTester(Tester tester, BugObserverInterface client) throws Exception;
    Programator loginProgramator(Programator programator, BugObserverInterface client) throws Exception;
    void addBug(Bug bug, BugObserverInterface client) throws Exception;
    List<Bug> getAllBugs(BugObserverInterface client) throws Exception;

    void updateBug(Bug bug, BugObserverInterface client) throws Exception;

    void logoutTester(Tester tester, BugObserverInterface client) throws Exception;

    void logoutProgramator(Programator programator, BugObserverInterface client) throws Exception;

    void deleteBug(Bug bug, BugObserverInterface client);
}
