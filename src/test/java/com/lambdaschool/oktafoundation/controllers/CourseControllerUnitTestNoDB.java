package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import com.lambdaschool.oktafoundation.services.CourseService;
import com.lambdaschool.oktafoundation.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ,
    classes = OktaFoundationApplicationTest.class,
    properties = {
        "command.line.runner.enabled=false"
    })
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CourseControllerUnitTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRespository courseRespository;

    private List<Course> courseList;

    private List<User> userList;

    private List<Program> programList;

    private User u1;

    @Before
    public void setUp()
    {
        courseList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        u1 = new User("admin");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));

        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails()
                .get(0)
                .setUseremailid(10);

        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));
        u1.getUseremails()
                .get(1)
                .setUseremailid(11);

        u1.setUserid(101);
        userList.add(u1);

        Program p1 = new Program();
        p1.setProgramId(20);
        p1.setProgramName("Testing New Program");
        p1.setProgramType("Testing");
        p1.setProgramDescription("I am mocking the creation of this object for testing purposes. Don't try this at home.");
        p1.setAdmin(u1);

        Course c1 = new Course();
        c1.setCourseid(21);
        c1.setCoursename("Testing New Course");
        c1.setCoursecode("ABC123ABC123");
        c1.setCoursedescription("Here is a description of this really cool course. So much information. So much knowledge.");

        //linking c1 to p1
        p1.getCourses().add(c1);

        Course c2 = new Course();
        c2.setCourseid(21);
        c2.setCoursename("Testing New Course 2");
        c2.setCoursecode("***ABC123ABC123***");
        c2.setCoursedescription("THIS IS FOR C2. Here is a description of this really cool course. So much information. So much knowledge.");

        //linking c2 to p1
        p1.getCourses().add(c2);

        programList.add(p1);
        courseList.add(c1);
        System.out.println(c1.getProgram().toString());

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";

        Mockito.when(courseRespository.findAll())
                .thenReturn(courseList);
    }
}
