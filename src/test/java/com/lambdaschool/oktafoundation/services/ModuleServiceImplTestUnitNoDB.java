package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.ModuleRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OktaFoundationApplicationTest.class,
        properties = {"command.line.runner.enabled=false"})
public class ModuleServiceImplTestUnitNoDB
{
    @Autowired
    ModuleService moduleService;

    @MockBean
    ModuleRepository moduleRepository;

    @MockBean
    UserService userService;

    @MockBean
    ProgramService programService;

    @MockBean
    HelperFunctions helperFunctions;

    @MockBean
    CourseService courseService;

    private List<Module> moduleList;

    @Before
    public void setUp() throws Exception
    {
        moduleList = new ArrayList<>();

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

        moduleList.add(m1);
        moduleList.add(m2);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void fetchModuleById() throws Exception
    {
        Mockito.when(moduleRepository.findById(1L))
                .thenReturn(Optional.of(moduleList.get(0)));
        assertEquals(1L, moduleService.fetchModuleById(1L).getModuleId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchModuleByIdNotFound() throws Exception
    {
        Mockito.when(moduleRepository.findById(10L))
                .thenReturn(Optional.empty());
        assertEquals(1L, moduleService.fetchModuleById(1L).getModuleId());
    }

    @Test
    public void save()
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
        m1.setCourse(c1);

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

        Mockito.when(moduleRepository.findById(1L))
                .thenReturn(Optional.of(m1));
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(moduleRepository.save(any(Module.class)))
                .thenReturn(m1);

        assertEquals("useState", moduleService.save(m1).getModuleName());
    }

    @Test
    public void putSave()
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
        m1.setCourse(c1);

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

        Mockito.when(moduleRepository.findById(1L))
                .thenReturn(Optional.of(m1));
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);
        Mockito.when(moduleRepository.save(any(Module.class)))
                .thenReturn(m1);

        assertEquals("useState", moduleService.save(m1).getModuleName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void putSaveNotFound()
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

        Mockito.when(moduleRepository.findById(10L))
                .thenReturn(Optional.empty());
        Mockito.when(moduleRepository.save(any(Module.class)))
                .thenReturn(m1);

        assertEquals("useState", moduleService.save(m1).getModuleName());
    }

    @Test
    public void deleteModuleById()
    {
        Mockito.when(moduleRepository.findById(1L))
                .thenReturn(Optional.of(moduleList.get(0)));
        Mockito.doNothing()
                .when(moduleRepository)
                .deleteById(1L);
        moduleService.deleteModuleById(1L);

        assertEquals(2, moduleList.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteModuleByIdNotFound()
    {
        Mockito.when(moduleRepository.findById(11L))
                .thenReturn(Optional.empty());
        Mockito.doNothing()
                .when(moduleRepository)
                .deleteById(1L);
        moduleService.deleteModuleById(11L);

        assertEquals(2, moduleList.size());
    }

    @Test
    public void edit()
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

        Mockito.when(moduleRepository.findById(1L))
                .thenReturn(Optional.of(m1));
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);

        Mockito.when(moduleRepository.save(any(Module.class)))
                .thenReturn(m1);

        assertEquals("useState", moduleService.edit(m1).getModuleName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void editNotFound()
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

        Mockito.when(moduleRepository.findById(10L))
                .thenReturn(Optional.empty());
        Mockito.when(courseService.fetchCourseById(1L))
                .thenReturn(c1);

        Mockito.when(moduleRepository.save(any(Module.class)))
                .thenReturn(m1);

        assertEquals("useState", moduleService.edit(m1).getModuleName());
    }

    @Test
    public void fetchAllModules()
    {
        Mockito.when(moduleRepository.findAll())
                .thenReturn(moduleList);

        assertEquals(2, moduleService.fetchAllModules().size());
    }
}