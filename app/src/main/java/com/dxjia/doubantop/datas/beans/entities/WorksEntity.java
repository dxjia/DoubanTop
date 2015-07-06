package com.dxjia.doubantop.datas.beans.entities;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/29.
 */
public class WorksEntity {
    /**
     * subject : {"images":{...}
     * roles : ["演员"]
     */
    private SubjectEntity subject;
    private List<String> roles;

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public List<String> getRoles() {
        return roles;
    }
}