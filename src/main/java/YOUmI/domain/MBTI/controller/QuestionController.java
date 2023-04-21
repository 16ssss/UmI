package YOUmI.domain.MBTI.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import YOUmI.domain.MBTI.service.impl.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import YOUmI.util.enumeration.FAILURE;
import YOUmI.util.enumeration.SUCCESS;
import YOUmI.domain.MBTI.model.dto.MBTIQuestions;
import YOUmI.domain.MBTI.model.dto.Question;
import YOUmI.domain.MBTI.model.dto.Response;
import YOUmI.domain.MBTI.model.dto.TestResult;
import io.swagger.annotations.ApiOperation;

@RestController
//@RequestMapping(value="/MBTI")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    //Swagger Annotation @ApiOperation(value="api value(summary)", notes = "api note")
    // @GetMapping("/posts")
    // public List<Post> selectPostList() {
    //     return postService.selectPostList();
    // }


    //@CrossOrigin("*")
    @GetMapping("/questions")
    @ApiOperation(value="/questions", notes="검사문항을 반환합니다.")
    public ResponseEntity<Response> getMBTIQuestions() throws NoSuchAlgorithmException {

        List<Question> questions = questionService.getSurveyQuestions();

        MBTIQuestions mbtiQuestions = new MBTIQuestions();

        mbtiQuestions.setQuestions(questions);
        mbtiQuestions.setId(SecureRandom.getInstanceStrong().nextInt(1000000));

        Response response = Response.builder()
                                    .result(SUCCESS.SUCCESS)
                                    .resultObject(mbtiQuestions)
                                    .build();

        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin("*")
    @PostMapping("/result/{id}")
    @ApiOperation(value="/result/{id}", notes="검사결과를 저장합니다.")
    public ResponseEntity<Response> saveTestResult(@RequestBody TestResult result, @PathVariable String id) {

        String resultMbti = questionService.saveTestResult(id,result);
        Response response;

        if(StringUtils.isNotBlank(resultMbti)) {
            response = Response.builder()
                    .result(SUCCESS.SUCCESS)
                    .resultObject(Map.of("mbti",resultMbti))
                    .build();
            return ResponseEntity.ok().body(response);
        } else {
            response = Response.builder()
                    .result(FAILURE.FAILURE)
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }


    }

    //@CrossOrigin("*")
    //@PatchMapping(value="/questions", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @PatchMapping(value="/questions", consumes={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="/questions", notes="MBTI 문항을 업데이트합니다.")
    public ResponseEntity<Response> patchQuestions(@RequestBody List<Question> questions) {

        if(questions == null || questions.size() == 0){
            return ResponseEntity.badRequest().body(Response.builder().result(FAILURE.ABSENT_REQUIRED_VALUE).build());
        }
        boolean result = questionService.updateQuestions(questions);
        Response response;

        if(result) {
            response = Response.builder()
                    .result(SUCCESS.SUCCESS)
                    .build();
            return ResponseEntity.ok().body(response);
        } else {
            response = Response.builder()
                    .result(FAILURE.FAILURE)
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }

    }

    //@CrossOrigin("*")
    @PostMapping(value="/questions", consumes={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="/questions", notes="MBTI 문항을 추가합니다.")
    public ResponseEntity<Response> addNewQuestions(@RequestBody List<Question> questions) {

        if(questions == null || questions.size() == 0){
            return ResponseEntity.badRequest().body(Response.builder().result(FAILURE.ABSENT_REQUIRED_VALUE).build());
        }
        boolean result = questionService.insertQuestions(questions);
        Response response;

        if(result) {
             response = Response.builder()
                    .result(SUCCESS.SUCCESS)
                    .build();
            return ResponseEntity.ok().body(response);
        } else {
            response = Response.builder()
                    .result(FAILURE.FAILURE)
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @DeleteMapping(value="/questions", consumes={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="/questions", notes="MBTI 문항을 삭제합니다.")
    public ResponseEntity<Response> deleteQuestions(@RequestBody List<Integer> questionSequences) {

        if(questionSequences == null || questionSequences.size() == 0){
            return ResponseEntity.badRequest().body(Response.builder().result(FAILURE.ABSENT_REQUIRED_VALUE).build());
        }
        List<Integer> successes = questionService.deleteQuestions(questionSequences);
        Response response = Response.builder()
                .result(SUCCESS.SUCCESS)
                .resultObject(Map.of("deletedList",successes))
                .build();

        return ResponseEntity.ok().body(response);

    }

    //@CrossOrigin("*")
    @GetMapping("/questions.html")
    @ApiOperation(value="/questions.html", notes="검사문항을 보여주는 임시 html 입니다.")
    public ModelAndView getQuestions() {

        ModelAndView mv = new ModelAndView("questions.html");

        try{
            List<Question> questions = questionService.getSurveyQuestions();

            questions = questions.stream().sorted((q1,q2)->q1.getSeq()-q2.getSeq()).collect(Collectors.toList());
            mv.addObject("questions",questions);
                                        
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        return mv;

    }

}

