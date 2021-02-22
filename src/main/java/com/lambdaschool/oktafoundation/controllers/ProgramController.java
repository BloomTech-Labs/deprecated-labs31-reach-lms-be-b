package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserStudents;
import com.lambdaschool.oktafoundation.models.UserTeachers;
import com.lambdaschool.oktafoundation.services.HelperFunctions;
import com.lambdaschool.oktafoundation.services.ProgramService;
import com.lambdaschool.oktafoundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/programs")
public class ProgramController {
    @Autowired
    private ProgramService programServices;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @GetMapping(value = "/programs", produces = "application/json")
    public ResponseEntity<?> listAllPrograms()
    {
        List<Program> programs = programServices.findAll();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programId}", produces = "application/json")
    public ResponseEntity<?> getProgramById(@PathVariable long programId){
        Program p = programServices.findProgramById(programId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(value = "/program/name/{programName}", produces = "application/json")
    public ResponseEntity<?> getProgramByName(@PathVariable String programName){
        Program p = programServices.findByName(programName);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programId}/courses", produces = "application/json")
    public ResponseEntity<?> getProgramCourses (@PathVariable long programId){
        Program p = programServices.findProgramById(programId);
        return new ResponseEntity<>(p.getCourses(), HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programId}/teachers", produces = "application/json")
    public ResponseEntity<?> getProgramTeachers (@PathVariable long programId){
        Program p = programServices.findProgramById(programId);
        List<User> teachers = new ArrayList<>();
        for(UserTeachers ut : p.getTeachers())
        {
            teachers.add(ut.getTeacher());
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programId}/students", produces = "application/json")
    public ResponseEntity<?> getProgramStudents (@PathVariable long programId){
        Program p = programServices.findProgramById(programId);
        List<User> students = new ArrayList<>();
        for(UserStudents us : p.getStudents())
        {
            students.add(us.getUser());
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping(value = "/program", consumes = "application/json")
    public ResponseEntity<?> addNewProgram(@Valid @RequestBody Program newProgram, Authentication authentication) throws Exception
    {
        newProgram.setAdmin(userService.findByName(authentication.getName()));
        newProgram.setProgramId(0);
        newProgram = programServices.save(newProgram);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProgramURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{programId}")
                .buildAndExpand(newProgram.getProgramId())
                .toUri();
        responseHeaders.setLocation(newProgramURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/program/{programid}", consumes = "application/json")
    public ResponseEntity<?> updateFullProgram(@Valid @RequestBody Program updateProgram,
                                               @PathVariable long programid, Authentication authentication) throws Exception
    {
        updateProgram.setAdmin(userService.findByName(authentication.getName()));
        updateProgram.setProgramId(programid);
        programServices.save(updateProgram);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "program/{programid}", consumes = "application/json")
    public ResponseEntity<?> updateProgram(@RequestBody Program updateProgram, @PathVariable long programid) throws Exception
    {
        programServices.update(updateProgram, programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "program/{programid}")
    public ResponseEntity<?> deleteProgramById(@PathVariable long programid){
        programServices.delete(programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
