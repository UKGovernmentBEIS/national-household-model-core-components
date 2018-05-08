package uk.org.cse.nhm.simulator.integration.tests.xml;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.XExtraContextParameter;
import uk.org.cse.nhm.language.definition.group.XGroup;
import uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregation;

@Doc({
    "This is a debugging element which checks an assertion.",
    "The assertion is a statement that, for a given group, a particular",
    "aggregation function evaluated for that group will increase, decrease, or remain constant.",
    "The assertion can be continuous, in which case all changes in the scenario must satisfy it,",
    "or not continuous, in which case the difference between the group at the start and the end must be acceptable."
})

@Bind("debug/assert")
public class XAssertion extends XExtraContextParameter {

    public enum XAssertionType {
        Decreasing,
        Unchanging,
        Increasing
    }

    private XGroup group;
    private XAggregation aggregation;
    private XAssertionType type = XAssertionType.Unchanging;
    private boolean continuous = false;
    private double bound = 0.05;
    private String name;

    @Override

    @BindNamedArgument
    @Doc("The name of the assertion, for humans.")
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @BindNamedArgument

    @Doc("The group over which the aggregation will be computed.")
    public XGroup getGroup() {
        return group;
    }

    public void setGroup(final XGroup group) {
        this.group = group;
    }

    @BindNamedArgument
    @Doc("An aggregation function whose value will be tested")
    public XAggregation getAggregation() {
        return aggregation;
    }

    public void setAggregation(final XAggregation aggregation) {
        this.aggregation = aggregation;
    }

    @BindNamedArgument
    @Doc("The direction of change to test for")
    public XAssertionType getType() {
        return type;
    }

    public void setType(final XAssertionType type) {
        this.type = type;
    }

    @BindNamedArgument
    @Doc("If true, this assertion will be tested whenever anything changes. Otherwise it will be tested only at the start and end of the run.")
    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(final boolean continuous) {
        this.continuous = continuous;
    }

    @BindNamedArgument
    @Doc("A bound, as a proportion, which indicates whether something is a change.")
    public double getBound() {
        return bound;
    }

    public void setBound(final double bound) {
        this.bound = bound;
    }
}
