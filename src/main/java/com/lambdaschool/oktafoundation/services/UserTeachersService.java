package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserTeachers;

public interface UserTeachersService {
    UserTeachers save(long userid, long programid);
}
