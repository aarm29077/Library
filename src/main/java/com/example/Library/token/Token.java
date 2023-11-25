package com.example.Library.token;

import com.example.Library.models.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter @Setter Long id;

    @Column(name = "token")
    private  @Getter @Setter String token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private  @Getter @Setter TokenType tokenType;

    @Column(name = "expired")
    public  @Getter @Setter boolean expired;

    @Column(name = "revoked")
    public  @Getter @Setter boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public  @Getter @Setter User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return expired == token1.expired && revoked == token1.revoked && Objects.equals(id, token1.id) && Objects.equals(token, token1.token) && Objects.equals(tokenType, token1.tokenType) && Objects.equals(user, token1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, tokenType, expired, revoked, user);
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", tokenType=" + tokenType +
                ", expired=" + expired +
                ", revoked=" + revoked +
                '}';
    }
}
