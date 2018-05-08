package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.List;

import com.google.common.base.Optional;

public interface IBatchInputs extends Iterable<List<Object>> {

    Optional<Integer> getBound();

    List<String> getPlaceholders();
}
