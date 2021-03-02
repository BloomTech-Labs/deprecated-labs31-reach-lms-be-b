package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.services.ProgramService;
import com.lambdaschool.oktafoundation.services.UserService;
import com.lambdaschool.oktafoundation.services.UserStudentsService;
import com.lambdaschool.oktafoundation.services.UserTeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * The entry point for clients to access program data
 */
@RestController
@RequestMapping("/programs")
public class ProgramController {
    @Autowired
    private ProgramService programServices;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTeachersService userTeachersService;

    @Autowired
    private UserStudentsService userStudentsService;

    /**
     * Returns a list of all programs
     * <br>Example: <a href="http://localhost:2019/programs/programs">http://localhost:2019/programs/programs</a>
     *
     * @return JSON list of all programs with a status of OK
     * @see ProgramService#findAll() ProgramService.findAll()
     */
    @GetMapping(value = "/programs", produces = "application/json")
    public ResponseEntity<?> listAllPrograms()
    {
        List<Program> programs = programServices.findAll();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    /**
     * Returns a single program based off a program id number
     * <br>Example: http://localhost:2019/programs/program/1
     *
     * @param programid The primary key of the program you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findProgramById(long)  ProgramService.findProgramById(long)
     */

    @GetMapping(value = "/program/{programid}", produces = "application/json")
    public ResponseEntity<?> getProgramById(@PathVariable long programid){
        Program p = programServices.findProgramById(programid);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    /**
     * Returns a single program based on a given program name
     * <br>Example: <a href="http://localhost:2019/programs/program/name/React">http://localhost:2019/programs/program/name/React</a>
     *
     * @param programName The name of the program (String) you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findByName(String)   ProgramService.findByName(String)
     */

    @GetMapping(value = "/program/name/{programName}", produces = "application/json")
    public ResponseEntity<?> getProgramByName(@PathVariable String programName){
        Program p = programServices.findByName(programName);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    /**
     * Returns a list of Courses based on a programid
     * <br>Example: <a href="http://localhost:2019/programs/program/1/courses">http://localhost:2019/programs/program/1/courses</a>
     *
     * @param programid The name of the program (long) you seek
     * @return JSON list of courses
     * @see ProgramService#findProgramById(long)    ProgramService.findProgramById(long)
     */

    @GetMapping(value = "/program/{programid}/courses", produces = "application/json")
    public ResponseEntity<?> getProgramCourses (@PathVariable long programid){
        Program p = programServices.findProgramById(programid);
        return new ResponseEntity<>(p.getCourses(), HttpStatus.OK);
    }

    /**
     * Returns a list of teachers based on a programid
     * <br>Example: <a href="http://localhost:2019/programs/program/1/teachers">http://localhost:2019/programs/program/1/teachers</a>
     *
     * @param programid The name of the program (long) you seek
     * @return JSON list of users
     * @see ProgramService#findProgramById(long)    ProgramService.findProgramById(long)
     */

    @GetMapping(value = "/program/{programid}/teachers", produces = "application/json")
    public ResponseEntity<?> getProgramTeachers (@PathVariable long programid){
        Program p = programServices.findProgramById(programid);
        List<User> teachers = new ArrayList<>();
        for(UserTeachers ut : p.getTeachers())
        {
            teachers.add(ut.getTeacher());
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    /**
     * Returns a list of students based on a programid
     * <br>Example: <a href="http://localhost:2019/programs/program/1/students">http://localhost:2019/programs/program/1/students</a>
     *
     * @param programid The name of the program (long) you seek
     * @return JSON list of users
     * @see ProgramService#findProgramById(long)    ProgramService.findProgramById(long)
     */

    @GetMapping(value = "/program/{programid}/students", produces = "application/json")
    public ResponseEntity<?> getProgramStudents (@PathVariable long programid){
        Program p = programServices.findProgramById(programid);
        List<User> students = new ArrayList<>();
        for(UserStudents us : p.getStudents())
        {
            students.add(us.getUser());
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Given a complete Program Object, create a new Program record and accompanying courses and modules
     * <br> Example: <a href="http://localhost:2019/programs/program">http://localhost:2019/programs/program</a>
     *
     * @param newProgram A complete new Program
     * @return A location header with the URI to the newly created program and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see ProgramService#save(Program) ProgramService.save(Program)
     */

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

    /**
     * Given a complete Program Object
     * Given the program id, primary key, is in the program table,
     * replace the Program record.
     * <br> Example: <a href="http://localhost:2019/programs/programs/1">http://localhost:2019/programs/program/1</a>
     *
     * @param updateProgram A complete Program
     * @param programid     The primary key of the program you wish to replace.
     * @return status of OK
     * @see ProgramService#save(Program) ProgramService.save(Program)
     */

    @PutMapping(value = "/program/{programid}", consumes = "application/json")
    public ResponseEntity<?> updateFullProgram(@Valid @RequestBody Program updateProgram,
                                               @PathVariable long programid, Authentication authentication) throws Exception
    {
        updateProgram.setAdmin(userService.findByName(authentication.getName()));
        updateProgram.setProgramId(programid);
        programServices.save(updateProgram);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given the program id, primary key, is in the program table,
     * and the user id, primary key, is in the User table, add the user to the set of teachers
     * for the given program
     * <br> Example: <a href="http://localhost:2019/programs/programs/1/teachers/1">http://localhost:2019/programs/program/1/teachers/1</a>
     *
     * @param programid     The primary key of the program you wish to add a teacher to
     * @param userid        The primary key of the user you wish to add as a teacher
     * @return status of OK
     * @see UserTeachersService#save(long, long) UserTeachersService.save(Long, Long)
     */

    @PutMapping(value = "/program/{programid}/teachers/{userid}")
    public ResponseEntity<?> addTeacherToProgram(@PathVariable long programid, @PathVariable long userid) {
        userTeachersService.save(userid, programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given the program id, primary key, is in the program table,
     * and the user id, primary key, is in the User table, add the user to the set of students
     * for the given program
     * <br> Example: <a href="http://localhost:2019/programs/programs/1/students/1">http://localhost:2019/programs/program/1/students/1</a>
     *
     * @param programid     The primary key of the program you wish to add a student to
     * @param userid        The primary key of the user you wish to add as a student
     * @return status of OK
     * @see UserTeachersService#save(long, long) UserTeachersService.save(Long, Long)
     */

    @PutMapping(value = "/program/{programid}/students/{userid}")
    public ResponseEntity<?> addStudentToProgram(@PathVariable long programid, @PathVariable long userid) {
        userStudentsService.save(userid, programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the program record associated with the given id with the provided data. Only the provided fields are affected.
     * If an course list is given, it replaces the original course list.
     * <br> Example: <a href="http://localhost:2019/programs/program/7">http://localhost:2019/programs/program/7</a>
     *
     * @param updateProgram An object containing values for just the fields that are being updated. All other fields are left NULL.
     * @param programid     The primary key of the program you wish to update.
     * @return A status of OK
     * @see ProgramService#update(Program, long) ProgramService.update(Program, long)
     */

    @PatchMapping(value = "program/{programid}", consumes = "application/json")
    public ResponseEntity<?> updateProgram(@RequestBody Program updateProgram, @PathVariable long programid) throws Exception
    {
        programServices.update(updateProgram, programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given program along with associated courses and modules
     * <br>Example: <a href="http://localhost:2019/programs/program/14">http://localhost:2019/programs/program/14</a>
     *
     * @param programid the primary key of the program you wish to delete
     * @return Status of OK
     */

    @DeleteMapping(value = "program/{programid}")
    public ResponseEntity<?> deleteProgramById(@PathVariable long programid){
        programServices.delete(programid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given teacher from the set of teachers for a given program
     * <br>Example: <a href="http://localhost:2019/programs/program/14/teachers/2">http://localhost:2019/programs/program/14/teacher/2</a>
     *
     * @param programid the primary key of the program you wish to remove a teacher from
     * @param userid the primary key of the teacher you wish to remove
     * @return Status of OK
     */

    @DeleteMapping(value = "/program/{programid}/teachers/{userid}")
    public ResponseEntity<?> deleteTeacherFromProgram(@PathVariable long programid, @PathVariable long userid)
    {
        userTeachersService.deleteById(new UserTeachersId(userid, programid));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given student from the set of students for a given program
     * <br>Example: <a href="http://localhost:2019/programs/program/14/students/2">http://localhost:2019/programs/program/14/students/2</a>
     *
     * @param programid the primary key of the program you wish to remove a student from
     * @param userid the primary key of the student you wish to remove
     * @return Status of OK
     */

    @DeleteMapping(value = "/program/{programid}/students/{userid}")
    public ResponseEntity<?> deleteStudentFromProgram(@PathVariable long programid, @PathVariable long userid)
    {
        userStudentsService.deleteById(new UserStudentsId(userid, programid));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
