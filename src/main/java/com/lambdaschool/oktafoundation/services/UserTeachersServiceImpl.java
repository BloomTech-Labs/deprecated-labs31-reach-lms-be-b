package com.lambdaschool.oktafoundation.services;
import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserTeachers;
import com.lambdaschool.oktafoundation.models.UserTeachersId;
import com.lambdaschool.oktafoundation.repository.UserTeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userTeachersService")
public class UserTeachersServiceImpl implements UserTeachersService
{
    @Autowired
    UserTeachersRepository userTeachersRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProgramService programService;

    @Override
    public UserTeachers save(long userid, long programid)
    {
        User user = userService.findUserById(userid);
        Program program = programService.findProgramById(programid);
        UserTeachers userTeachers = userTeachersRepository.findById(new UserTeachersId(user.getUserid(), program.getProgramId()))
                .orElse(new UserTeachers(user, program));

        return userTeachersRepository.save(userTeachers);
    }

    @Override
    public void deleteById(UserTeachersId id) {
        userTeachersRepository.deleteById(id);

    }
}
