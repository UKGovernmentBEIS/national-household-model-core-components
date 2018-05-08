package com.larkery.jasb.sexp.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError.Type;

public interface IErrorHandler {

    @JsonTypeInfo(use = Id.CLASS)
    public interface IError {

        public Location getLocation();

        public String getMessage();

        public Type getType();

        public enum Type {
            ERROR,
            WARNING;

            public boolean isFatal() {
                return this == ERROR;
            }
        }
    }

    static final Logger log = LoggerFactory.getLogger(IErrorHandler.class);
    public static final IErrorHandler SLF4J = new BaseErrorHandler() {
        @Override
        public void handle(final IError error) {
            switch (error.getType()) {
                case ERROR:
                    log.error("{} : {}", error.getLocation(), error.getMessage());
                    break;
                case WARNING:
                    log.warn("{} : {}", error.getLocation(), error.getMessage());
                    break;
            }
        }

        @Override
        public String toString() {
            return "IErrorHandler.SLF4J";
        }
    };

    public static final IErrorHandler NOP = new BaseErrorHandler() {
        @Override
        public void handle(final IError error) {
        }

        @Override
        public String toString() {
            return "IErrorHandler.NOP";
        }
    };
    static final IErrorHandler RAISE = new BaseErrorHandler() {
        @Override
        public void handle(final IError error) {
            if (error.getType() != Type.WARNING) {
                throw new JasbErrorException(error);
            }
        }

        @Override
        public String toString() {
            return "IErrorHandler.RAISE";
        }
    };
    static final IErrorHandler IGNORE = NOP;

    public void handle(final IError error);

    public void error(final ILocated location, final String format, final Object... interpolate);

    public void warn(final ILocated location, final String string, final Object... interpolate);
}
