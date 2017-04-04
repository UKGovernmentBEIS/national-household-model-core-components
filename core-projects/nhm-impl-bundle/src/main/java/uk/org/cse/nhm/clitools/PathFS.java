package uk.org.cse.nhm.clitools;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import uk.org.cse.nhm.bundle.api.IFS;

public class PathFS implements IFS<Path> {

	public static final IFS<Path> INSTANCE = new PathFS();
	
	@Override
	public Path deserialize(String arg0) {
		return Paths.get(arg0);
	}

	@Override
	public Path filesystemPath(Path arg0) {
		return arg0;
	}

	@Override
	public Reader open(Path arg0) throws IOException {
		return Files.newBufferedReader(arg0, StandardCharsets.UTF_8);
	}

	@Override
    public Path resolve(String arg0, String arg1) {
        if (arg0 == null || arg1 == null) throw new IllegalArgumentException("Attempted to resolve the path fragment " + arg1 + " against the path " + arg0);
        final Path p = deserialize(arg0);
        if (p == null) throw new IllegalArgumentException("The path " + arg0 + " de-serialized to null");
        final Path parent = p.getParent();
        if (parent == null) throw new IllegalArgumentException("The path " + p + " has a null parent path");
        final Path result = parent.resolve(arg1);
        return result;
	}

}
