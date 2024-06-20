package gbsw.hs.kr.Health.service;

import gbsw.hs.kr.Health.model.*;
import gbsw.hs.kr.Health.repository.DiteRepository;
import gbsw.hs.kr.Health.repository.FoodsRepositroy;
import gbsw.hs.kr.Health.repository.SignRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final SignRepository signRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public MemberService(SignRepository signRepository, FoodsRepositroy foodsRepositroy, BCryptPasswordEncoder passwordEncoder, DiteRepository diteRepository) {
        this.signRepository = signRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Suceessdto signService(Signdto signdto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        Signdto newUser = new Signdto();
        newUser.setId(signdto.getId());
        // 비밀번호를 암호화하여 저장
        newUser.setPwd(passwordEncoder.encode(signdto.getPwd()));
        newUser.setName(signdto.getName());
        // 권한 설정 (예: ROLE_USER)
        newUser.setRole(Role.valueOf("ROLE_USER"));

        // 새로운 사용자 정보를 데이터베이스에 저장
        signRepository.save(newUser);
        suceessdto.setSucess(true);
        return suceessdto;
    }

    public Suceessdto loginService( Signdto signdto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        System.out.println(signdto.getId());
        if(!findByid(signdto.getId())) {
            Optional<Signdto> pw = signRepository.findById(signdto.getId());
            if(BCrypt.checkpw(signdto.getPwd(),String.valueOf(pw.get().getPwd()) )) {
                suceessdto.setSucess(true);
                return suceessdto;
            }
        }
        return suceessdto;
    }



    public boolean findByid(String id) {
        Optional<Signdto> signdto = signRepository.findById(id);
        if(signdto.isEmpty()) {
            return true;
        }
        return false;
    }



    public Suceessdto ChangePassword(ChangeDto changeDto,String usenid) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        System.out.println(usenid);
        Optional<Signdto> byName = signRepository.findById(usenid);
        System.out.println(byName);
        if(passwordEncoder.matches(changeDto.getPassword(),byName.get().getPwd())) {
            byName.get().setPwd(passwordEncoder.encode(changeDto.getNewPassword()));
            signRepository.save(byName.get());
            suceessdto.setSucess(true);
        }
        return suceessdto;

    }
}
