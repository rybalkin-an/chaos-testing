package com.github.rybalkin_an.app.user.model;

import jakarta.validation.constraints.NotEmpty;

public class Subscriptions {

    @NotEmpty(message = "subName should be not empty")
    private String subName;

}
