package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.dto.EvaluationDto;
import cz.upce.nnpia.sem.entity.Evaluation;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.service.EvaluationService;
import cz.upce.nnpia.sem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/evaluation")
@CrossOrigin
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final UserService userService;

    public EvaluationController(EvaluationService evaluationService, UserService userService) {
        this.evaluationService = evaluationService;
        this.userService = userService;
    }



    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Evaluation> findedEvaluations = evaluationService.getAllEvaluations();
        if(findedEvaluations==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedEvaluations.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<?> getAllToUser(@PathVariable String userEmail){
        List<Evaluation> findedEvaluations = evaluationService.getAllEvaluationsToUser(userEmail);
        if(findedEvaluations==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedEvaluations.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getAllToRestaurant(@PathVariable int restaurantId){
        List<Evaluation> findedEvaluations = evaluationService.getAllEvaluationsToRestaurant(restaurantId);
        if(findedEvaluations==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedEvaluations.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        Evaluation evaluation = evaluationService.getById(id);
        if(evaluation==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(evaluation),HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EvaluationDto evaluationDto){
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
            user = userService.getByEmail(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername());
        }
        if(user!=null){
            evaluationDto.setUserId(user.getId());
        }
        Evaluation createdEvaluation = evaluationService.create(convertToEntity(evaluationDto),evaluationDto.getRestaurantId(),evaluationDto.getUserId());
        if(createdEvaluation==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(createdEvaluation),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody EvaluationDto evaluationDto,@PathVariable int id){
        Evaluation updatedEvaluation = evaluationService.update(convertToEntity(evaluationDto),evaluationDto.getRestaurantId(),evaluationDto.getUserId(),id);
        if(updatedEvaluation==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(updatedEvaluation),HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Evaluation deletedEvaluation = evaluationService.delete(id);
        if(deletedEvaluation==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(deletedEvaluation),HttpStatus.OK);
    }

    private EvaluationDto convertToDto(Evaluation evaluation){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(evaluation, EvaluationDto.class);
    }

    private Evaluation convertToEntity(EvaluationDto evaluationDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(evaluationDto, Evaluation.class);
    }
}
