package egovframework.example.pagination;

import java.util.Collections;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * 페이징
 */
public class EgovPaginationDialect extends AbstractDialect implements IExpressionObjectDialect {

	/**
	 * 페이징
	 */
	public EgovPaginationDialect() {
		super("EgovPaginationDialect");
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return new IExpressionObjectFactory() {
			@Override
			public Set<String> getAllExpressionObjectNames() {
				return Collections.singleton("egovPaginationFormat");
			}

			@Override
			public Object buildObject(IExpressionContext context, String expressionObjectName) {
				return new EgovPaginationFormat();
			}

			@Override
			public boolean isCacheable(String expressionObjectName) {
				return true;
			}
		};
	}

}
