package gbsw.hs.kr.Health.controller;

import gbsw.hs.kr.Health.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class FileController {
    private final JwtTokenProvider jwtTokenProvider;

    public FileController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/")
    public String indexController() {
        return "index";
    }

    @GetMapping("/login")
    public String loginController() {
        return "login";
    }

    @GetMapping("/sign")
    public String signControler() {
        return "signup";
    }

    @GetMapping("/dite")
    public String moludiController(HttpServletRequest request) {
        String token = null;
        System.out.println("request");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {

                    token = cookie.getValue();

                }
            }
        }

        if (token != null && isValidToken(token)) {
            return "moludi";
        } else {
            System.out.println("에러");
            return "error"; // 에러 페이지로 리디렉션하거나 다른 처리를 할 수 있습니다.
        }

    }

    private boolean isValidToken(String token) {
        try {
            // 여기서 jwtTokenProvider의 validateToken 메서드를 호출하여 토큰을 검증합니다.
            return jwtTokenProvider.validateToken(token);
        } catch (Exception e) {
            return false;
        }

    }
}
