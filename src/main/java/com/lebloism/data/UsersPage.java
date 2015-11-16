package com.lebloism.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.Serializable;

@SuppressWarnings("serial")
@Component
@Scope("view")
public class UsersPage implements Serializable {

    protected static final String USER_ID = "user_id";
    protected static final String VIEW_ONLY = "VIEW_ONLY";
    private static final String USER_EDIT_URL = "/users/edit.xhtml?faces-redirect=true";

    public String createNew() {
        putFlashParameter(USER_ID, null);
        putFlashParameter(VIEW_ONLY, false);
        return USER_EDIT_URL;
    }


    public void putFlashParameter(String key, Object value) {
        // Le FacesContext peut être nul dans les tests JUnit
        if (FacesContext.getCurrentInstance() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);
        }
    }


}
