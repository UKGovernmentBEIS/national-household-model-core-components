package uk.org.cse.nhm.language.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.visit.SinglyVisitingVisitor;

public class ValidatingVisitor extends SinglyVisitingVisitor<XElement> {
	private final Validator validator;
	private final List<IError> violations;

	private static final LoadingCache<Class<?>, Boolean> hasValidationConstraints = 
			CacheBuilder.newBuilder().build(
					new CacheLoader<Class<?>, Boolean>() {
						@Override
						public Boolean load(final Class<?> key) throws Exception {
							for (final Annotation a : key.getAnnotations()) {
								if (isValidationAnnotation(a)) {
									return true;
								}
							}
							
							for (final Method m : key.getMethods()) {
								for (final Annotation a : m.getAnnotations()) {
									if (isValidationAnnotation(a)) {
										return true;
									}
								}
							}
							
							return false;
						}
						
						boolean isValidationAnnotation(final Annotation a) {
							return a.annotationType().isAnnotationPresent(Constraint.class);
						}
					}
				);
	
	public ValidatingVisitor(final Validator validator) {
		super(XElement.class);
		violations = new ArrayList<IError>();
		this.validator = validator;
	}
	
	@Override
	public void visit(final XElement v) {
		try {
			if (hasValidationConstraints.get(v.getClass())) {
				final ClassLoader tcc = Thread.currentThread().getContextClassLoader();
				try {
					Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
					for(final ConstraintViolation<XElement> violation : validator.validate(v)) {
						violations.add(BasicError.at(v.getLocation(), violation.getMessage()));
					}
				} finally {
					Thread.currentThread().setContextClassLoader(tcc);
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			violations.add(BasicError.at(v.getLocation(), e.getMessage() == null ? "Unknown error" : e.getMessage()));
		}
	}

	public List<IError> getViolations() {
		return violations;
	}
}
