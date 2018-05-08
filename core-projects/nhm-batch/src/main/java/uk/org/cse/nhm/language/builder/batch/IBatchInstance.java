package uk.org.cse.nhm.language.builder.batch;

import java.util.Map;

import com.larkery.jasb.sexp.ISExpression;

public interface IBatchInstance extends ISExpression {

    Map<String, String> getParameters();

}
