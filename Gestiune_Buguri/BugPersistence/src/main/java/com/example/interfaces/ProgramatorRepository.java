package com.example.interfaces;

import com.example.Programator;
import com.example.Repository;

public interface ProgramatorRepository extends Repository<Long, Programator> {
    public Programator getAccount(String username, String parola);
}
