package gbsw.hs.kr.Health.repository;

import gbsw.hs.kr.Health.model.Signdto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignRepository extends JpaRepository<Signdto,String> {
    Optional<Signdto> findById(String userid);
    Signdto findByName(String userName);

    Optional<Signdto> findByPwd(String userid);
}
