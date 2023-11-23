package com.example.Library.token;

import com.example.Library.util.customExceptions.relatedToToken.TokenAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public Token saveToken(Token token) {
        if (tokenRepository.findByToken(token.getToken()).isPresent()) {
            throw new TokenAlreadyExistsException("Token : " + token.getToken() + " already exists");
        }
        return tokenRepository.save(token);
    }
    @Transactional
    public Token updateToken(Token token) {
        return tokenRepository.save(token);
    }

    public List<Token> findAllValidTokensByUser(Long userId) {
        return tokenRepository.findAllValidTokensByUser(userId);
    }

    @Transactional
    public void saveAllTokens(List<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }

    public Optional<Token> findByToken(String token){
        return tokenRepository.findByToken(token);
    }
}
