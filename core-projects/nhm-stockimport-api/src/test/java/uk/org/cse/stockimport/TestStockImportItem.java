package uk.org.cse.stockimport;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import uk.org.cse.stockimport.impl.StockImportItem;
import uk.org.cse.stockimport.util.Path;

/**
 * TestStockImportItem.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt
 * $
 * @since $
 */
public class TestStockImportItem {

    @Test
    public void testStockItemCanBeUnmarshalledFromXML() throws Exception {
        String itemFile = Path.file("src", "test", "resources", "xmlSources", "importItem.xml");
        InputStream xml = new FileInputStream(itemFile);

        final JAXBContext context = JAXBContext.newInstance(StockImportItem.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StockImportItem importItem = StockImportItem.class.cast(unmarshaller.unmarshal(xml));
        assertNotNull("Classname was null", importItem.getClassName());
        assertNotNull("File Name should be returned", importItem.getFileName());
    }
}
