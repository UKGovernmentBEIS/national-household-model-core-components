package uk.org.cse.stockimport.repository;

import java.util.List;

import uk.org.cse.nhm.hom.SurveyCase;

public interface IHousingStockDataService {

    void deleteImportedStock(String importId);

    void saveInHousingStockCollection(String importId, Object objectToSave);

    <T> List<T> findAll(Class<T> type, String importId);

    List<SurveyCase> findAllSurveyCasesWithoutAdditionalProperties(String importId);
}
