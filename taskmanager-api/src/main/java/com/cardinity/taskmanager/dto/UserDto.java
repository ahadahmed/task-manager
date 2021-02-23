package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.controllers.rest.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class UserDto {

    @NotBlank
    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, example = "2")
    long id;
    @JsonView(value = {View.TaskResponseView.class})
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "user1")
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
