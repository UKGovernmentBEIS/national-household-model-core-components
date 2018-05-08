package uk.org.cse.stockimport.domain.schema;

import java.nio.file.Path;
import java.util.Set;

public interface IKeyValue {

    public Set<String> getKeys();

    public String get(final String key);

    public Path file();

    public int line();
}
