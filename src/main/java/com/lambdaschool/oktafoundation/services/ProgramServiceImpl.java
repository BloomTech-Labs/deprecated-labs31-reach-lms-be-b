package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceFoundException;
import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "programService")
public class ProgramServiceImpl implements ProgramService {
    @Autowired
    private ProgramRepository programrepos;

    @Override
    public List<Program> findAll() {
        List<Program> list = new ArrayList<>();
        programrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    @Override
    public Program findProgramById(long id) throws ResourceFoundException{
        return programrepos.findById(id)
                .orElseThrow(() -> new ResourceFoundException("Program id " + id + " not found!"));
    }

    @Override
    public Program findByName(String name) {
        Program p = programrepos.findByProgramName(name);
        if (p == null)
        {
            throw new ResourceFoundException("Program name " + name + " not found!");
        }
        return p;
    }

    @Override
    public void delete(long id) {
        programrepos.findById(id)
                .orElseThrow(() -> new ResourceFoundException("Program id " + id + " not found!"));
        programrepos.deleteById(id);
    }

    @Override
    public Program save(Program program) {
        Program newProgram = new Program();
        if (program.getProgramId() != 0)
        {
            programrepos.findById(program.getProgramId())
                    .orElseThrow(() -> new ResourceFoundException("Program id " + program.getProgramId() + " not found!"));
            newProgram.setProgramId(program.getProgramId());
        }
        newProgram.setProgramName(program.getProgramName());
        newProgram.setGetProgramType(program.getGetProgramType());
        newProgram.setProgramDescription(program.getProgramDescription());

        return programrepos.save(newProgram);
    }

    @Override
    public Program update(Program program, long id) {
        return null;
    }

    @Override
    public void deleteAll() {
        programrepos.deleteAll();
    }
}
