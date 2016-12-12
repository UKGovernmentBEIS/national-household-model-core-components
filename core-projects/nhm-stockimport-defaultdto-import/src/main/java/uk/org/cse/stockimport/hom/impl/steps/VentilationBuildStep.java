package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

public class VentilationBuildStep implements ISurveyCaseBuildStep {
	protected static final Logger log = LoggerFactory.getLogger(VentilationBuildStep.class);
    public static final String IDENTIFIER = VentilationBuildStep.class.getCanonicalName();
    
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public Set<String> getDependencies() {
        return ImmutableSet.of(StructureInitializingBuildStep.IDENTIFIER);
	}
	
	@Override
	public void build(SurveyCase model, IHouseCaseSources<IBasicDTO> dtoProvider) {
		final StructureModel structure = model.getStructure();
		Optional<IVentilationDTO> ventilationDTO = dtoProvider.getOne(IVentilationDTO.class);
		
		if (ventilationDTO.isPresent()) {
			structure.setIntermittentFans(
					ventilationDTO.get().getIntermittentFans());
			
			structure.setPassiveVents(
					ventilationDTO.get().getPassiveVents());
			
		} else {
			structure.setIntermittentFans(0);
			structure.setPassiveVents(0);
		}
	}
}
