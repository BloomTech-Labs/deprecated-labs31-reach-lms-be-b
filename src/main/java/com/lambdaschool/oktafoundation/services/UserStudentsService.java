package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.UserStudents;
import com.lambdaschool.oktafoundation.models.UserStudentsId;

public interface UserStudentsService
{
    UserStudents save(long userid, long programId);
    void deleteById(UserStudentsId id);
}
