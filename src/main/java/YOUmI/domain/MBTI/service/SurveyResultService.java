package YOUmI.domain.MBTI.service;

import YOUmI.domain.MBTI.model.dto.Statistics.CitationDto;
import YOUmI.domain.MBTI.model.entity.MbtiSurveyResult;
import YOUmI.domain.MBTI.repository.QuestionRepository;
import YOUmI.domain.MBTI.repository.SurveyResultRepository;
import YOUmI.domain.MBTI.service.impl.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SurveyResultService {

    private SurveyResultRepository surveyResultRepository;

    private QuestionRepository questionRepository;

    private QuestionService questionService;

    public Long getResultCount() {
        return surveyResultRepository.findAll().stream().map(MbtiSurveyResult::getId).distinct().count();
    }

    public List<CitationDto> getQuestionCitations() {
        List<CitationDto> result = new ArrayList<>();

        List<MbtiSurveyResult> surveyResults = surveyResultRepository.findAll();

        List<Integer> questionSeqs = surveyResults.stream().map(MbtiSurveyResult::getSeq).distinct().toList();

        //questionRepository.findByIdIn(questionIds);
        for(Integer seq : questionSeqs) {
            result.add(
                    CitationDto
                            .builder()
                            .question(questionService.getQuestionBySeq(seq))
                            .count(surveyResults.stream().map(MbtiSurveyResult::getSeq).filter(seq::equals).count())
                            .build()
            );
        }
        return result;
    }

}
