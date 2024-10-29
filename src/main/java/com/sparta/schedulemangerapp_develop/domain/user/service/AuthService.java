package com.sparta.schedulemangerapp_develop.domain.user.service;

import com.sparta.schedulemangerapp_develop.common.auth.JwtUtil;
import com.sparta.schedulemangerapp_develop.common.auth.PasswordEncoder;
import com.sparta.schedulemangerapp_develop.domain.user.dto.AuthRequestDto;
import com.sparta.schedulemangerapp_develop.domain.user.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.user.entity.UserRoleEnum;
import com.sparta.schedulemangerapp_develop.domain.user.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(AuthRequestDto requestDto, HttpServletResponse response) {

        requestDto.initPassword(passwordEncoder.encode(requestDto.getPassword()));
        Optional<Member> checkEmail = memberRepository.findByEmail(requestDto.getEmail());
        if(checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }

        UserRoleEnum role = UserRoleEnum.getRole(requestDto.isAdmin());
        Member signMember = Member.from(requestDto, role);

        // 사용자 등록
        Member savedUser = memberRepository.save(signMember);

        // 토큰 생성
        String token = jwtUtil.createToken(savedUser.getEmail(), savedUser.getRole());
        jwtUtil.addJwtToCookie(token, response);
    }

}
