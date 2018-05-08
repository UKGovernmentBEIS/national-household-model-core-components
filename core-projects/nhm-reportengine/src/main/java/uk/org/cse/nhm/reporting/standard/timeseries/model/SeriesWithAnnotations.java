package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.util.List;
import java.util.SortedSet;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SeriesWithAnnotations {

    private List<Series> series;
    private SortedSet<Annotation> annotations;

    public SeriesWithAnnotations(List<Series> series, SortedSet<Annotation> annotations) {
        this.series = series;
        this.annotations = annotations;
    }

    public List<Series> getSeries() {
        return series;
    }

    public SortedSet<Annotation> getAnnotations() {
        return annotations;
    }
}
