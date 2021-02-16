package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Program;

import java.util.List;

public interface ProgramService {
    List<Program> findAll();
    Program findProgramById(long id);
    Program findByName(String name);
    void delete(long id);
    Program save(Program program) throws Exception;
    Program update(Program program, long id) throws Exception;
    void deleteAll();
}
