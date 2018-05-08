package com.larkery.jasb.io;

public interface IAtomWriter {

    public boolean canWrite(final Object object);

    public String write(final Object object);
}
