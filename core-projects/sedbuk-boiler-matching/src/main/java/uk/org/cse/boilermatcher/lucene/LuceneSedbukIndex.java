package uk.org.cse.boilermatcher.lucene;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.DocIdBitSet;
import org.apache.lucene.util.Version;

import uk.org.cse.boilermatcher.sedbuk.IBoilerTable;
import uk.org.cse.boilermatcher.sedbuk.ISedbuk;
import uk.org.cse.boilermatcher.sedbuk.ModelDescription;
import uk.org.cse.boilermatcher.sedbuk.ModelDescription.Type;
import uk.org.cse.boilermatcher.tokens.Tokenizer;
import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

/**
 * An {@link ISedbukIndex} which loads an {@link ISedbuk} into Lucene and then
 * searches that.
 *
 * @author hinton
 * @since 1.0
 */
public class LuceneSedbukIndex implements ISedbukIndex {

    private static class VecTextField extends Field {

        /* Indexed, tokenized, not stored. */
        public static final FieldType TYPE_NOT_STORED = new FieldType();

        /* Indexed, tokenized, stored. */
        public static final FieldType TYPE_STORED = new FieldType();

        static {
            TYPE_NOT_STORED.setIndexed(true);
            TYPE_NOT_STORED.setTokenized(true);
            TYPE_NOT_STORED.setStoreTermVectors(true);
            TYPE_NOT_STORED.setStoreTermVectorPositions(true);
            TYPE_NOT_STORED.freeze();

            TYPE_STORED.setIndexed(true);
            TYPE_STORED.setTokenized(true);
            TYPE_STORED.setStored(true);
            TYPE_STORED.setStoreTermVectors(true);
            TYPE_STORED.setStoreTermVectorPositions(true);
            TYPE_STORED.freeze();
        }

        /**
         * Creates a new TextField with String value.
         */
        public VecTextField(final String name, final String value, final Store store) {
            super(name, value, store == Store.YES ? TYPE_STORED : TYPE_NOT_STORED);
        }
    }

    private static final String BRAND_FIELD = "BRAND";
    private static final String QUAL_FIELD = "QUAL";
    private static final String MODEL_FIELD = "MODEL";
    private static final String K_MANUFACTURER = "KMAN";
    private static final String K_MODEL = "KMOD";
    private static final String K_BRAND = "KBRAND";
    private static final String K_QUALIFIER = "KQUAL";
    private static final String K_FUEL_TYPE = "KFUEL";
    private static final String K_FLUE_TYPE = "KFLUE";
    private static final String K_IS_CONDENSING = "K_IS_CONDENSING";
    private static final String K_BOILER_TYPE = "KBT";
    private static final String K_SUMMER_EFFICIENCY = "KSUME";
    private static final String K_WINTER_EFFICIENCY = "KWINE";
    private static final String K_ANNUAL_EFFICIENCY = "KANNE";
    private static final String K_STORE_BOILER_VOLUME = "KSBVOL";
    private static final String K_STORE_SOLAR_VOLUME = "KSSVOL";
    private static final String K_STORE_INSULATION_THICKNESS = "KSINTHCK";

    private static final float QUALITY_THRESHOLD = 0.9f;

    private static final String K_ROW = "KROW";

    final Directory directory;
    private final IndexWriterConfig config;
    private final Tokenizer tokenizer = new Tokenizer();

