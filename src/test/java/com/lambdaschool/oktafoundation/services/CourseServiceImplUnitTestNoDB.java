package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.exceptions.ResourceFoundException;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OktaFoundationApplicationTest.class,
properties = {"command.line.runner.enabled=false"})
public class CourseServiceImplUnitTestNoDB
{
    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRespository courseRespository;

    @MockBean
    private UserService userService;

    @MockBean
    private ProgramService programService;

    @MockBean
    private ModuleService moduleService;

    @MockBean
    private HelperFunctions helperFunctions;

    private List<Course> courseList;

    @Before
    public void setUp() throws Exception
    {
        courseList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);

        User u1 = new User("test@reachlms.com", "John", "Doe", "18005551234");
        u1.setUserid(1);
        u1.getRoles().add(new UserRoles(u1, r1));

        com.lambdaschool.oktafoundation.models.Module m1 = new com.lambdaschool.oktafoundation.models.Module();
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

        com.lambdaschool.oktafoundation.models.Module m2 = new Module();
        m2.setModuleId(2);
        m2.setModuleName("Controllers");
        m2.setModuleDescription("Learn about making controllers");
        m2.setModuleContent("Make controllers for each of your models.");

        Course c2 = new Course();
        c2.setCourseid(2);
        c2.setCoursename("APIs");
        c2.setCoursecode("Web 36 Java");
        c2.setCoursedescription("A look at making web APIs");
        c2.getModules().add(m2);

        Program p2 = new Program();
        p2.setProgramId(2);
        p2.setAdmin(u1);
        p2.setProgramName("Spring Boot");
        p2.setProgramType("Java");
        p2.setProgramDescription("Intro to Spring Boot");
        p2.getCourses().add(c2);

        courseList.add(c1);
        courseList.add(c2);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void findAll() throws Exception
    {
        Mockito.when(courseRespository.findAll()).thenReturn(courseList);

        assertEquals(2, courseService.getAllCourses().size());
    }

    @Test
    public void getCourseById() throws Exception
    {
        Mockito.when(courseRespository.findById(1L))
                .thenReturn(Optional.of(courseList.get(0)));

        assertEquals("Hooks", courseService.fetchCourseById(1L).getCoursename());
    }

    @Test(expected = ResourceFoundException.class)
    public void getCourseByIdNotFound()
    {
        Mockito.when(courseRespository.findById(1L))
                .thenReturn(empty());

        assertEquals("React", courseService.fetchCourseById(10L).getCoursename());
    }

    @Test
    public void addNewCourse() throws Exception
    {
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
        c1.setProgram(p1);

        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(courseRespository.findById(1L))
                .thenReturn(Optional.of(c1));
        Mockito.when(programService.findProgramById(1L))
                .thenReturn(p1);
        Mockito.when(courseRespository.save(any(Course.class)))
                .thenReturn(c1);
        assertEquals("Hooks", courseService.save(c1).getCoursename());
    }

    @Test
    public void putExistingCourse() throws Exception
    {
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
        c1.setProgram(p1);

        Mockito.when(userService.findUserById(1L))
                .thenReturn(u1);
        Mockito.when(programService.findProgramById(1L))
                .thenReturn(p1);
        Mockito.when(courseRespository.findById(1L))
                .thenReturn(Optional.of(c1));
        Mockito.when(courseRespository.save(any(Course.class)))
                .thenReturn(c1);
        assertEquals("Hooks", courseService.save(c1).getCoursename());
    }

    @Test(expected = EntityNotFoundException.class)
    public void putExistingCourseNotFound() throws Exception
    {
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
        Mockito.when(courseRespository.findById(10L))
                .thenReturn(Optional.empty());
        Mockito.when(courseRespository.save(any(Course.class)))
                .thenReturn(c1);
        assertEquals("Hooks", courseService.save(c1).getCoursename());
    }

    @Test
    public void patchExistingCourse() throws Exception
    {
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

        Mockito.when(courseRespository.findById(1L))
                .thenReturn(Optional.of(courseList.get(0)));

        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(true);

        Mockito.when(courseRespository.save(any(Course.class)))
                .thenReturn(c1);

        assertEquals("Hooks", courseRespository.save(c1).getCoursename());
    }

    @Test(expected = EntityNotFoundException.class)
    public void patchExistingCourseNotFound() throws Exception
    {
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

        Mockito.when(courseRespository.findById(10L))
                .thenReturn(Optional.empty());

        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(true);

        Mockito.when(courseRespository.save(any(Course.class)))
                .thenReturn(c1);

        assertEquals("Hooks", courseService.edit(c1).getCoursename());
    }

    @Test
    public void deleteCourse() throws Exception
    {
        Mockito.when(courseRespository.findById(1L))
                .thenReturn(Optional.of(courseList.get(0)));
        Mockito.doNothing()
                .when(courseRespository)
                .deleteById(1L);
        courseService.deleteCourseById(1L);
        assertEquals(2, courseList.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCourseNotFound() throws Exception
    {
        Mockito.when(courseRespository.findById(20L))
                .thenReturn(Optional.empty());
        Mockito.doNothing()
                .when(courseRespository)
                .deleteById(20L);
        courseService.deleteCourseById(20L);
        assertEquals(2, courseList.size());
    }
}
