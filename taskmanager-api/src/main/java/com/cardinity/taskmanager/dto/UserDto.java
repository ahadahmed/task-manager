package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.controllers.rest.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class UserDto {

    @JsonView(value = {View.HttpMethodView.PUT.class})
    long id;

    String userName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
