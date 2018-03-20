package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.validate.contents.ActsAsParent;

@Doc({"Repeats a sequence of other commands until a given condition is met.",
      "When the repeat is triggered, it will repeatedly perform all the commands it contains,",
      "in the order they are given.",
      "At the end of each repetition, the logical test given in the until: argument will be tested.",
      "If the test passes (it is true), then the repetition is finished, and the effects of the commands",
      "are kept.",
      "If the test does not pass, the repeat starts again; if any variables are specified in the preserving: argument",
      "then all changes made in the repeat so far to variables and houses except changes to those variables are undone",
      "before repeating. If no variables are specified, all changes are kept.",
      "These two modes allow repeat to be used either as a simple loop, or for limited optimisation of the effects of",
      "a numerical parameter."
 })
@Bind("repeat")
public class XRepeatHookAction extends XHookAction {
    public static class P {
        public static final String toRepeat   = "toRepeat";
        public static final String until      = "until";
        public static final String preserving = "preserving";
    }
    
    private List<XHookAction> toRepeat = new ArrayList<>();

    @Doc("The actions which will be repeated. These actions will always be applied at least once.")
    @Size(min=1, message="repeat must contain some actions to repeat, or nothing will change and the repeat will be infinite")
    @BindRemainingArguments
    @Prop(P.toRepeat)
    public List<XHookAction> getToRepeat() {
        return toRepeat;
    }
    
    public void setToRepeat(List<XHookAction> toRepeat) {
        this.toRepeat = toRepeat;
    }

    private XBoolean until;

    @Doc({"The condition to determine when the repeat will stop. This is computed at the end of each repeat.",
          "When it is true, the repeat is finished.",
          "There is no current house when this is computed, but you can use summarize to determine an aggregate state,",
          "or examine numbers defined with the Simulation scope."})
    @NotNull(message="repeat requires an until: argument, which specifies when repetition will stop")
    @BindNamedArgument
    @Prop(P.until)
    public XBoolean getUntil() {
        return until;
    }
    
    public void setUntil(XBoolean until) {
        this.until = until;
    }

    private List<XNumberDeclaration> preserving = new ArrayList<>();

    @Doc({"A list of variables to keep. If any variables are specified here, the effects of the commands to repeat",
          "are undone each time the test fails, barring changes to these variables. For example, this could be used",
          "to preserve the value of a subsidy level which is increased with each repetition, until its effects are sufficient",
          "to meet a policy objective.",
          "The changes undone include the use of random values from the random number stream." })
    @BindNamedArgument("preserving")
    @Prop(P.preserving)
    @ActsAsParent(IHouseContext.class)
    public List<XNumberDeclaration> getPreserving() {
        return preserving;
    }
    
    public void setPreserving(List<XNumberDeclaration> preserving) {
        this.preserving = preserving;
    }
}


