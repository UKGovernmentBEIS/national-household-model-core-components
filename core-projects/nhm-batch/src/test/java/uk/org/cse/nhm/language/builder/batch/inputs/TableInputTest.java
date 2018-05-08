package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class TableInputTest {

    private List<String> headers;
    private List<List<Object>> rows;

    @Before
    public void setup() {
        headers = ImmutableList.of("a", "b");
        List<Object> rowOne = ImmutableList.of(new Object(), new Object());
        List<Object> rowTwo = ImmutableList.of(new Object(), new Object());
        rows = ImmutableList.of(rowOne, rowTwo);
    }

    @Test
    public void placeholdersAreHeaders() {
        Assert.assertEquals("Placeholders should come from headers.", headers, new TableInput(headers, rows).getPlaceholders());
    }

    @Test
    public void boundIsNumberOfRows() {
        Assert.assertEquals("Bound is number of rows.", Optional.of(rows.size()), new TableInput(headers, rows).getBound());
    }

    @Test
    public void iteratorContainsRows() {
        Assert.assertEquals("Bound is number of rows.", rows, ImmutableList.copyOf(new TableInput(headers, rows).iterator()));
    }
}
