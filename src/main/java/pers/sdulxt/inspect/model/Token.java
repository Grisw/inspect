package pers.sdulxt.inspect.model;

import java.util.Date;

public class Token {
    private String token;
    private Date expire;

    public Token(String token, Date expire) {
        this.token = token;
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
