package uk.org.cse.stockimport.imputation.lookupbuilders;

/**
 * Builds look-up tables from a given data-source
 *
 * @author richardt
 *
 * @param <LOOKUPTABLE>
 * @param <DATASOURCE>
 */
public interface ILookUpTableBuilder<LOOKUPTABLE, DATASOURCE> {

    LOOKUPTABLE buildTables(DATASOURCE dataSource);
}
