package uk.org.cse.stockimport.hom;

import uk.org.cse.stockimport.imputation.ISchemaForImputation;

public interface ISurveyCaseBuilderFactory {

    public ISurveyCaseBuilder getSurveyCaseBuilder(ISchemaForImputation imputationSchema);
}
