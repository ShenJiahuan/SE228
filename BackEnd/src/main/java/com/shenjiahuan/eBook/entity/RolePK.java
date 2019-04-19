package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RolePK implements Serializable {
    private int uid;
    private String role;

    @Column(name = "uid")
    @Id
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "role")
    @Id
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePK rolePK = (RolePK) o;
        return uid == rolePK.uid &&
                Objects.equals(role, rolePK.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, role);
    }
}
