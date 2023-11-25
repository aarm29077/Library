package com.example.Library.service.auth;

import com.example.Library.models.users.User;

public interface TokensManagement {
    void saveUserToken(User user, String jwtToken);
    void revokeAllTokens(User user);

}
