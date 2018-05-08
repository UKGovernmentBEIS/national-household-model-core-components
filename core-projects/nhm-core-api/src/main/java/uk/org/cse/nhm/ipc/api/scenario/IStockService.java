package uk.org.cse.nhm.ipc.api.scenario;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.stock.IHousingStock;

public interface IStockService {

    public Set<String> getStockIDs();

    public IHousingStock getMetadata(final String stockID);

    public List<? extends IHousingStock> getMetadata();

    public List<? extends IImportStatus> getImportStatuses();

    public List<SurveyCase> getSurveyCases(final String stockID, final Set<String> additionalProperties);

    public Set<String> getImportIDs();

    public IImportStatus getImportStatus(String id);

    public boolean startImport(final String id);

    public void updateImport(final String name, final String message);

    public void completeImport(String name, final IHousingStock metadata, final List<SurveyCase> cases);

    public void failImport(String name, final String message);
}
