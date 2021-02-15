package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRespository extends CrudRepository<Course, Long>
{
}
