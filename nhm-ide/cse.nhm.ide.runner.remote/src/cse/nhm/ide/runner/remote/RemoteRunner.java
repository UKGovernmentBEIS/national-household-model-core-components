package cse.nhm.ide.runner.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Sets;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRun.State;
import cse.nhm.ide.runner.api.IScenarioRun.Type;
import cse.nhm.ide.runner.api.ScenarioRunner;

public class RemoteRunner extends ScenarioRunner {
    private static final String _PART = "_part-";
    private static final String USER = "user";
    private static final String VERSION = "version";
    private static final String NAME = "name";
    private static final String STOCKS = "stocks";
    private static final String RESULTS = "results";
    private static final String JOBS = "jobs";
    private static final String PART = "part-";
    private static final String LOG = "log";
    private final String baseAddress;
    private final Map<String, RemoteRun> contents = new TreeMap<>();
    private final String user;
    private static Joiner SLASH = Joiner.on('/');

    public RemoteRunner(final String baseAddress, final String user) {
        super();
        this.baseAddress = baseAddress;
        this.user = user;
    }

    private HttpURLConnection con(final String resource) {
        try {
            final URL u = new URL(baseAddress + (
                    (baseAddress.endsWith("/") || resource.startsWith("/"))
                    ? "" : "/") + resource);
            return (HttpURLConnection) u.openConnection();
        } catch (final Exception e) {
            RemoteRunnerPlugin.getDefault().error(
                    "Invalid remote runner address " + baseAddress, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return baseAddress;
    }

    @Override
    public String getDescription() {
        return "server " + baseAddress;
    }

    @Override
    public String getStatus() {
        return "REMOTE";
    }

    @Override
    public List<IScenarioRun> getScenarioRuns() {
        return ImmutableList.<IScenarioRun>copyOf(contents.values());
    }

    @Override
    public void refresh() {
        try {
            update();
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Poll the server for new data!
     * @throws IOException
     */
    boolean update() throws IOException {
        final HttpURLConnection con = con(JOBS + "/");
        final JsonValue result;
        try {
            result = Json.parse(new InputStreamReader(con.getInputStream()));
        } catch (final IOException e) {
            updated(getScenarioRuns(), Collections.<IScenarioRun>emptySet(), Collections.<IScenarioRun>emptySet());
            throw e;
        }

        if (result.isArray()) {
            final ImmutableList.Builder<IScenarioRun> added   = ImmutableList.builder();
            final ImmutableList.Builder<IScenarioRun> changed = ImmutableList.builder();
            final ImmutableList.Builder<IScenarioRun> removed = ImmutableList.builder();
            final Set<String> seen = new HashSet<>();

            for (final JsonValue thing : result.asArray()) {
                if (thing.isObject()) {
                    final JsonObject o = thing.asObject();

                    final JsonValue id = o.get("id");

                    if (id != null && id.isString()) {
                        final String id_ = id.asString();
                        seen.add(id_);
                        if (contents.containsKey(id_)) {
                            updateOne(contents.get(id_), o, changed);
                        } else {
                            added.add(addOne(id_, o));
                        }
                    }
                }
            }

            for (final String missing : Sets.difference(contents.keySet(), seen)) {
                removed.add(contents.get(missing));
            }

            updated(removed.build(), changed.build(), added.build());
            for (final IScenarioRun run : contents.values()) {
                if (run.getState() == State.STARTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void updated(
            final Collection<? extends IScenarioRun> removed,
            final Collection<? extends IScenarioRun> changed,
            final Collection<? extends IScenarioRun> added) {
        for (final IScenarioRun a : added) contents.put(a.getID(), (RemoteRun) a);
        for (final IScenarioRun d : removed) contents.remove(d.getID());
        super.updated(removed, changed, added);
    }

    private IScenarioRun addOne(final String id_, final JsonObject o) {
        final String name = o.get(NAME).asString();
        final String user = o.get(USER).asString();
        final String state = o.getString("state", State.QUEUED.name());
        final double prog = o.getDouble("progress", 0d);
        final long dl = new Date().getTime();
        final long queued = o.getLong("queued", dl);
//        final long queued;
//        if (o.get("queued").isNumber()) {
//        	queued = o.getLong("queued", dl);
//        } else {
//        	queued = dl;
//        }
        final long changed = o.getLong("changed", dl);
        final String version = o.getString("version", "unknown");
        final int nparts = o.get("properties").asObject().names().size();

        return new RemoteRun(this, id_, version, name, user,
                nparts,
                new Date(queued), new Date(changed),
                State.valueOf(state.toUpperCase()), prog);
    }

    private void updateOne(
            final RemoteRun existing,
            final JsonObject o,
            final Builder<IScenarioRun> out) {
        // the only things which are allowed to change are
        // progress, state, and change date

        final String state = o.getString("state", State.QUEUED.name());
        final double prog = o.getDouble("progress", 0d);
        final long dl = new Date().getTime();
        final long changed = o.getLong("changed", dl);


        if (existing.update(State.valueOf(state.toUpperCase()),
                prog,
                new Date(changed))) {
            out.add(existing);
        }
    }

    @Override
    protected Optional<IScenarioRun> doGet(final String hash) {
        return Optional.<IScenarioRun>fromNullable(contents.get(hash));
    }

    @Override
    protected IScenarioRun doSubmit(
            final String hash,
            final String name,
            final String version,
            final Map<String, String> stockHashes,
            final Map<String, Path> stockFiles,
            final Iterable<String> snapshots,
            final boolean isBatch,
            final IProgressMonitor monitor) {
        HttpURLConnection c = null;
        try {
            uploadStocks(stockHashes, stockFiles, monitor);

            final String stocks;
            {
                final JsonObject o = Json.object();
                for (final Map.Entry<String, String> e : stockHashes.entrySet()) {
                    o.add(e.getKey(), e.getValue());
                }
                stocks = o.toString();
            }
            c = con(SLASH.join(JOBS, hash) + "/");
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            int part = 1;
            try (MultipartHelper h = new MultipartHelper(c)) {
                h.addField(NAME, name);
                h.addField(VERSION, version);
                h.addField(USER, user);
                h.addField(STOCKS, stocks);
                // TODO add batch metadata?
                for (final String s : snapshots) {
                    h.addField(PART + (part++), s);
                }
            }

            final Date now = new Date();
            return new RemoteRun(this, hash, version, name, user, isBatch ? (part-1) : 0, now, now, State.QUEUED, 0d);
        } catch (final ConnectException ce) {
            throw new RuntimeException(
                    "This computer could not connect to " + baseAddress +
                    ". The server may be off-line or the address could be incorrect, or this computer may have network problems.", ce);
        } catch (final UnknownHostException uhe) {
            throw new RuntimeException(
                    "This computer could not find the network address for " + baseAddress +
                    ". This computer may have network problems, or be connected to the wrong network, or the server address may be incorrect.", uhe);
        } catch (final IOException e) {

            if (c != null) {
                int responseCode = -1;
                String shortMessage = "(no message)";
                String longMessage = "(no message)";

                try {
                    responseCode = c.getResponseCode();
                    shortMessage = c.getResponseMessage();
                    final InputStream errorStream = c.getErrorStream();

                    if (errorStream != null) {
                        java.util.Scanner s = new java.util.Scanner(errorStream).useDelimiter("\\A");
                        longMessage = s.hasNext() ? s.next() : "";
                    }
                } catch (final IOException e2) { }

                throw new RuntimeException(
                    String.format("While communicating with %s there was an error.\nThe HTTP error code is %d\nThe short message is %s\nOther error output is:\n%s",
                                  baseAddress, responseCode, shortMessage, longMessage), e);
            } else {
                throw new RuntimeException("An unexpected error occurred when communicating with "+ baseAddress + ": " + e.getMessage(), e);
            }

        }
    }

    private void uploadStocks(
            final Map<String, String> stockHashes,
            final Map<String, Path> stockFiles,
            final IProgressMonitor monitor) throws IOException {
        // for each stock, we do a head request, and then upload if we need to.
        for (final String stockName : stockHashes.keySet()) {
            if (!stockExists(stockHashes.get(stockName))) {
                uploadStock(stockHashes.get(stockName), stockFiles.get(stockName));
            }
        }
    }

    private void uploadStock(final String hash, final Path path) throws IOException {
        final HttpURLConnection con = con(SLASH.join(STOCKS, hash));
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        try (final MultipartHelper h = new MultipartHelper(con)) {
            h.addFile("stock", path);
        }
    }

    private boolean stockExists(final String stockName) throws IOException {
        final HttpURLConnection con = con(SLASH.join(STOCKS, stockName));
        con.setRequestMethod("HEAD");
        final int code = con.getResponseCode();
        return code == HttpURLConnection.HTTP_OK;
    }

    public void delete(final RemoteRun remoteRun) {
        final HttpURLConnection c = con(SLASH.join(JOBS, remoteRun.getID()) + "/");

        try {
            c.setRequestMethod("DELETE");
            final int result = c.getResponseCode();
            if (! (result == HttpURLConnection.HTTP_OK ||
                    result == HttpURLConnection.HTTP_ACCEPTED ||
                    result == HttpURLConnection.HTTP_NO_CONTENT)) {
                throw new IOException("Expected response code 200 or 202 or 204, not " + result);
            }
            super.removed(remoteRun);
        } catch (final IOException e) {
            RemoteRunnerPlugin.getDefault().error("Deleting " + remoteRun.getID(), e);
        }
    }

    public Optional<InputStream> getPartStream(final RemoteRun remoteRun, final int part) {
    	final String join;
    	if(remoteRun.getType() == Type.Batch){
    		join = SLASH.join(JOBS, remoteRun.getID() + _PART + (1 + part), RESULTS);
    	} else {
    		if(part != 0){
    			throw new IllegalArgumentException("Expected just 1 part, but had " + part);
    		}
    		join = SLASH.join(JOBS, remoteRun.getID(), RESULTS);
    	}

		final HttpURLConnection c = con(join);
        try {
            final int status = c.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                return Optional.of(c.getInputStream());
            } else {
				throw new RuntimeException("HTTP Status code not OK");
			}
        } catch (final Throwable e) {
			try {
				RemoteRunnerPlugin.
					getDefault().warn(String.format("Attempt to get part %d of %s [%s] from URL %s produced status %d [%s].",
													part, remoteRun.getName(), remoteRun.getID(), join, c.getResponseCode(), c.getResponseMessage()),
									  e);
			} catch (final Throwable e2) {
				RemoteRunnerPlugin.
					getDefault().warn(e2.getMessage(), e2);

			}
		}
        return Optional.absent();
    }

    public Reader getLogText(final RemoteRun remoteRun) {
        final HttpURLConnection c = con(SLASH.join(JOBS, remoteRun.getID(), LOG));
        try {
            final int status = c.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                return new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8);
            } else {
                return new StringReader("ERROR: status code "+ status + " for " + c.getURL());
            }
        } catch (final IOException e) {
            RemoteRunnerPlugin.getDefault().error(e.getMessage(), e);
            return new StringReader("ERROR: " + e.getMessage());
        }
    }
}
