package uk.org.cse.nhm.reporting.standard.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;

public class TemplatedResourceOutput extends SizeRecordingReportOutput {

    public static String VELOCITY_TEMPLATE_EXT = "vm";
    private final String path;
    private final VelocityEngine templateEngine;
    private final Map<String, Object> parameters;
    private final String resourcePath;

    protected TemplatedResourceOutput(final String resourcePath, final String outputPath, final VelocityEngine templateEngine, final Map<String, Object> parameters) {
        this.templateEngine = templateEngine;
        this.parameters = parameters;
        this.resourcePath = resourcePath;
        this.path = outputPath;
    }

    public static class Builder {

        private String template = null;
        private String outputFilename = null;
        private Map<String, Object> parameters;
        private VelocityEngine engine;

        public Builder withTemplate(final VelocityEngine engine, final String template, final Map<String, Object> parameters) {
            this.template = template;
            this.engine = engine;
            this.parameters = parameters;
            return this;
        }

        public Builder withOutput(final String output) {
            this.outputFilename = output;
            return this;
        }

        public TemplatedResourceOutput build() {
            return new TemplatedResourceOutput(
                    this.template,
                    this.outputFilename,
                    this.engine,
                    this.parameters
            );
        }
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getTemplate() {
        return IReportOutput.GENERIC_TEMPLATE;
    }

    @Override
    public Type getType() {
        return Type.Chart;
    }

    @Override
    public void doWriteContent(final OutputStream outputStream) throws IOException {
        Template template;
        try {
            template = templateEngine.getTemplate(resourcePath);
        } catch (final ResourceNotFoundException ex) {
            throw new IllegalArgumentException("Could not find resource " + resourcePath, ex);
        }
        final Writer writer = new OutputStreamWriter(outputStream);
        template.merge(new VelocityContext(parameters), writer);
        writer.flush();
        outputStream.flush();
    }
}
