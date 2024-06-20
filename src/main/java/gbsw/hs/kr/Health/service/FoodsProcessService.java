package gbsw.hs.kr.Health.service;

import gbsw.hs.kr.Health.model.*;
import gbsw.hs.kr.Health.repository.CommentRepository;
import gbsw.hs.kr.Health.repository.DiteRepository;
import gbsw.hs.kr.Health.repository.FoodsRepositroy;
import gbsw.hs.kr.Health.repository.SignRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class FoodsProcessService {
    private final FoodsRepositroy foodsRepositroy;
    private final DiteRepository diteRepository;

    private final CommentRepository commentRepository;

    private final SignRepository signRepository;

    public FoodsProcessService(FoodsRepositroy foodsRepositroy, DiteRepository diteRepository, CommentRepository commentRepository, SignRepository signRepository) {
        this.foodsRepositroy = foodsRepositroy;
        this.diteRepository = diteRepository;
        this.commentRepository = commentRepository;
        this.signRepository = signRepository;
    }

    public List<FoodDto> FoodsService() {
        return foodsRepositroy.findAll();
    }

    public Suceessdto DiteService(Ditedto ditedto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        Ditedto ditedto1 = diteRepository.save(ditedto);
        if (ditedto1 != null) {
            suceessdto.setSucess(true);
            return suceessdto;
        }
        return suceessdto;
    }

    public List<TextDto> FIndDateService(int id) {

        List<TextDto> textDtos = new ArrayList<>();
        Ditedto all = diteRepository.findById(id);
        String userid = all.getUsername();
        Optional<Signdto> byId = signRepository.findById(userid);
        String foodname = all.getFoodname();
        List<FoodDto> byFoodName = foodsRepositroy.findByFoodnameContains(foodname);
        List<CommentDto> commentDtoList = commentRepository.findAllByTextid(id);
        TextDto textDto = new TextDto();
        textDto.setCalories(byFoodName.get(0).getCalories());
        textDto.setProv(byFoodName.get(0).getProv());
        textDto.setProt(byFoodName.get(0).getProt());
        textDto.setTan(byFoodName.get(0).getTan());
        textDto.setVita(byFoodName.get(0).getVita());
        textDto.setDate(all.getDay());
        textDto.setMoludi(all.getMoludi());
        textDto.setFoodName(all.getFoodname());
        textDto.setUserid(all.getUsername());
        textDto.setUsername(byId.get().getName());
        textDto.setCommentList(commentDtoList);
        textDtos.add(textDto);

        return textDtos;
    }

    public Suceessdto UpdateService(Ditedto ditedto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        Ditedto allById = diteRepository.findById(ditedto.getId());
        if(ditedto.getDay() != null) {
            allById.setDay(ditedto.getDay());
        }
        if(ditedto.getFoodname() != null){
            allById.setFoodname(ditedto.getFoodname());
        }
        if(ditedto.getMoludi() != null) {
            allById.setMoludi(ditedto.getMoludi());
        }
        diteRepository.save(allById);
        suceessdto.setSucess(true);
        return suceessdto;
    }
    public Suceessdto DeleteService(int id) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        diteRepository.deleteById(id);
        suceessdto.setSucess(true);
        return suceessdto;
    }

    public Suceessdto CreateCommentService(CommentDto commentDto) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        commentRepository.save(commentDto);
        suceessdto.setSucess(true);
        return suceessdto;
    }
    public Suceessdto DeleteCommentService(int id) {
        Suceessdto suceessdto = new Suceessdto();
        suceessdto.setSucess(false);
        commentRepository.deleteById(id);
        suceessdto.setSucess(true);
        return suceessdto;
    }
}
