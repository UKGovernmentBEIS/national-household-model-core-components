package cse.nhm.ide.runner.local;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.ServiceReference;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.ui.NHMUIPlugin;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IRunInformation;
import uk.org.cse.nhm.bundle.api.ISimulationCallback;
import uk.org.cse.nhm.bundle.api.IValidationResult;

public class LocalScenarioRun implements IScenarioRun {
	private static final String STOCKS_BY_HASH = "stocks-by-hash";

	static final String RUNS = "runs";

	private static final String META_FILE_NAME = "metadata";
	private static final String OUTPUT_ZIP_FILE_NAME = "output.";
	private static final String SCENARIO_DATA_FILE_NAME = "data.";
	private static final String LOG_FILE_NAME = "run-log.txt";

	private final Path path;
	private final Path stocksDirectory;

	private final String name;
	private final String version;

	private final int parts;
	private final boolean batch;

	private final Map<String, String> stockMapping = new HashMap<>();

	private final LocalScenarioRunner runner;
	private State state = State.QUEUED;
	protected double progress;

	private LocalScenarioRun(final LocalScenarioRunner runner, final Path path, final Path stocksDirectory) {
		super();
		this.runner = runner;
		this.path = path;
		this.stocksDirectory = stocksDirectory;

		boolean batch;
		int parts;
		String name;
		String version;
		try (final BufferedReader r = Files.newBufferedReader(path.resolve(META_FILE_NAME), StandardCharsets.UTF_8)) {
			final JsonValue meta = Json.parse(r);
			final JsonObject o = meta.asObject();

			name = o.getString("name", getID());
			version = o.getString("version", "unknown");
			parts = o.getInt("parts", 1);
			batch = o.getBoolean("batch", false);
			final JsonObject stocks = o.get("stocks").asObject();
			for (final String k : stocks.names()) {
				stockMapping.put(k, stocks.getString(k, ""));
			}
		} catch (final Throwable th) {
			name = getID();
			version = "unknown";
			parts = 1;
			batch = false;
			LocalRunnerPlugin.logInformation("An exception happened when loading a previous run: %s", th.getMessage());
		}
		this.batch = batch;
		this.name = name;
		this.version = version;
		this.parts = parts;

		if (Files.exists(path.resolve(OUTPUT_ZIP_FILE_NAME + (parts - 1)))) {
			state = State.FINISHED;
		}
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getID() {
		return path.getFileName().toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUser() {
		return "Local user";
	}

	@Override
	public Date getQueuedDate() {
		try {
			return new Date(Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes()
					.creationTime().toMillis());
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Date getChangeDate() {
		return new Date();
	}

	@Override
	public Reader getLogText() {
		try {
			// this is lame and needs an if(i am live)
			return Files.newBufferedReader(path.resolve(LOG_FILE_NAME), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			return new StringReader("");
		}
	}

	@Override
	public double getProgress() {
		return progress;
	}

	@Override
	public Type getType() {
		return batch ? Type.Batch : Type.Scenario;
	}
	
	@Override
	public int getNumberOfParts() {
		return parts;
	}

	@Override
	public Optional<InputStream> getPartStream(final int part) {
		try {
			return Optional.of(Files.newInputStream(path.resolve(OUTPUT_ZIP_FILE_NAME + part)));
		} catch (final IOException e) {
			NHMUIPlugin.logInformation("Exception getting part %d of %s", part, name);
			return Optional.absent();
		}
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void delete() {
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		wasDeleted();
	}

	@Override
	public IScenarioRunner getRunner() {
		return runner;
	}

	public static LocalScenarioRun existing(final LocalScenarioRunner runner, final Path storePath,
			final String hashName) {
		return new LocalScenarioRun(runner, storePath.resolve(RUNS).resolve(hashName),
				storePath.resolve(STOCKS_BY_HASH));
	}

	public static IScenarioRun of(final LocalScenarioRunner runner, final Path storePath, final String hash,
			final String name, final String version, final Map<String, String> stockHashes,
			final Map<String, Path> stockFiles, final Iterable<String> snapshotText, final boolean isBatch)
					throws IOException {
		final Path stockStorePath = storePath.resolve(STOCKS_BY_HASH);
		Files.createDirectories(stockStorePath);
		final JsonObject stockMapping = new JsonObject();

		for (final Map.Entry<String, String> e : stockHashes.entrySet()) {
			stockMapping.add(e.getKey(), e.getValue());
			final Path pathOfStockInStore = stockStorePath.resolve(e.getValue());
			if (!Files.exists(pathOfStockInStore)) {
				Files.copy(stockFiles.get(e.getKey()), pathOfStockInStore);
			}
		}

		final Path resultStorePath = storePath.resolve(RUNS).resolve(hash);
		if (!Files.exists(resultStorePath)) {
			// save data into result store path
			Files.createDirectories(resultStorePath);
			int part = 0;
			for (final String s : snapshotText) {
				spit(resultStorePath, SCENARIO_DATA_FILE_NAME + (part++), s);
			}

			final JsonObject meta = new JsonObject();

			meta.add("name", name);
			meta.add("version", version);
			meta.add("parts", part);
			meta.add("batch", isBatch);

			meta.add("stocks", stockMapping);

			spit(resultStorePath, META_FILE_NAME, meta.toString());

			final LocalScenarioRun result = existing(runner, storePath, hash);
			LocalRunnerPlugin.logInformation("Started a new run with hash %s [%s]", hash, result.getName());
			result.wasCreated();

			return result;
		} else {
			final IScenarioRun result = existing(runner, storePath, hash);
			LocalRunnerPlugin.logInformation("Identical run with hash %s already exists [%s]", hash, result.getName());
			return result;
		}
	}

	private static void spit(final Path resultStorePath, final String scenarioDataFileName, final String snapshot)
			throws IOException {
		try (final BufferedWriter w = Files.newBufferedWriter(resultStorePath.resolve(scenarioDataFileName),
				StandardCharsets.UTF_8)) {
			w.write(snapshot);
		}
	}

	private static String slurp(final Path file) throws IOException {
		return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
	}

	public void run(final IProgressMonitor monitor) {
		try {
			final ServiceReference<INationalHouseholdModel> modelRef = LocalRunnerPlugin.getPlugin().findModel(version);
			try (final BufferedWriter runLog = Files.newBufferedWriter(path.resolve(LOG_FILE_NAME),
					StandardCharsets.UTF_8)) {
				if (modelRef == null) {
					// die usefully.
					state = State.ERROR;
					runLog.write("Model version " + version + " is not installed; could not run scenario.");
					return;
				}

				state = State.STARTED;

				wasUpdated();

				try {
					final INationalHouseholdModel model = LocalRunnerPlugin.getPlugin().getBundle().getBundleContext()
							.getService(modelRef);

					// find a model with the desired version

					final ImmutableMap.Builder<String, Path> _stocks = ImmutableMap.builder();

					for (final Map.Entry<String, String> e : stockMapping.entrySet()) {
						_stocks.put(e.getKey(), stocksDirectory.resolve(e.getValue()));
					}

					final Map<String, Path> stocks = _stocks.build();
					for (int part = 0; part < parts; part++) {
						final String data = slurp(path.resolve(SCENARIO_DATA_FILE_NAME + part));
						actuallyRunModel(part, parts, runLog, model, data, stocks, monitor);
					}
				} finally {
					if (state != State.ERROR)
						state = State.FINISHED;
					wasUpdated();
					if (modelRef != null) {
						LocalRunnerPlugin.getPlugin().getBundle().getBundleContext().ungetService(modelRef);
					}
				}
			}
		} catch (final IOException ex) {
			LocalRunnerPlugin.logException("Exception running " + name, ex);
		} finally {
			wasUpdated();
		}
	}

	private void wasUpdated() {
		runner.jobsUpdated(Collections.<LocalScenarioRun> emptySet(), Collections.<LocalScenarioRun> singleton(this),
				Collections.<LocalScenarioRun> emptySet());
	}

	private void wasDeleted() {
		runner.jobsUpdated(Collections.<LocalScenarioRun> singleton(this), Collections.<LocalScenarioRun> emptySet(),
				Collections.<LocalScenarioRun> emptySet());
	}

	private void wasCreated() {
		runner.jobsUpdated(Collections.<LocalScenarioRun> emptySet(), Collections.<LocalScenarioRun> emptySet(),
				Collections.<LocalScenarioRun> singleton(this));
	}

	private void actuallyRunModel(final int part, final int partCount, final BufferedWriter runLog,
			final INationalHouseholdModel model, final String data, final Map<String, Path> stocks, final IProgressMonitor monitor) {

		model.simulate(PathFS.INSTANCE, new IRunInformation<Path>() {
			@Override
			public List<String> snapshots() {
				return Collections.singletonList(data);
			}

			@Override
			public Map<String, Path> stocks() {
				return stocks;
			}

			@Override
			public boolean isBatch() {
				return batch;
			}
		}, new ISimulationCallback<String>() {
			@Override
			public void cancelled() {
				state = State.ERROR;
				log("INFO", "Run cancelled");
			}

			@Override
			public void failed() {
				state = State.ERROR;
				log("ERROR", "Run failed");
			}

			@Override
			public void invalid(final IValidationResult<String> arg0) {
				state = State.ERROR;
				log("ERROR", "Scenario is not valid");
			}

			@Override
			public void log(final String arg0, final String arg1) {
				try {
					runLog.write('[');
					runLog.write(arg0);
					runLog.write("] ");
					runLog.write(arg1);
                    runLog.write('\n');
                    runLog.flush();
					monitor.subTask(arg1);
					wasUpdated();
				} catch (final IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}

			@Override
			public void progress(final double arg0) {
				log("PROGRESS", (int) (100 * arg0) + "% complete");
				final double oldProgress = LocalScenarioRun.this.progress;
				
				LocalScenarioRun.this.progress = (part + arg0) / partCount;
				
				monitor.worked((int) ((LocalScenarioRun.this.progress - oldProgress) * 100));
				
				wasUpdated();
			}

			@Override
			public boolean shouldCancel() {
				return Thread.currentThread().isInterrupted();
			}

			@Override
			public void completed(final Path arg0) {
				log("INFO", "Completed part " + (part + 1) + " of " + partCount);
				try {
					Files.move(arg0, path.resolve(OUTPUT_ZIP_FILE_NAME + part));
				} catch (final IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				} finally {
					wasUpdated();
				}
			}
		});
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LocalScenarioRun) {
			return ((LocalScenarioRun) obj).path.equals(path);
		}
		return false;
	}
}
