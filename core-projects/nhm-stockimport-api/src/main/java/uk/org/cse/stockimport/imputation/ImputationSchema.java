package uk.org.cse.stockimport.imputation;

import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowPropertyTables;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingUValueTables;
import uk.org.cse.stockimport.imputation.floors.IFloorPropertyTables;
import uk.org.cse.stockimport.imputation.house.IHousePropertyTables;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyTables;

public class ImputationSchema implements ISchemaForImputation {

	String executionId;
	private ICeilingUValueTables ceilingUValueTables;
	private IHousePropertyTables housePropertyTables;
	private IFloorPropertyTables floorPropertyTables;
	private IWindowPropertyTables windowPropertyTables;
	private IWallPropertyTables wallPropertyTables;
	private IDoorPropertyImputer doorPropertyImputer;
	
	public ImputationSchema(String executionId) {
		this.executionId = executionId;
	}
	
	@Override
	public String getExecutionId() {
		return executionId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ImputationSchema) {
			ImputationSchema schema = (ImputationSchema) obj;
			if (schema.getExecutionId().equals(getExecutionId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ICeilingUValueTables getCeilingUValueTables() {
		return ceilingUValueTables;
	}

	@Override
	public void setCeilingUValueTables(ICeilingUValueTables uValuesTables) {
		ceilingUValueTables = uValuesTables;
	}


	@Override
	public IHousePropertyTables getHousePropertyTables() {
		return housePropertyTables;
	}


	@Override
	public void setHousePropertyTables(IHousePropertyTables housePropertyTables) {
		this.housePropertyTables = housePropertyTables;
	}

	@Override
	public IFloorPropertyTables getFloorPropertyTables() {
		return floorPropertyTables;
	}


	@Override
	public void setFloorPropertyTables(IFloorPropertyTables floorPropertyTables) {
		this.floorPropertyTables = floorPropertyTables;
	}

	@Override
	public IWindowPropertyTables getWindowPropertyTables() {
		return windowPropertyTables;
	}

	@Override
	public void setWindowPropertyTables(
			IWindowPropertyTables windowPropertyTables) {
		this.windowPropertyTables = windowPropertyTables;
	}

	@Override
	public IWallPropertyTables getWallPropertyTables() {
		return wallPropertyTables;
	}

	@Override
	public void setWallPropertyTables(IWallPropertyTables wallPropertyTables) {
		this.wallPropertyTables = wallPropertyTables;
	}

	@Override
	public IDoorPropertyImputer getDoorPropertyImputer() {
		return this.doorPropertyImputer;
	}

	@Override
	public void setDoorPropertyImputer(IDoorPropertyImputer doorPropertyImputer) {
		this.doorPropertyImputer = doorPropertyImputer;
	}
}
