package uk.org.cse.stockimport.hom.impl.steps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.importlog.IWarningDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

public class WarningsStep implements ISurveyCaseBuildStep {

    private final Set<String> existingIdentifiers;

    public WarningsStep(final Set<String> existingIdentifiers) {
        this.existingIdentifiers = existingIdentifiers;
    }

    @Override
    public String getIdentifier() {
        return WarningsStep.class.getCanonicalName();
    }

    @Override
    public Set<String> getDependencies() {
        return existingIdentifiers;
    }

    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final List<IWarningDTO> all = dtoProvider.getAll(IWarningDTO.class);
        final Set<String> logMessages = new HashSet<String>();

        for (final IWarningDTO dto : all) {
            logMessages.add(dto.getMessage());
        }

        model.setImportLog(logMessages);
    }
}
