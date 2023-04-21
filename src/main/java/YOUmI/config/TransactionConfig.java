package YOUmI.config;

import java.util.Collections;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Configuration
public class TransactionConfig {
    private final String AOP_TRANSACTION_MEHTOD_NAME = "*";
    private final String AOP_TRANSACTION_EXPRESSION = "execution(* *..service.*Service.*(..))";

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice(){
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
        txAttribute.setName(AOP_TRANSACTION_MEHTOD_NAME);
        txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(txAttribute);

        return new TransactionInterceptor(transactionManager, source);
    }
    
    @Bean
    public Advisor txAdviceAdvisor(){
        AspectJExpressionPointcut pt = new AspectJExpressionPointcut();
        pt.setExpression(AOP_TRANSACTION_EXPRESSION);

        return new DefaultPointcutAdvisor(pt, txAdvice());
    }

}
