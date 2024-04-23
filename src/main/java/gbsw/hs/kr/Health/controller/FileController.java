package gbsw.hs.kr.Health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

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

    @GetMapping("/diet")
    public String moludiController() { return  "moludi";}

}
