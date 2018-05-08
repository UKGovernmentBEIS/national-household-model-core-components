package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.IKeyValue;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbsMapperTest extends Mockito {

    protected IKeyValue fieldSet;
    private final Set<String> fields = new HashSet<String>();

    protected final static String aacode = "AACCDD123";

    public static final void testBuildReferenceData(final IBasicDTO dto, final String aacode) {
        assertEquals("AAcode not mapped", aacode, dto.getAacode());
    }

    public AbsMapperTest() {
        fields().reset();
    }

    protected MockFieldSet fields() {
        return new MockFieldSet();
    }

    class MockFieldSet {

        public MockFieldSet add(final String field, final Object value) {
            when(fieldSet.get(field)).thenReturn(String.valueOf(value));
            when(fieldSet.get(field.toLowerCase())).thenReturn(String.valueOf(value));
            fields.add(field);
            fields.add(field.toLowerCase());
            return this;
        }

        public MockFieldSet reset() {
            fieldSet = mock(IKeyValue.class);
            fields.clear();
            return this;
        }
    }
}
