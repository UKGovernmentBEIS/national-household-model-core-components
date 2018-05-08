package uk.org.cse.nhm.reporting.standard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class TemplatedResourceOutputTest {

    public static final VelocityEngine templateEngine = new VelocityEngine(
            defaultVelocityEngineProperties()
    );

    private static Properties defaultVelocityEngineProperties() {
        final Properties p = new Properties();

        p.put("resource.loader", "class");
        p.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return p;
    }

    @Test
    public void testTemplateWithNoParameters() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final TemplatedResourceOutput resource
                = TemplatedResourceOutput.create()
                        .withTemplate(templateEngine, "templates/template.vm", Collections.<String, Object>emptyMap())
                        .withOutput("templates/template.txt")
                        .build();

        resource.doWriteContent(outputStream);

        Assert.assertEquals("Resource should have loaded the content of the template file", "this is a test template", new String(outputStream.toByteArray()));
    }

}
