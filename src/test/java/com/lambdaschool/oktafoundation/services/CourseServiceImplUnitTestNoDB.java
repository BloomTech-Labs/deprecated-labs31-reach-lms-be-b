package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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


}
