package com.heng.blog_system.bean;

import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String roleCode;

    private String roleName;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
