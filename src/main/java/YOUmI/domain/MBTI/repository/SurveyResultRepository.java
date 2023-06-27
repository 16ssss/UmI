package YOUmI.domain.MBTI.repository;

import YOUmI.domain.MBTI.model.entity.MbtiSurveyResultId;
import org.springframework.data.jpa.repository.JpaRepository;

import YOUmI.domain.MBTI.model.entity.MbtiSurveyResult;

public interface SurveyResultRepository extends JpaRepository<MbtiSurveyResult, MbtiSurveyResultId>{

    //Long findDistinctAllById();

}
