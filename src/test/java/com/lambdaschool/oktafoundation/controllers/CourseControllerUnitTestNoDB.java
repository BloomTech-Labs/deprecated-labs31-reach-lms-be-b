package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.CourseService;
import com.lambdaschool.oktafoundation.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.*;
import java.lang.Module;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private UserRepository userRepository;

    private List<Course> courseList;

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

        courseList.add(c1);
        System.out.println(courseList);
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

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.getAllCourses())
                .thenReturn(courseList);
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();

        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void fetchCourseById() throws Exception
    {
        String apiUrl = "/courses/course/21";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.fetchCourseById(21L))
                .thenReturn(courseList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList.get(0));

        assertEquals(er, tr);
    }

    @Test
    public void fetchCourseIdNotFound() throws Exception
    {
        String apiUrl = "/courses/course/50";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.fetchCourseById(50L))
                .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        String er = "";

        assertEquals(er, tr);
    }

    @Test
    public void fetchCourseModules() throws Exception
    {
        String apiUrl = "/courses/course/{courseid}/modules";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.fetchCourseById(21L))
                .thenReturn(courseList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 21L)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        String er = objectMapper.writeValueAsString(courseList.get(0).getModules());

        assertEquals(er, tr);
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl = "/courses/courses";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(courseList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"coursename\" : \"Testing New Course\", \"coursecode\" : \"ABC123ABC123\"}");

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateExistingCourse() throws Exception
    {
        String apiUrl = "/courses/course/{courseid}";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.save(any(Course.class)))
                .thenReturn(courseList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 21L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"coursename\" : \"Testing New Course\", \"coursecode\" : \"ABC123ABC123\"}");
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void patchExistingCourse() throws Exception
    {
        String apiUrl = "/courses/patchcourse/{courseid}";

        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(courseService.edit(any(Course.class)))
                .thenReturn(courseList.get(0));
        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl, 21L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"coursename\" : \"Testing New Course\", \"coursecode\" : \"ABC123ABC123\"}");
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteCourseById() throws Exception
    {
        String apiUrl = "/courses/course/{courseid}";
        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, 21L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        Mockito.when(userRepository.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}
