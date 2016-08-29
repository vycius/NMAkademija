package com.nmakademija.nmaakademija.entity;

import com.google.gson.annotations.SerializedName;

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
