package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.ProgramRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OktaFoundationApplicationTest.class,
    properties = {"command.line.runner.enabled=false"})
public class ProgramServiceImplUnitTestNoDB {

    @Autowired
    private ProgramService programService;

    @MockBean
    private ProgramRepository programrepos;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private HelperFunctions helperFunctions;

    private List<Program> programList;

    @Before
    public void setUp() throws Exception {
        programList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(1);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);

        Module m2 = new Module();
        m2.setModuleId(1);
        m2.setModuleName("Controllers");
        m2.setModuleDescription("Learn about making controllers");
        m2.setModuleContent("Make controllers for each of your models.");

        Course c2 = new Course();
        c2.setCourseid(1);
        c2.setCoursename("APIs");
        c2.setCoursecode("Web 36 Java");
        c2.setCoursedescription("A look at making web APIs");
        c2.getModules().add(m2);

        Program p2 = new Program();
        p2.setProgramId(1);
        p2.setAdmin(u1);
        p2.setProgramName("Spring Boot");
        p2.setProgramType("Java");
        p2.setProgramDescription("Intro to Spring Boot");
        p2.getCourses().add(c2);

        programList.add(p1);
        programList.add(p2);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        Mockito.when(programrepos.findAll()).thenReturn(programList);
        assertEquals(2, programService.findAll().size());
    }

    @Test
    public void findProgramById() {
        Mockito.when(programrepos.findById(1L))
                .thenReturn(Optional.of(programList.get(0)));
        assertEquals("React", programService.findProgramById(1L).getProgramName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findProgramByIdNotFound() {
        Mockito.when(programrepos.findById(10L))
                .thenReturn(Optional.empty());

        assertEquals("React", programService.findProgramById(10L).getProgramName());
    }

    @Test
    public void findByName() {
        Mockito.when(programrepos.findByProgramName("Spring Boot"))
                .thenReturn(programList.get(1));
        assertEquals("Spring Boot", programService.findByName("Spring Boot").getProgramName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByNameNotFound() {
        Mockito.when(programrepos.findByProgramName("Visual Basic"))
                .thenReturn(null);
        assertEquals("Visual Basic", programService.findByName("Visual Basic").getProgramName());
    }

    @Test
    public void delete() {
        Mockito.when(programrepos.findById(2L))
                .thenReturn(Optional.of(programList.get(1)));
        Mockito.doNothing()
                .when(programrepos)
                .deleteById(2L);
        programService.delete(2L);
        assertEquals(2, programList.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFoundDelete()
    {
        Mockito.when(programrepos.findById(10L))
                .thenReturn(Optional.empty());
        Mockito.doNothing()
                .when(programrepos)
                .deleteById(10L);

        programService.delete(10L);
        assertEquals(2, programList.size());
    }

    @Test
    public void save() throws Exception {
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Course c2 = new Course();
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(0);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);
        p1.getCourses().add(c2);
        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(c1);
        Mockito.when(programrepos.save(any(Program.class)))
                .thenReturn(p1);
        assertEquals("React", programService.save(p1).getProgramName());
    }

    @Test
    public void savePut() throws Exception {
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(5);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);
        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        Mockito.when(programrepos.findById(5L))
                .thenReturn(Optional.of(p1));
        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(c1);
        Mockito.when(programrepos.save(any(Program.class)))
                .thenReturn(p1);
        assertEquals("React", programService.save(p1).getProgramName());
    }

    @Test
    public void update() throws Exception {
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(5);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);
        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        Mockito.when(programrepos.findById(5L))
                .thenReturn(Optional.of(p1));
        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(true);
        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(c1);
        Mockito.when(programrepos.save(any(Program.class)))
                .thenReturn(p1);
        assertEquals("React", programService.update(p1, 5L).getProgramName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNotFound() throws Exception {
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(5);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);
        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        Mockito.when(programrepos.findById(10L))
                .thenReturn(Optional.empty());
        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(true);
        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(c1);
        Mockito.when(programrepos.save(any(Program.class)))
                .thenReturn(p1);
        assertEquals("React", programService.update(p1, 5L).getProgramName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNotAuthorizedToMakeChange() throws Exception {
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        Module m1 = new Module();
        m1.setModuleId(1);
        m1.setModuleName("useState");
        m1.setModuleDescription("Learn about the useState hook.");
        m1.setModuleContent("useState sets up state for a component.");

        Course c1 = new Course();
        c1.setCourseid(1);
        c1.setCoursename("Hooks");
        c1.setCoursecode("Web 36 React");
        c1.setCoursedescription("A look at commonly used React Hooks.");
        c1.getModules().add(m1);

        Program p1 = new Program();
        p1.setProgramId(5);
        p1.setAdmin(u1);
        p1.setProgramName("React");
        p1.setProgramType("JavaScript");
        p1.setProgramDescription("Intro to React");
        p1.getCourses().add(c1);
        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        Mockito.when(programrepos.findById(5L))
                .thenReturn(Optional.of(p1));
        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(false);
        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(c1);
        Mockito.when(programrepos.save(any(Program.class)))
                .thenReturn(p1);
        assertEquals("React", programService.update(p1, 5L).getProgramName());
    }

    @Test
    public void deleteAll() {
        Mockito.doNothing()
                .when(programrepos)
                .deleteAll();
        programService.deleteAll();
        assertEquals(2, programList.size());
    }
}