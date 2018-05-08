package uk.org.cse.stockimport.spss.elementreader;

import java.util.Collection;
import java.util.Iterator;

public interface ISpssReader<DTO> extends Iterator<Collection<DTO>> {

    String getName();
    //List<DTO> read(IHouseCaseSources<Object> provider);
}
