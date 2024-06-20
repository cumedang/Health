package gbsw.hs.kr.Health.controller;

import gbsw.hs.kr.Health.model.*;
import gbsw.hs.kr.Health.repository.DiteRepository;
import gbsw.hs.kr.Health.service.FoodsProcessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodsController {

    private final FoodsProcessService foodsProcessService;
    private final DiteRepository diteRepository;

    public FoodsController(FoodsProcessService foodsProcessService, DiteRepository diteRepository) {
        this.foodsProcessService = foodsProcessService;
        this.diteRepository = diteRepository;
    }

    @GetMapping("/foods")
    public List<FoodDto> FoddsHandler() {
        return foodsProcessService.FoodsService();
    }

    @PostMapping("/dite")
    public Suceessdto DiteProcess(@RequestBody Ditedto ditedto) {
        return foodsProcessService.DiteService(ditedto);
    }

    @GetMapping("/finddate")
    public List<TextDto> FindDateHandler(@RequestParam("id") int id) {
        return foodsProcessService.FIndDateService(id);
    }

    @GetMapping("/list")
    public Page<Ditedto> FindList(Pageable pageable) {
        return diteRepository.findAll(pageable);
    }

    @PostMapping("/update")
    public Suceessdto UpdateProcess(@RequestBody Ditedto ditedto) {
        return foodsProcessService.UpdateService(ditedto);
    }

    @PostMapping("/delete")
    public Suceessdto DeleteProcess(@RequestParam("id") int id) {
        return foodsProcessService.DeleteService(id);
    }

    @PostMapping("/comment")
    public Suceessdto CreateComment(@RequestBody CommentDto commentDto) {
        return foodsProcessService.CreateCommentService(commentDto);
    }

    @PostMapping("/delete/comment")
    public Suceessdto DeleteComment(@RequestParam("id") int id) {
        return foodsProcessService.DeleteCommentService(id);
    }



}
