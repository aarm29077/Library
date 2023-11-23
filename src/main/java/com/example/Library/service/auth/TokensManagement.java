package com.example.Library.service.auth;

import com.example.Library.models.forUsers.User;

public interface TokensManagement {
    void saveUserToken(User user, String jwtToken);
    void revokeAllTokens(User user);

}
