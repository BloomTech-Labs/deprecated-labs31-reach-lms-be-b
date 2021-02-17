package com.lambdaschool.oktafoundation.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "userstudent")
@IdClass(UserStudentsId.class)
public class UserStudents
        extends Auditable
        implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = {"adminPrograms", "studentPrograms", "teacherPrograms"}, allowSetters = true)
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "programid")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Program program;
    public UserStudents() {}
    public UserStudents(
            User user,
            Program program
    ) {
        this.user    = user;
        this.program = program;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Program getProgram() {
        return program;
    }
    public void setProgram(Program program) {
        this.program = program;
    }
    @Override
    public int hashCode() {
        return 37;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserStudents)) {
            return false;
        }
        UserStudents that = (UserStudents) o;
        return ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
                ((program == null) ? 0 : program.getProgramId()) ==
                        ((that.program == null) ? 0 : that.program.getProgramId());
    }
}