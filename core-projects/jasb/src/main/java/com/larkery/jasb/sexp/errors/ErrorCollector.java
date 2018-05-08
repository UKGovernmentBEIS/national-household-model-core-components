package com.larkery.jasb.sexp.errors;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class ErrorCollector extends BaseErrorHandler {

    private final List<IError> errors = new ArrayList<>();

    @Override
    public void handle(final IError error) {
        errors.add(error);
    }

    public List<IError> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    public void clear() {
        errors.clear();
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }
}
