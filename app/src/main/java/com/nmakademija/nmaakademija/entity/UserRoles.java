package com.nmakademija.nmaakademija.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dekedro on 16.8.24.
 */
public enum UserRoles {
    @SerializedName("teacher")
    TEACHER,
    @SerializedName("member")
    MEMBER,
    @SerializedName("guest")
    GUEST,
    @SerializedName("supervisor")
    SUPERVISOR,
    @SerializedName("administrator")
    ADMINISTRATOR,
}
