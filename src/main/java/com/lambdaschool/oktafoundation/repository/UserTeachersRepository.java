package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.UserTeachers;
import com.lambdaschool.oktafoundation.models.UserTeachersId;
import org.springframework.data.repository.CrudRepository;

public interface UserTeachersRepository extends CrudRepository<UserTeachers, UserTeachersId> {
}