    /**
     * @since 1.0
     */
    public LuceneSedbukIndex(final ISedbuk sedbuk) {
        this.directory = new RAMDirectory();
        final SedbukAnalyzer analyzer = new SedbukAnalyzer(Version.LUCENE_40);

        config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

        try {
            final IndexWriter w = new IndexWriter(directory, config);

            try {
                final IBoilerTable boilers = sedbuk.getTable(IBoilerTable.class, IBoilerTable.ID);

                for (int i = 0; i < boilers.getNumberOfRows(); i++) {
                    // tokenize the name in sedbuk
                    final List<ModelDescription> tokens = tokenizer.parse(
                            boilers.getModelName(i) + " " + boilers.getModelQualifier(i));

                    final Document doc = new Document();

                    final StringBuffer model = new StringBuffer();
                    final StringBuffer qualifier = new StringBuffer();

                    for (final ModelDescription md : tokens) {
                        final StringBuffer out = md.type == Type.WORD ? model : qualifier;
                        for (final String word : md.getWords()) {
                            if (out.length() > 0) {
                                out.append(" ");
                            }
                            out.append(word);
                        }
                    }

                    doc.add(new TextField(BRAND_FIELD, boilers.getBrandName(i).toLowerCase(), Store.YES));
                    doc.add(new TextField(MODEL_FIELD, model.toString().toLowerCase(), Store.YES));
                    doc.add(new VecTextField(QUAL_FIELD, qualifier.toString().toLowerCase(), Store.YES));

                    doc.add(new StringField(K_MANUFACTURER, boilers.getManufacturerName(i).toLowerCase(), Store.YES));
                    doc.add(new StringField(K_MODEL, boilers.getModelName(i).toLowerCase(), Store.YES));
                    doc.add(new StringField(K_BRAND, boilers.getBrandName(i).toLowerCase(), Store.YES));
                    doc.add(new StringField(K_QUALIFIER, boilers.getModelQualifier(i).toLowerCase(), Store.YES));

                    doc.add(new StringField(K_FUEL_TYPE, boilers.getFuelType(i).name(), Store.YES));
                    doc.add(new StringField(K_FLUE_TYPE, boilers.getFlueType(i).name(), Store.YES));
                    doc.add(new StringField(K_BOILER_TYPE, boilers.getBoilerType(i).name(), Store.YES));

                    doc.add(new StringField(K_IS_CONDENSING, boilers.isCondensing(i).toString(), Store.YES));

                    doc.add(new DoubleField(K_SUMMER_EFFICIENCY, boilers.getSummerEfficiency(i), Store.YES));
                    doc.add(new DoubleField(K_WINTER_EFFICIENCY, boilers.getWinterEfficiency(i), Store.YES));
                    doc.add(new DoubleField(K_ANNUAL_EFFICIENCY, boilers.getAnnualEfficiency(i), Store.YES));

                    doc.add(new DoubleField(K_STORE_BOILER_VOLUME, boilers.getStoreBoilerVolume(i), Store.YES));
                    doc.add(new DoubleField(K_STORE_SOLAR_VOLUME, boilers.getStoreSolarVolume(i), Store.YES));
                    doc.add(new DoubleField(K_STORE_INSULATION_THICKNESS, boilers.getStoreInsulationThickness(i), Store.YES));

                    doc.add(new IntField(K_ROW, i, Store.YES));

                    w.addDocument(doc);

                }
            } finally {
                w.close();
            }
        } catch (final IOException e) {
            throw new RuntimeException(
                    "Lucene has produced an IO error on an in-memory database, which should never happen", e);
        }
    }

    /**
     * @since 1.0
     */
    public static class BoilerTableEntry implements IBoilerTableEntry {

        private final FlueType flueType;
        private final FuelType fuelType;
        private final BoilerType boilerType;
        private final String qualifier;
        private final String manufacturer;
        private final String brand;
        private final String model;
        private final Boolean isCondensing;
        private final double annualEfficiency;
        private final double summerEfficiency;
        private final double winterEfficiency;
        private final double storeBoilerVolume;
        private final double storeSolarVolume;
        private final double storeInsulationThickness;
        private final int row;

        /**
         * @since 1.0
         */
        public BoilerTableEntry(final IBoilerTable table, final int row) {
            model = table.getModelName(row);
            brand = table.getBrandName(row);
            manufacturer = table.getManufacturerName(row);
            qualifier = table.getModelQualifier(row);
            boilerType = table.getBoilerType(row);
            fuelType = table.getFuelType(row);
            flueType = table.getFlueType(row);

            isCondensing = table.isCondensing(row);

            annualEfficiency = table.getAnnualEfficiency(row);
            summerEfficiency = table.getSummerEfficiency(row);
            winterEfficiency = table.getWinterEfficiency(row);

            storeBoilerVolume = table.getStoreBoilerVolume(row);
            storeSolarVolume = table.getStoreSolarVolume(row);
            storeInsulationThickness = table.getStoreInsulationThickness(row);

            this.row = row;
        }

