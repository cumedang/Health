package gbsw.hs.kr.Health.service;

import gbsw.hs.kr.Health.model.Signdto;
import gbsw.hs.kr.Health.model.Suceessdto;
import gbsw.hs.kr.Health.repository.SignRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final SignRepository signRepository;

    public MemberService(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public Suceessdto signService(Signdto signdto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        if(findByid(signdto.getId())) {
            if(findByName(signdto.getName())) {
                String hashedPassword = BCrypt.hashpw(signdto.getPw(), BCrypt.gensalt());
                signdto.setPw(hashedPassword);
                signRepository.save(signdto);
                suceessdto.setSucess(true);
                System.out.println(suceessdto.getSucess());
                return suceessdto;
            }
        }else {
            return suceessdto;
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

    public boolean findByName(String name) {
        Optional<Signdto> signdto = signRepository.findByName(name);
        if(signdto.isEmpty()) {
            return true;
        }
        return false;
    }
}
