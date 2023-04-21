package YOUmI.domain.MBTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import YOUmI.domain.MBTI.model.entity.MbtiQuestion;

@Repository
public interface QuestionRepository extends JpaRepository<MbtiQuestion, Integer>{

    // public List<Question> getQuestions() throws Exception;
 
    // public void saveResult(String id, TestResult result) throws Exception;

    //JPQL 쓸 때는 테이블명이 아니라 entity 이름을 써줘야함
    @Query("SELECT MAX(q.seq) FROM MbtiQuestion as q")
    public Integer findMaxSeq();

    public MbtiQuestion findBySeqIn(List<Integer> seqs);
    
}
