package uk.org.cse.stockimport.repository;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class HouseCaseSources<Q> implements IHouseCaseSources<Q> {

    private final String aacode;
    private final int surveyYear;
    private final List<? extends Q> contents;

    HouseCaseSources(final String aacode, final int surveyYear, final List<? extends Q> contents) {
        super();
        this.aacode = aacode;
        this.surveyYear = surveyYear;
        this.contents = contents;
    }

    public static <Q> IHouseCaseSources<Q> withMutableList(final String aacode, final int surveyYear, final List<? extends Q> contents) {
        return new HouseCaseSources<>(aacode, surveyYear, contents);
    }

    public static <Q> IHouseCaseSources<Q> withImmutableList(final String aacode, final int surveyYear, final List<? extends Q> contents) {
        return withMutableList(aacode, surveyYear, ImmutableList.copyOf(contents));
    }

    @Override
    public String getAacode() {
        return aacode;
    }

    @Override
    public <T extends Q> Optional<T> getOne(final Class<T> clazz) throws IllegalArgumentException {
        final List<T> vals = getAll(clazz);
        if (vals.isEmpty()) {
            return Optional.absent();
        } else if (vals.size() == 1) {
            return Optional.of(vals.get(0));
        } else {
            throw new IllegalArgumentException(String.format("There are %d instances of %s, not zero or one", vals.size(), clazz.getSimpleName()));
        }
    }

    @Override
    public <T extends Q> T requireOne(final Class<T> clazz) throws IllegalArgumentException {
        final Optional<T> value = getOne(clazz);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new NoSuchDTOException(getAacode(), clazz);
        }
    }

    @Override
    public <T extends Q> List<T> getAll(final Class<T> clazz) {
        final ImmutableList.Builder<T> result = ImmutableList.builder();
        for (final Q value : contents) {
            if (clazz.isInstance(value)) {
                result.add(clazz.cast(value));
            }
        }
        return result.build();
    }

    @Override
    public int getSurveyYear() {
        return surveyYear;
    }
}
