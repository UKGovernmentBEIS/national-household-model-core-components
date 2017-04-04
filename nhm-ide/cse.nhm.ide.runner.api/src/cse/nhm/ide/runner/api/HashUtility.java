package cse.nhm.ide.runner.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import uk.org.cse.nhm.bundle.api.IRunInformation;

public class HashUtility {
	static String hashOneFile(final Path path) throws IOException {
		return Files.hash(path.toFile(), Hashing.sha256()).toString();
	}

	static String hashRun(final Iterable<String> snapshots, final boolean isBatch, final String version, final Map<String, String> stockMapping) {
		final HashFunction sha256 = Hashing.sha256();
		final Hasher h = sha256.newHasher();
		
		for (final String s :snapshots) {
			h.putString(s, StandardCharsets.UTF_8);
		}

		h.putBoolean(isBatch);
		
		h.putString(version, StandardCharsets.UTF_8);
			
		for (final String s : stockMapping.keySet()) {
			h.putString(s, StandardCharsets.UTF_8);
			h.putString("=", StandardCharsets.UTF_8);
			h.putString(stockMapping.get(s), StandardCharsets.UTF_8);
		}
		
		return h.hash().toString();
	}
	
	public static String hashRun(
			final String version,
			final IRunInformation<IPath> information,
			final ImmutableMap.Builder<String, String> hashesOut,
			final ImmutableMap.Builder<String, Path> pathsOut) throws IOException {
		for (final Map.Entry<String, IPath> stocks : information.stocks().entrySet()) {
			final IResource resOfStockInWorkspace = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(stocks.getValue());
			if (resOfStockInWorkspace == null || !resOfStockInWorkspace.exists()) {
				throw new RuntimeException("Stock file " + stocks.getValue() + " does not exist!");
			}
			final Path pathOfStockInWorkspace = resOfStockInWorkspace.getRawLocation().toFile().toPath();
			final String stockHash = HashUtility.hashOneFile(pathOfStockInWorkspace);
			hashesOut.put(stocks.getKey(), stockHash);
			pathsOut.put(stocks.getKey(), pathOfStockInWorkspace);
		}
		return hashRun(information.snapshots(), information.isBatch(), version, hashesOut.build());
	}
	
}
