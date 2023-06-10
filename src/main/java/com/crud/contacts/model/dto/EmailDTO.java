package com.crud.contacts.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EmailDTO {
    private Long id;

    @NotNull
    @Pattern(regexp = "\\\\S{3,}",message = "Invalid email")
    private String email;

    @NotNull
    @Pattern(regexp = "^[.@]+$", message = "Invalid domain name")
    private String domainName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
