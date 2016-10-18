package uk.org.cse.nhm.reporting.guice;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

public class VelocityModule extends AbstractModule {
	private static final Logger log = LoggerFactory.getLogger(VelocityModule.class);
	
	
	private static final LogChute logChute = new LogChute(){	
		@Override
		public void log(final int level, final String message, final Throwable t) {
			switch (level) {
			case TRACE_ID:log.trace(message, t);break;
			case DEBUG_ID:log.debug(message, t);break;
			case ERROR_ID:log.error(message, t);break;
			case INFO_ID:log.info(message, t);break;
			case WARN_ID:log.warn(message, t);break;
			}
		}
	
		@Override
		public void log(final int level, final String message) {
			switch (level) {
			case TRACE_ID:log.trace(message);break;
			case DEBUG_ID:log.debug(message);break;
			case ERROR_ID:log.error(message);break;
			case INFO_ID:log.info(message);break;
			case WARN_ID:log.warn(message);break;
			}
		}
	
		@Override
		public boolean isLevelEnabled(final int level) {
			switch (level) {
			case TRACE_ID:return log.isTraceEnabled();
			case DEBUG_ID:return log.isDebugEnabled();
			case ERROR_ID:return log.isErrorEnabled();
			case INFO_ID:return log.isInfoEnabled();
			case WARN_ID:return log.isWarnEnabled();
			default:
				return false;
			}
		}
	
		@Override
		public void init(final RuntimeServices rs) throws Exception {
			
		}
	};
	
	@Override
	protected void configure() {
		bind(VelocityEngine.class).toInstance(createVelocityEngine());
	}

	private VelocityEngine createVelocityEngine() {
		final Properties p = new Properties();
		
		p.put("resource.loader", " classpath");
		p.put("classpath.resource.loader.description", "Velocity Classpath Resource Loader");
		p.put("classpath.resource.loader.class", " org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		p.put("classpath.resource.loader.cache", " false");

		p.put(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, logChute);
		
		return new VelocityEngine(p);
	}
}
