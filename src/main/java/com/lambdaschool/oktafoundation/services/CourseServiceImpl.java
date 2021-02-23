package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceFoundException;
import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.models.Program;
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

    @Autowired
    ProgramService programService;

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

        Program p = programService.findProgramById(course.getProgram().getProgramId());
        newCourse.setProgram(p);

        newCourse = courseRespository.save(newCourse);

        newCourse.getModules().clear();
        for (Module m : course.getModules()) {
            m.setCourse(newCourse);
            m = moduleService.save(m);
            newCourse.getModules().add(m);
        }
        return courseRespository.save(newCourse);
    }

    @Override
    public void deleteCourseById(long courseid)
    {
        courseRespository.findById(courseid).orElseThrow(() -> new EntityNotFoundException("Course with id " + courseid + " not found!"));
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
    public Course edit(Course partiallyEditedCourse) throws Exception
    {
        Course currentCourse = courseRespository.findById(partiallyEditedCourse.getCourseid())
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + partiallyEditedCourse.getCourseid() + " not found!"));

        if (partiallyEditedCourse.getCoursename() != null)
        {
            currentCourse.setCoursename(partiallyEditedCourse.getCoursename());
        }

        if (partiallyEditedCourse.getCoursecode() != null)
        {
            currentCourse.setCoursecode(partiallyEditedCourse.getCoursecode());
        }

        if (partiallyEditedCourse.getCoursedescription() != null)
        {
            currentCourse.setCoursedescription(partiallyEditedCourse.getCoursedescription());
        }

        //may want to change this if we don't want the program to be edited through course
        if (partiallyEditedCourse.getProgram() != null)
        {
            currentCourse.setProgram(partiallyEditedCourse.getProgram());
        }

        //may want to change this depending on what we want to do. I am thinking that we wouldn't want to do a patch to courses
        // to add a module, instead I think we do that through module endpoint.
        if (partiallyEditedCourse.getModules().size() > 0)
        {
            currentCourse.getModules().clear();
            for (Module m : partiallyEditedCourse.getModules())
            {
                m.setCourse(currentCourse);
                m = moduleService.save(m);
                currentCourse.getModules().add(m);
            }
        }


        return courseRespository.save(currentCourse);
    }
}
