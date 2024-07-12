package egovframework.example.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;

@Configuration
public class EgovConfigTransaction {

	@Bean(name="txManager")
	public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}

	@Bean
	public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager) {
		RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
		txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

		HashMap<String, TransactionAttribute> txMethods = new HashMap<String, TransactionAttribute>();
		txMethods.put("*", txAttribute);

		NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
		txAttributeSource.setNameMap(txMethods);

		TransactionInterceptor txAdvice = new TransactionInterceptor();
		txAdvice.setTransactionAttributeSource(txAttributeSource);
		txAdvice.setTransactionManager(txManager);

		return txAdvice;
	}

	@Bean
	public Advisor txAdvisor(@Qualifier("txManager") DataSourceTransactionManager txManager) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* egovframework.example.sample..impl.*Impl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
	}

}
