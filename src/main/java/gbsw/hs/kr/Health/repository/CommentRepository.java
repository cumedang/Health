package gbsw.hs.kr.Health.repository;

import gbsw.hs.kr.Health.model.CommentDto;
import gbsw.hs.kr.Health.model.Ditedto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<CommentDto,String> {
    List<CommentDto> findAllByTextid(int id);

    void deleteById(int id);
}
