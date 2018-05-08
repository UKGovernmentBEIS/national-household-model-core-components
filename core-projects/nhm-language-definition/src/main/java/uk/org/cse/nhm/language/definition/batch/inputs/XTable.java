package uk.org.cse.nhm.language.definition.batch.inputs;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.validation.TableRowLengths;

@Bind("table")
@Doc(value = {
    "An input which is a table of variations to apply to the scenario.",
    "The header specifies the placeholder names which must match those in the scenario for a successful replacement.",
    "Each row specifies a set of values to be substituted in for a particular scenario run.",
    "A table is bounded by the number of rows it contains."})
@TableRowLengths(message = "table rows must all be the same length as the table's header.")
public class XTable extends XInputs {

    public static final class P {

        public static final String HEADER = "header";
        public static final String ROWS = "rows";
    }

    private List<String> header = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    @Prop(P.HEADER)
    @BindPositionalArgument(0)
    @NotNull(message = "table element must contain a header, which is a list of placeholders in square brackets.")
    @Doc("The header for the table specifies the names of the columns, which are the placeholder names which will be used when substituting values into the scenario.")
    public List<String> getHeader() {
        return header;
    }

    public void setHeader(final List<String> header) {
        this.header = header;
    }

    @BindRemainingArguments
    @Prop(P.ROWS)
    @Size(min = 1, message = "table element must contain at least 1 row.")
    @Doc("Each row of the table specifies the values to be used for one scenario run. Each row is written as a list in square brackets.")
    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(final List<List<String>> rows) {
        this.rows = rows;
    }

    @Override
    public boolean hasBound() {
        return true;
    }

    @Override
    @Prop(XInputs.P.PLACEHOLDERS)
    public List<String> getPlaceholders() {
        final ImmutableList.Builder<String> result = ImmutableList.builder();
        for (final String cell : header) {
            result.add(cell);
        }
        return result.build();
    }
}
