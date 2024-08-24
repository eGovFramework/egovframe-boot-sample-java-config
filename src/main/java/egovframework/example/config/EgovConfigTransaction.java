package egovframework.example.config;

import java.util.Collections;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Transaction 구성
 */
@Configuration
public class EgovConfigTransaction {

	/**
	 * 트랜잭션 관리자
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "txManager")
	public DataSourceTransactionManager txManager(final @Qualifier("dataSource") DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}

	/**
	 * 트랜잭션 조언
	 * 
	 * @param txManager
	 * @return
	 */
	@Bean
	public TransactionInterceptor txAdvice(final DataSourceTransactionManager txManager) {
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

	/**
	 * 트랜잭션 조언
	 * 
	 * @param txManager
	 * @return
	 */
	@Bean
	public Advisor txAdvisor(final @Qualifier("txManager") DataSourceTransactionManager txManager) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* egovframework.example.sample..impl.*Impl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
	}

}
