package com.albanoj2.ooluroo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class OolurooChallengeConfiguration extends Configuration {

	private String test;
	
	@JsonProperty
    public String getTest() {
        return this.test;
    }

    @JsonProperty
    public void setTest(String test) {
        this.test = test;
    }
}
