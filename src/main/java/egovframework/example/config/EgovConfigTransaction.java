package egovframework.example.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import lombok.NoArgsConstructor;

/**
 * Transaction 구성
 */
@Configuration
@NoArgsConstructor
public class EgovConfigTransaction {

	/**
	 * 트랜잭션 관리자
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "txManager")
	public DataSourceTransactionManager txManager(final @Qualifier("dataSource") DataSource dataSource) {
		final DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}

	/**
	 * 트랜잭션 조언
	 * 
	 * @param txManager
	 * @return
	 */
	@Bean
	public TransactionInterceptor txAdvice(final DataSourceTransactionManager txManager) {
		final RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
		txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

		final Map<String, TransactionAttribute> txMethods = new ConcurrentHashMap<>();
		txMethods.put("*", txAttribute);

		final NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
		txAttributeSource.setNameMap(txMethods);

		final TransactionInterceptor txAdvice = new TransactionInterceptor();
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
		final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* egovframework.example.sample..impl.*Impl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
	}

}
