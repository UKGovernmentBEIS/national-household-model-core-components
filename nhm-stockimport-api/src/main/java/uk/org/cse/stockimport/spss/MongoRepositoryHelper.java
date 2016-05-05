package uk.org.cse.stockimport.spss;

/**
 * Helper class to wrap calls to Mongo for specific collections.
 * 
 * @author richardt
 * @version $Id: MongoRepositoryHelper.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class MongoRepositoryHelper {

    /**
     * @since 1.0
     */
    public static final String getCollectionName(String executionId, Class<?> clazz) {
        return executionId + "_" + clazz.getName();
    }
}
