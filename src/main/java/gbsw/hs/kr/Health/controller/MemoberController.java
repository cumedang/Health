package gbsw.hs.kr.Health.controller;

import gbsw.hs.kr.Health.jwt.JwtTokenProvider;
import gbsw.hs.kr.Health.model.*;
import gbsw.hs.kr.Health.service.MemberService;
import gbsw.hs.kr.Health.service.MyUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemoberController {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService userDetailsService;
    @PostMapping("/sign")
    public Suceessdto signProcess(@RequestBody Signdto signdto) {
        return memberService.signService(signdto);
    }
    @PostMapping("/login")
    public ResponseEntity<Suceessdto> loginProcess(@RequestBody Signdto signdto, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(signdto.getId());
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        String accessToken = jwtTokenProvider.createToken(signdto.getId(), userDetails.getAuthorities().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(signdto.getId());
        if(memberService.loginService(signdto).getSucess().equals(true)) {
            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setHttpOnly(true);
            //accessTokenCookie.setSecure(true); // HTTPS에서만 사용
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge((int) jwtTokenProvider.getAccessExpirationMs() / 1000); // 만료 시간 설정

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            //refreshTokenCookie.setSecure(true); // HTTPS에서만 사용
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge((int) jwtTokenProvider.getRefreshExpirationMs() / 1000); // 만료 시간 설정

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);


            if(ResponseEntity.ok().equals("")) {
                suceessdto.setSucess(false);
            }else {
                suceessdto.setSucess(true);
            }
        }



        return ResponseEntity.ok().body(suceessdto);
    }

    @PostMapping("/change")
    public Suceessdto ChangePassword(@RequestBody ChangeDto changeDto , Authentication authentication) {
        return memberService.ChangePassword(changeDto, authentication.getName());
    }


}
