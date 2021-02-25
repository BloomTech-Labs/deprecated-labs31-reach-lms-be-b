package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserStudents;
import com.lambdaschool.oktafoundation.models.UserStudentsId;
import com.lambdaschool.oktafoundation.repository.UserStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userStudentsService")
public class UserStudentsServiceImpl implements UserStudentsService
{
    @Autowired
    UserStudentsRepository userStudentsRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProgramService programService;

    @Override
    public UserStudents save(long userid, long programId)
    {
        User user = userService.findUserById(userid);
        Program program = programService.findProgramById(programId);
        UserStudents userStudents = userStudentsRepository.findById(new UserStudentsId(user.getUserid(), program.getProgramId())).orElse(new UserStudents(user, program));

        return userStudentsRepository.save(userStudents);
    }

    @Override
    public void deleteById(UserStudentsId id) {
        userStudentsRepository.deleteById(id);
    }
}