        /**
         * @since 1.0
         */
        public BoilerTableEntry(final Document document) {
            model = document.get(K_MODEL);
            brand = document.get(K_BRAND);
            manufacturer = document.get(K_MANUFACTURER);
            qualifier = document.get(K_QUALIFIER);
            boilerType = BoilerType.valueOf(document.get(K_BOILER_TYPE));
            fuelType = FuelType.valueOf(document.get(K_FUEL_TYPE));
            flueType = FlueType.valueOf(document.get(K_FLUE_TYPE));
            isCondensing = Boolean.valueOf(document.get(K_IS_CONDENSING));

            annualEfficiency = document.getField(K_ANNUAL_EFFICIENCY).numericValue().doubleValue();
            summerEfficiency = document.getField(K_SUMMER_EFFICIENCY).numericValue().doubleValue();
            winterEfficiency = document.getField(K_WINTER_EFFICIENCY).numericValue().doubleValue();

            storeBoilerVolume = document.getField(K_STORE_BOILER_VOLUME).numericValue().doubleValue();
            storeSolarVolume = document.getField(K_STORE_SOLAR_VOLUME).numericValue().doubleValue();
            storeInsulationThickness = document.getField(K_STORE_INSULATION_THICKNESS).numericValue().doubleValue();

            row = document.getField(K_ROW).numericValue().intValue();
        }

        @Override
        public String toString() {
            return String.format("%s, %s, %s", brand, model, qualifier);
        }

        @Override
        public String getManufacturer() {
            return manufacturer;
        }

        @Override
        public String getBrand() {
            return brand;
        }

        @Override
        public String getModel() {
            return model;
        }

        @Override
        public String getQualifier() {
            return qualifier;
        }

        @Override
        public BoilerType getBoilerType() {
            return boilerType;
        }

        @Override
        public FuelType getFuelType() {
            return fuelType;
        }

        @Override
        public FlueType getFlueType() {
            return flueType;
        }

        @Override
        public double getAnnualEfficiency() {
            return annualEfficiency;
        }

        @Override
        public double getWinterEfficiency() {
            return winterEfficiency;
        }

        @Override
        public double getSummerEfficiency() {
            return summerEfficiency;
        }

        /**
         * @since 1.0
         */
        @Override
        public double getStoreBoilerVolume() {
            return storeBoilerVolume;
        }

        /**
         * @since 1.0
         */
        @Override
        public double getStoreSolarVolume() {
            return storeSolarVolume;
        }

        /**
         * @since 1.0
         */
        @Override
        public double getStoreInsulationThickness() {
            return storeInsulationThickness;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public Boolean isCondensing() {
            return isCondensing;
        }
    }

    @Override
    public IBoilerTableEntry find(final String brand, final String model, final FuelType fuelType, final FlueType flueType,
            final BoilerType boilerType) {
        try {
            final BoilerTableEntry e = exactMatch(brand, model, fuelType, flueType, boilerType);
            if (e == null) {
                return vagueMatch(brand, model, fuelType, flueType, boilerType);
            } else {
                return e;
            }
        } catch (final IOException e) {
            throw new RuntimeException("IO exception searching in-memory database?!", e);
        }
    }

