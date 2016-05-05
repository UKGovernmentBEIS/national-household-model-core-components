package uk.org.cse.stockimport.domain;
import uk.org.cse.nhm.hom.types.VentilationSystem;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * @since 1.0
 */
@DTO(value = "ventilation", required=true)
public interface IVentilationDTO extends IBasicDTO {
	public static final String CHIMNEYSMAIN_FIELD = "ChimneysMainHeating";
	public static final String CHIMNEYSOTHER_FIELD = "ChimneysOther";
	public static final String CHIMNEYSSECONDARY_FIELD = "ChimneysSecondaryHeating";
	public static final String FANS_FIELD = "IntermittentFans";
	public static final String PASSIVEVENTS_FIELD = "PassiveVents";
	public static final String VENTSYSTEMS_FIELD = "VentilationSystem";
	public static final String DRAUGHTSTRIPPED_FIELD = "WindowsAndDoorsDraughtStrippedProportion";	
	
	@DTOField(CHIMNEYSMAIN_FIELD)
	public int getChimneysMainHeating();
	public void setChimneysMainHeating(int chimneysMainHeating);

	@DTOField(CHIMNEYSSECONDARY_FIELD)
	public int getChimneysSecondaryHeating();
	public void setChimneysSecondaryHeating(int chimneysSecondaryHeating);

	@DTOField(CHIMNEYSOTHER_FIELD)
	public int getChimneysOther();
	public void setChimneysOther(int chimneysOther);

	@DTOField(FANS_FIELD)
	public int getIntermittentFans();
	public void setIntermittentFans(int intermittentFans);

	@DTOField(PASSIVEVENTS_FIELD)
	public int getPassiveVents();
	public void setPassiveVents(int passiveVents);

	@DTOField(DRAUGHTSTRIPPED_FIELD)
	public double getWindowsAndDoorsDraughtStrippedProportion();
	public void setWindowsAndDoorsDraughtStrippedProportion(final double proportion);

	@DTOField(VENTSYSTEMS_FIELD)
	public VentilationSystem getVentilationSystem();
	public void setVentilationSystem(VentilationSystem ventilationSystem);
}
