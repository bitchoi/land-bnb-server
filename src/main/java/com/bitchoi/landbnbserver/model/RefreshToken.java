package com.bitchoi.landbnbserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    private int refreshTokenId;

    private String refreshToken;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_refresh_token_member"))
    private Member member;

    private Date expiredOn;

    @Builder
    public RefreshToken(String refreshToken, Member member, Date expiredOn) {
        this.refreshToken = refreshToken;
        this.member = member;
        this.expiredOn = expiredOn;
    }
}
