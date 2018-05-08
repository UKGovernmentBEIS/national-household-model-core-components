package uk.org.cse.boilermatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import uk.org.cse.boilermatcher.lucene.IBoilerTableEntry;
import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.LuceneSedbukIndex;
import uk.org.cse.boilermatcher.lucene.StopwordIndex;
import uk.org.cse.boilermatcher.sedbuk.IBoilerTable;
import uk.org.cse.boilermatcher.sedbuk.ISedbuk;
import uk.org.cse.boilermatcher.sedbuk.Sedbuk;

public class SedbukToLucene {

    ISedbuk sedbuk;
    IBoilerTable boilerTable;
    ISedbukIndex index;

    @Before
    public void intialiseTests() throws IOException {
        sedbuk = new Sedbuk();
        boilerTable = sedbuk.getTable(IBoilerTable.class, IBoilerTable.ID);
        index = new StopwordIndex(new LuceneSedbukIndex(new Sedbuk()));
    }

    @Test
    public void testLuceneIndex() throws IOException {
        try (BufferedReader questions
                = new BufferedReader(
                        new InputStreamReader(getClass().getResourceAsStream("/sedbuk/q.csv")))) {

            String row = null;
            while ((row = questions.readLine()) != null) {
                final String[] parts = row.split(",");
                if (parts.length > 1) {
                    index.find(parts[0], parts[1], null, null, null);
                }
            }
        }
    }

    @Test
    /**
     * We allow some leeway with this metric since the test itself is slightly
     * wrong: it doesn't account for matches which are essentially the same, but
     * with the words in a different order.
     *
     * @throws IOException
     */
    public void testSedbukIndexProvidesCorrectMatchesWhenFedItsOwnData() throws IOException {
        for (final int row : boilerTableRows()) {
            final IBoilerTableEntry match = index.find(boilerTable.getBrandName(row), boilerTable.getModelName(row) + " " + boilerTable.getModelQualifier(row),
                    boilerTable.getFuelType(row), boilerTable.getFlueType(row), boilerTable.getBoilerType(row));

            int i = 0;
            if (match != null && !boilerToString(boilerTable, row).toLowerCase().equals(boilerToString(match))) {
                System.out.println(String.format("mismatch when looking up sedbuk data in sedbuk index Expected %s Actual %s", boilerToString(boilerTable, row).toLowerCase(),
                        boilerToString(match)));
                i++;
            }
            Assert.assertTrue("Too many cases matched incorrectly when feeding Sedbuk data into the Sedbuk index.", i < 60);
        }
    }

    @Test
    public void countSedbukToSedbukLookupFailures() throws IOException {
        int i = 0;
        for (final int row : boilerTableRows()) {

            final IBoilerTableEntry match = index.find(boilerTable.getBrandName(row), boilerTable.getModelName(row) + " " + boilerTable.getModelQualifier(row),
                    boilerTable.getFuelType(row), boilerTable.getFlueType(row), boilerTable.getBoilerType(row));

            if (match == null) {
                i++;
            }
        }
        System.out.println("Could not match this many cases when feeding Sedbuk data into the Sedbuk index: " + i);
        Assert.assertTrue("Too many cases could not be matched when feeding Sedbuk data into the Sedbuk index.", i < 100);
    }

    private ContiguousSet<Integer> boilerTableRows() {
        return ContiguousSet.create(Range.closed(0, boilerTable.getNumberOfRows() - 1), DiscreteDomain.integers());
    }

    private String boilerToString(final IBoilerTableEntry boilerEntry) {
        return String.format("%s %s %s", boilerEntry.getBrand(), boilerEntry.getModel(), boilerEntry.getQualifier());
    }

    private String boilerToString(final IBoilerTable table, final int row) {
        return String.format("%s %s %s", table.getBrandName(row), table.getModelName(row), table.getModelQualifier(row));
    }
}
