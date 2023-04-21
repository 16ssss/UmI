package YOUmI.domain.MBTI.service.impl;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import YOUmI.domain.MBTI.model.dto.Question;
import YOUmI.domain.MBTI.model.dto.TestResult;
import YOUmI.domain.MBTI.model.dto.TestItem;
import YOUmI.domain.User.model.entity.Customer;
import YOUmI.domain.MBTI.model.entity.MbtiQuestion;
import YOUmI.domain.MBTI.model.entity.MbtiSurveyResult;
import YOUmI.domain.User.repository.CustomerRepository;
import YOUmI.domain.MBTI.repository.QuestionRepository;
import YOUmI.domain.MBTI.repository.SurveyResultRepository;

@Service
@Log4j2
public class MBTIQuestionService {
    

}
