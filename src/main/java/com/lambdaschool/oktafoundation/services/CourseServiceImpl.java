package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceFoundException;
import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Autowired
    CourseRespository courseRespository;

    @Autowired
    ModuleService moduleService;

    @Override
    public Course fetchCourseById(long courseid) throws ResourceFoundException
    {
        return courseRespository.findById(courseid)
                .orElseThrow(() -> new ResourceFoundException("Course " + courseid + " not found!"));
    }

    @Override
    public Course save(Course course) throws Exception
    {
        Course newCourse = new Course();

        if (course.getCourseid() != 0)
        {
            courseRespository.findById(course.getCourseid())
                    .orElseThrow(() -> new EntityNotFoundException("Course " + course.getCourseid() + " not Found!"));
            newCourse.setCourseid(course.getCourseid());
        }

        newCourse.setCoursename(course.getCoursename());
        newCourse.setCoursecode(course.getCoursecode());
        newCourse.setCoursedescription(course.getCoursedescription());

        for (Module m : course.getModules())
        {
            if (m.getModuleId() > 0)
            {
                moduleService.fetchModuleById(m.getModuleId());
            }
            else
            {
                m.setModuleId(0);
                moduleService.save(m);
            }
            newCourse.getModules().add(m);
        }
        return courseRespository.save(newCourse);
    }

    @Override
    public void deleteCourseById(long courseid)
    {
        courseRespository.deleteById(courseid);
    }

    @Override
    public List<Course> getAllCourses()
    {
        List<Course> rtnList = new ArrayList<>();
        courseRespository.findAll().iterator().forEachRemaining(rtnList::add);

        return rtnList;
    }

    @Override
    public Course edit(Course partiallyEditedCourse)
    {
        Course editedCourse = new Course();



        return editedCourse;
    }
}
