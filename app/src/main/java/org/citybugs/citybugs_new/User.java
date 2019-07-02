package org.citybugs.citybugs_new;

public class User {
    String email;
    String password;
    String client_secret;
    String client_id;
    String network;
    String grant_type;

    String token_type;
    String expires_in;
    String access_token;
    String refresh_token;

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public User(String email, String password, String client_secret,
                String client_id, String network, String grant_type) {
        this.email = email;
        this.password = password;
        this.client_secret = client_secret;
        this.client_id = client_id;
        this.network = network;
        this.grant_type = grant_type;
    }

}
