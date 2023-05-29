package com.example.interfaces;

import com.example.Repository;
import com.example.Tester;

public interface TesterRepository extends Repository<Long, Tester> {
    public Tester getAccount(String username, String parola);

}
