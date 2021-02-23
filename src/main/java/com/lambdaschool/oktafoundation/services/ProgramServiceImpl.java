package com.lambdaschool.oktafoundation.services;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
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

    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Override
    public List<Program> findAll() {
        List<Program> list = new ArrayList<>();
        programrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    @Override
    public Program findProgramById(long id) throws ResourceNotFoundException {
        return programrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program id " + id + " not found!"));
    }

    @Override
    public Program findByName(String name) {
        Program p = programrepos.findByProgramName(name);
        if (p == null)
        {
            throw new ResourceNotFoundException("Program name " + name + " not found!");
        }
        return p;
    }

    @Override
    public void delete(long id) {
        programrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program id " + id + " not found!"));
        programrepos.deleteById(id);
    }

    @Override
    public Program save(Program program) throws Exception
    {
        Program newProgram = new Program();

        if (program.getProgramId() != 0)
        {
            programrepos.findById(program.getProgramId())
                    .orElseThrow(() -> new ResourceNotFoundException("Program id " + program.getProgramId() + " not found!"));
            newProgram.setProgramId(program.getProgramId());
        }

        User admin = userService.findUserById(program.getAdmin().getUserid());
        newProgram.setAdmin(admin);

        newProgram.setProgramName(program.getProgramName());
        newProgram.setProgramType(program.getProgramType());
        newProgram.setProgramDescription(program.getProgramDescription());

        newProgram = programrepos.save(newProgram);

        newProgram.getTeachers().clear();
        for (UserTeachers ut : program.getTeachers())
        {
            User addTeacher = userService.findUserById(ut.getTeacher().getUserid());
            newProgram.getTeachers().add(new UserTeachers(addTeacher, newProgram));
        }

        newProgram.getStudents().clear();
        for (UserStudents us: program.getStudents())
        {
            User addStudent = userService.findUserById(us.getUser().getUserid());
            newProgram.getStudents().add(new UserStudents(addStudent, newProgram));
        }

        newProgram.getCourses().clear();
        for (Course c : program.getCourses())
        {
            c.setProgram((newProgram));
            c = courseService.save(c);
            newProgram.getCourses().add(c);
        }


        return programrepos.save(newProgram);
    }

    @Override
    public Program update(Program program, long id) throws Exception
    {
        Program currentProgram = findProgramById(id);

        if(helperFunctions.isAuthorizedToMakeChange(currentProgram.getAdmin().getUsername()))
        {
            if (program.getProgramName() != null)
            {
                currentProgram.setProgramName(program.getProgramName());
            }
            if (program.getProgramType() != null)
            {
                currentProgram.setProgramType(program.getProgramType());
            }
            if (program.getProgramDescription() != null)
            {
                currentProgram.setProgramDescription(program.getProgramDescription());
            }
            if(program.getTeachers().size() > 0)
            {
                currentProgram.getTeachers().clear();
                for (UserTeachers ut : program.getTeachers())
                {
                    User addTeacher = userService.findUserById(ut.getTeacher().getUserid());
                    currentProgram.getTeachers().add(new UserTeachers(addTeacher, currentProgram));
                }
            }
            if(program.getStudents().size() > 0){
                currentProgram.getStudents().clear();
                for (UserStudents us : program.getStudents())
                {
                    User addStudent = userService.findUserById(us.getUser().getUserid());
                    currentProgram.getStudents().add(new UserStudents(addStudent, currentProgram));
                }
            }
            if(program.getCourses().size() > 0)
            {
                currentProgram.getCourses().clear();
                for(Course c : program.getCourses())
                {
                    c.setProgram(currentProgram);
                    c = courseService.save(c);
                    currentProgram.getCourses().add(c);
                }
            }

        } else
        {
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
        return currentProgram;
    }

    @Override
    public void deleteAll() {
        programrepos.deleteAll();
    }
}
