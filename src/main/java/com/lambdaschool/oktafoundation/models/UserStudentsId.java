package com.lambdaschool.oktafoundation.models;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class UserStudentsId
        implements Serializable {
    private long user;
    private long program;
    public UserStudentsId() {}

    public UserStudentsId(long user, long program)
    {
        this.user = user;
        this.program = program;
    }

    public long getUser() {
        return user;
    }
    public void setUser(long user) {
        this.user = user;
    }
    public long getProgram() {
        return program;
    }
    public void setProgram(long program) {
        this.program = program;
    }
    @Override
    public int hashCode() {
        return 37;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserStudentsId that = (UserStudentsId) o;
        return getUser() == that.getUser() && getProgram() == that.getProgram();
    }
}