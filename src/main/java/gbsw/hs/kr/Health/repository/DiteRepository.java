package gbsw.hs.kr.Health.repository;

import gbsw.hs.kr.Health.model.Ditedto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiteRepository extends JpaRepository<Ditedto,String> {
    @Override
    Page<Ditedto> findAll(Pageable pageable);



    Ditedto findById(int id);

    void deleteById(int id);
}