    private IBoilerTableEntry vagueMatch(final String brand, final String model, final FuelType fuelType, final FlueType flueType,
            final BoilerType boilerType) throws IOException {
        final BooleanQuery bq = new BooleanQuery();

        // require flue, boiler and fuel to match exactly
        requireEnum(bq, K_FUEL_TYPE, fuelType);
        requireEnum(bq, K_FLUE_TYPE, flueType);
        suggestEnum(bq, K_BOILER_TYPE, boilerType);

        // fuzzy require brand
        requireBrand(bq, brand);

        // split out tokens and add to query
        final List<ModelDescription> tokens = tokenizer.parse(model);

        final HashSet<String> quals = new HashSet<String>();

        boolean noQualifiers = true;
        for (final ModelDescription md : tokens) {
            if (md.type == Type.QUALIFIER) {
                for (final String w : md.getWords()) {
                    noQualifiers = false;
                    final FuzzyQuery qualMatch = new FuzzyQuery(new Term(QUAL_FIELD, w), 1);
                    qualMatch.setBoost(2);
                    bq.add(qualMatch, Occur.SHOULD);
                    quals.add(w);
                }
            } else {
                for (final String w : md.getWords()) {
                    bq.add(new FuzzyQuery(new Term(MODEL_FIELD, w)), Occur.SHOULD);
                }
            }
        }

        if (noQualifiers) {
            bq.add(new TermQuery(new Term(QUAL_FIELD, "")), Occur.MUST);
        }

        final Filter filter = new Filter() {
            final HashSet<String> termsInDocument = new HashSet<String>();

            @Override
            public DocIdSet getDocIdSet(final AtomicReaderContext context, final Bits acceptDocs) throws IOException {
                final BitSet bitSet = new BitSet(context.reader().numDocs());

                for (int i = 0; i < context.reader().numDocs(); i++) {
                    final Terms termVector = context.reader().getTermVector(i, QUAL_FIELD);
                    if (termVector == null) {
                        continue;
                    }

                    final TermsEnum iterator = termVector.iterator(null);

                    termsInDocument.clear();

                    BytesRef next = null;
                    while ((next = iterator.next()) != null) {
                        termsInDocument.add(next.utf8ToString());
                    }

                    // is this too brutal?
                    termsInDocument.removeAll(quals);

                    if (termsInDocument.isEmpty()) {
                        bitSet.set(i);
                    }
                }

                return new DocIdBitSet(bitSet);
            }
        };

        return runQuery("FUZZY", brand, model, bq, QUALITY_THRESHOLD, filter);
    }

    private BoilerTableEntry runQuery(final String matchType, final String brand, final String model, final Query q,
            final float threshold, final Filter filter) throws IOException {
        final DirectoryReader dr = DirectoryReader.open(directory);
        final IndexSearcher searcher = new IndexSearcher(dr);
        final TopScoreDocCollector collector = TopScoreDocCollector.create(1, true);
        if (filter != null) {
            searcher.search(q, filter, collector);
        } else {
            searcher.search(q, collector);
        }
        final ScoreDoc[] hits = collector.topDocs().scoreDocs;

        if (hits.length == 0) {
            return null;
        } else {
            final BoilerTableEntry result = new BoilerTableEntry(searcher.doc(hits[0].doc));
            if (hits[0].score < QUALITY_THRESHOLD) {
                return null;
            } else {
                return result;
            }
        }
    }

    private BoilerTableEntry exactMatch(final String brand, final String model, final FuelType fuelType, final FlueType flueType,
            final BoilerType boilerType) throws IOException {
        final BooleanQuery bq = new BooleanQuery();

        // require flue, boiler and fuel to match exactly
        requireEnum(bq, K_FUEL_TYPE, fuelType);
        requireEnum(bq, K_FLUE_TYPE, flueType);

        suggestEnum(bq, K_BOILER_TYPE, boilerType);

        bq.add(new FuzzyQuery(new Term(K_BRAND, brand)), Occur.MUST);
        bq.add(new TermQuery(new Term(K_MODEL, model)), Occur.MUST);
        bq.add(new TermQuery(new Term(K_QUALIFIER, "")), Occur.MUST);

        return runQuery("EXACT", brand, model, bq, Float.MAX_VALUE, null);
    }

    private static void suggestEnum(final BooleanQuery bq, final String termName, final Enum<?> e) {
        if (e == null) {
            return;
        }

        bq.add(new TermQuery(new Term(termName, e.name())), Occur.SHOULD);
    }

    private static void requireBrand(final BooleanQuery bq, final String words) {
        if (words == null) {
            return;
        }

        final BooleanQuery sub = new BooleanQuery();
        bq.add(sub, Occur.MUST);
        final String clean = words.replaceAll(",", " ").toLowerCase();

        for (final String part : clean.split(" +")) {
            sub.add(new FuzzyQuery(new Term(BRAND_FIELD, part)), Occur.SHOULD);
        }
    }

    private static void requireEnum(final BooleanQuery bq, final String termName, final Enum<?> e) {
        if (e == null) {
            return;
        }

        bq.add(new TermQuery(new Term(termName, e.name())), Occur.MUST);
    }
}
