package gbsw.hs.kr.Health.repository;

import gbsw.hs.kr.Health.model.FoodDto;
import gbsw.hs.kr.Health.model.Signdto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodsRepositroy extends JpaRepository<FoodDto,String> {
    List<FoodDto> findByFoodnameContains(String foodName);


}
