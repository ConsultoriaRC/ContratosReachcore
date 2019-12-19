package com.rc.mongo;

import org.bson.types.ObjectId;

public final class UserPojo {
    private ObjectId id;
    private String correo;
    private String accountId;
    private String userId;
    private String vendor;
    private String passw;

    public UserPojo() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(final String accountId) {
        this.accountId= accountId;
    }
    
    public String getVendor() {
        return vendor;
    }

    public void setVendor(final String vendor) {
        this.vendor= vendor;
    }
    public String getPassw() {
        return passw;
    }

    public void setPassw(final String passw) {
        this.passw= passw;
    }
    // Rest of implementation
}