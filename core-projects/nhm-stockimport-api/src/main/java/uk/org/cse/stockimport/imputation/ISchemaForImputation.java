package uk.org.cse.stockimport.imputation;

import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowPropertyTables;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingUValueTables;
import uk.org.cse.stockimport.imputation.floors.IFloorPropertyTables;
import uk.org.cse.stockimport.imputation.house.IHousePropertyTables;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyTables;

public interface ISchemaForImputation {

    String getExecutionId();

    ICeilingUValueTables getCeilingUValueTables();

    void setCeilingUValueTables(ICeilingUValueTables uValuesTables);

    IHousePropertyTables getHousePropertyTables();

    void setHousePropertyTables(IHousePropertyTables housePropertyTables);

    IFloorPropertyTables getFloorPropertyTables();

    void setFloorPropertyTables(IFloorPropertyTables floorPropertyTables);

    IWindowPropertyTables getWindowPropertyTables();

    void setWindowPropertyTables(IWindowPropertyTables windowPropertyTables);

    IWallPropertyTables getWallPropertyTables();

    void setWallPropertyTables(IWallPropertyTables wallPropertyTables);

    IDoorPropertyImputer getDoorPropertyImputer();

    void setDoorPropertyImputer(IDoorPropertyImputer doorPropertyImputer);
}
