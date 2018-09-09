package com.frank.cloud.message.common.model;

/**
 * Created by  Frank on 2018-1-17.
 */
public class RoleInfoDTO {
    private Long roleId;
    private String roleName;
    private String roleTag;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleTag() {
        return roleTag;
    }

    public void setRoleTag(String roleTag) {
        this.roleTag = roleTag;
    }
}
