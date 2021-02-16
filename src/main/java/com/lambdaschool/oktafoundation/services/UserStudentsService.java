package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.UserStudents;

public interface UserStudentsService
{
    UserStudents save(long userid, long programId);
}
