package cse.nhm.ide.runner.ui.preferences;

import java.util.List;
import java.util.ArrayList;

import cse.nhm.ide.runner.ui.RunnerUIPlugin;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Joiner;
import cse.nhm.ide.runner.api.IScenarioRunner;

import java.util.Objects;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class RunnerUIPreferences extends AbstractPreferenceInitializer {
    private static final String K_RECENT_SERVICES = "RECENT_SERVICES";
    static final String K_HIDE_LOCAL = "HIDE_LOCAL";
    private static final String DELIM = ":::";

    private static final Splitter splitter = Splitter.on(DELIM).trimResults().omitEmptyStrings();
    private static final Joiner joiner = Joiner.on(DELIM).skipNulls();

    public static boolean isLocalRunnerHidden() {
        return RunnerUIPlugin.getDefault().getPreferenceStore()
            .getBoolean(K_HIDE_LOCAL);
    }

    public static Optional<IScenarioRunner> findService(final String name) {
        // attempt to find a service with the given name.
        // if the name is null, try and find a service which we have used before
        // if there are none, return absent

        final IEclipsePreferences preferenceNode = InstanceScope.INSTANCE.getNode(RunnerUIPlugin.PLUGIN_ID);

        final String recent = preferenceNode.get(K_RECENT_SERVICES, "");

        final Iterable<String> parts = splitter.split(recent);

        if (name == null) {
            for (final String s : parts) {
                final Optional<IScenarioRunner> r = findService(s);
                if (r.isPresent()) return r;
            }
            // find a service that definitely exists?
            final List<IScenarioRunner> runners = RunnerUIPlugin.getDefault().tracker.getRunners();

            // todo maybe filter out the local runner?
            if (!runners.isEmpty()) {
                return findService(runners.get(0).getName());
            }

            return Optional.absent();
        } else {
            Optional<IScenarioRunner> result = Optional.absent();
            final List<IScenarioRunner> runners = RunnerUIPlugin.getDefault().tracker.getRunners();

            for (final IScenarioRunner runner : runners) {
                if (Objects.equals(name, runner.getName())) {
                    result = Optional.of(runner);
                    break;
                }
            }

            if (result.isPresent()) {
                final String selected = result.get().getName();
                // bump the selected item up to the head of the list, and then save
                final ArrayList<String> newParts = new ArrayList<String>();
                newParts.add(selected);
                for (final String p : parts)
                    if (!p.equals(selected))
                        newParts.add(p);

                preferenceNode.put(K_RECENT_SERVICES, joiner.join(newParts));
                try {preferenceNode.sync();} catch (Exception e) {}
            }

            return result;
        }
    }

    @Override
    public void initializeDefaultPreferences() {
        final IPreferenceStore store = RunnerUIPlugin.getDefault().getPreferenceStore();
        store.setDefault(K_HIDE_LOCAL, false);
    }
}
