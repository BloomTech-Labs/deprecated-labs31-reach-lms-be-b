package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.UserStudents;
import com.lambdaschool.oktafoundation.models.UserStudentsId;
import org.springframework.data.repository.CrudRepository;

public interface UserStudentsRepository extends CrudRepository<UserStudents, UserStudentsId>
{
}
