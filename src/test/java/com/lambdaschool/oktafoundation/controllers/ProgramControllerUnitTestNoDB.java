package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.ProgramService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = OktaFoundationApplicationTest.class,
    properties = {"command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class ProgramControllerUnitTestNoDB {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProgramService programService;

    @MockBean
    private UserRepository userrepos;

    private List<Program> programList;

    private User u1;

    @Before
    public void setUp() throws Exception {
        programList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);

        u1 = new User("admin", "John", "Doe", "18005551234");
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
        p2.setProgramId(2);
        p2.setAdmin(u1);
        p2.setProgramName("Spring Boot");
        p2.setProgramType("Java");
        p2.setProgramDescription("Intro to Spring Boot");
        p2.getCourses().add(c2);

        Program p3 = new Program();
        p3.setProgramId(3);
        p3.setAdmin(u1);
        p3.setProgramName("Python");
        p3.setProgramType("Py");
        p3.setProgramDescription("Intro to Python");

        programList.add(p1);
        programList.add(p2);
        programList.add(p3);

        p1.getTeachers().add(new UserTeachers(u1, p1));
        p1.getStudents().add(new UserStudents(u1, p1));

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllPrograms() throws Exception {
        String apiUrl = "/programs/programs";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findAll())
                .thenReturn(programList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void getProgramById() throws Exception {
        String apiUrl = "/programs/program/{programId}";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findProgramById(1L))
                .thenReturn(programList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 1L)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void getProgramByIdNotFound() throws Exception {
        String apiUrl = "/programs/program/{programId}";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findProgramById(77L))
                .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 77)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void getProgramByName() throws Exception {
        String apiUrl = "/programs/program/name/{programName}";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findByName("React"))
                .thenReturn(programList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, "React")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void addNewProgram() throws Exception {
        String apiUrl = "/programs/program";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.save(any(Program.class)))
                .thenReturn(programList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(2));
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(er);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateFullProgram() throws Exception {
        String apiUrl = "/programs/program/{programId}";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.save(any(Program.class)))
                .thenReturn(programList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(er);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProgram() throws Exception {
        String apiUrl = "/programs/program/{programId}";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.update(any(Program.class), any(Long.class)))
                .thenReturn(programList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(er);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProgramById() throws Exception {
        String apiUrl = "/programs/program/{programId}";
        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        Mockito.when(userrepos.findByUsername(u1.getUsername())).thenReturn(u1);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProgramCourses() throws Exception {
        String apiUrl = "/programs/program/{programId}/courses";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findProgramById(1L))
                .thenReturn(programList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 1L)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();

        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(programList.get(0).getCourses());

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void getProgramTeachers() throws Exception {
        String apiUrl = "/programs/program/{programId}/teachers";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findProgramById(1L))
                .thenReturn(programList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 1L)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();

        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<User> lp = new ArrayList<>();
        for (UserTeachers ut : programList.get(0).getTeachers())
        {
            lp.add(ut.getTeacher());
        }
        String er = mapper.writeValueAsString(lp);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }

    @Test
    public void getProgramStudents() throws Exception {
        String apiUrl = "/programs/program/{programId}/students";

        Mockito.when(userrepos.findByUsername(u1.getUsername()))
                .thenReturn(u1);

        Mockito.when(programService.findProgramById(1L))
                .thenReturn(programList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl, 1L)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
                .andReturn();

        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<User> lp = new ArrayList<>();
        for (UserStudents us : programList.get(0).getStudents())
        {
            lp.add(us.getUser());
        }
        String er = mapper.writeValueAsString(lp);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er, tr);
    }
}