package com.bitchoi.landbnbserver.service;

import com.bitchoi.landbnbserver.constant.ErrorCode;
import com.bitchoi.landbnbserver.dto.JwtRequest;
import com.bitchoi.landbnbserver.dto.JwtResponse;
import com.bitchoi.landbnbserver.exception.BusinessException;
import com.bitchoi.landbnbserver.model.Member;
import com.bitchoi.landbnbserver.model.RefreshToken;
import com.bitchoi.landbnbserver.repository.MemberRepository;
import com.bitchoi.landbnbserver.repository.RefreshTokenRepository;
import com.bitchoi.landbnbserver.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final MemberRepository memberRepo;

    private final RefreshTokenRepository refreshTokenRepo;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refreshToken.duration}")
    private long REFRESH_TOKEN_DURATION;

    public Optional<Member> emailAndPassword(String email, String password){
        Optional<Member> optMember = memberRepo.findByEmail(email);
        if(optMember.isPresent()){
            Member member = optMember.get();
            return passwordEncoder.matches(password, member.getPassword()) ? optMember : Optional.empty();
        }
        return Optional.empty();
    }

    public JwtResponse authenticateWithToken(JwtRequest jwtRequest) {
        Member member;
        switch (jwtRequest.getGrantType()){
            case CLIENT_CREDENTIALS:
                String email = jwtRequest.getEmail();
                member = emailAndPassword(email, jwtRequest.getPassword()).orElseThrow(() -> new BusinessException(ErrorCode.WRONG_EMAIL_OR_PASSWORD));
                break;
            case REFRESH_TOKEN:
                var refreshToken =
                        refreshTokenRepo.findByRefreshToken(jwtRequest.getRefreshToken()).orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
                if (refreshToken.getExpiredOn().before(new Date()))
                    throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
                member = refreshToken.getMember();
                if (member == null)
                    throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
                break;
            default:
                throw new IllegalStateException("Grant type cannot be null");
        }
        return processJwtResponse(member);
    }

    private JwtResponse processJwtResponse(Member member) {
        final String email = member.getEmail();
        var res = jwtUtils.generateToken(email);
        var usrRefToken = refreshTokenRepo.findById(member.getMemberId());
        if (usrRefToken.isPresent() && usrRefToken.get().getExpiredOn().after(new Date())) {
            log.debug("Found a valid refresh token of member with email {}", email);
            res.setRefreshToken(usrRefToken.get().getRefreshToken());
        } else {
            if (usrRefToken.isPresent()) {
                refreshTokenRepo.deleteById(member.getMemberId());
            }
            log.debug("Did not find a valid refresh token or refresh token expired, creating a new refresh token for " +
                    "member with email: {}", email);
            res.setRefreshToken(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            Date expiredDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_DURATION * 1000);
            RefreshToken refTokenEnt = new RefreshToken(res.getRefreshToken(), member, expiredDate);
            refreshTokenRepo.save(refTokenEnt);
            log.debug("A refresh token was created successfully with expiredDate {} for member with email {}",
                    expiredDate, email);
        }

        return res;
    }
}
