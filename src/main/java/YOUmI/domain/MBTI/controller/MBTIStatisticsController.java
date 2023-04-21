package YOUmI.domain.MBTI.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import YOUmI.domain.MBTI.model.dto.Statistics.CitationDto;
import YOUmI.domain.MBTI.model.dto.Statistics.ResultCountDto;
import YOUmI.domain.MBTI.service.SurveyResultService;
import YOUmI.domain.User.model.entity.Customer;
import YOUmI.domain.User.service.impl.CustomerService;
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
import YOUmI.domain.MBTI.service.impl.MBTIQuestionService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/MBTI")
@Slf4j
@RequiredArgsConstructor
public class MBTIStatisticsController {

    private final MBTIQuestionService questionService;

    private final CustomerService customerService;

    private final SurveyResultService surveyResultService;


    @GetMapping("/result/count")
    @ApiOperation(value="/result/count", notes="설문조사 응시횟수를 조회합니다.")
    public ResultCountDto getSurveyCount() {
        return ResultCountDto.builder().count(surveyResultService.getResultCount()).build();
    }

    @GetMapping("/citations")
    @ApiOperation(value="/citations", notes="설문조사 문항 별 인용횟수를 조회합니다.")
    public List<CitationDto> getQuestionCitations() {
        return surveyResultService.getQuestionCitations();
    }

//    @GetMapping("/responses")
//    @ApiOperation(value="/responses", notes="설문조사 문항 별 응답결과를 조회합니다.")
//    public List<QuestionResponse> getQuestionReponses() {
//
//    }

    @GetMapping("/results")
    @ApiOperation(value="/results", notes="설문조사 응시자 목록을 조회합니다.")
    public List<Customer> getSurveyUsers() {
        return customerService.findAllCustomer();
    }

//    @GetMapping("/")
//    @ApiOperation(value="/", notes="설문조사 결과 일치율을 조회합니다.")
//    public ResponseEntity<Response> getBouncRate() {
//
//    }

    //bounce rate = 이탈률
//    @GetMapping("/bounces")
//    @ApiOperation(value="/bounces", notes="설문조사 중 이탈률을 조회합니다.")
//    public void getBouncRate() {
//        //TODO
//        //이탈여부 데이터 필요
//    }

}

