package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserTeachers;
import com.lambdaschool.oktafoundation.models.UserTeachersId;

public interface UserTeachersService {
    UserTeachers save(long userid, long programid);
    void deleteById(UserTeachersId id);
}
