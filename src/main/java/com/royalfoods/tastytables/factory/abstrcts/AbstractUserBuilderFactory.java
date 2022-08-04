package com.royalfoods.tastytables.factory.abstrcts;

import java.util.*;

public abstract class AbstractUserBuilderFactory <T,U> extends AbstractModelBuilderFactory{
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;


    public AbstractUserBuilderFactory withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public AbstractUserBuilderFactory withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AbstractUserBuilderFactory withEmail(String email) {
        this.email = email;
        return this;
    }


    protected abstract AbstractUserBuilderFactory withUser(U user);
    protected abstract AbstractUserBuilderFactory withRoles(U roles);
}
