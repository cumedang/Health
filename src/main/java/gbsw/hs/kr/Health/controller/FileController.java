package gbsw.hs.kr.Health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping("/")
    public String indexController() {
        return "index";
    }
}
