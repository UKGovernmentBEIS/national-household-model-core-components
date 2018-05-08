package uk.org.cse.nhm.ipc.api.about;

public interface IAboutService {

    public void register(final IAboutable component);

    public void unregister(final IAboutable component);

    public interface IListener {

        public boolean heardAbout(final About thing);
    }

    public void addListener(final IListener listener);

    public void removeListener(final IListener listener);
}
