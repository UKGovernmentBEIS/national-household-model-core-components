package uk.org.cse.nhm.language.builder.guice;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import uk.org.cse.nhm.simulator.guice.SimulationScoped;
import uk.org.cse.nhm.simulator.main.Initializable;

@SimulationScoped
public class InitializableListener implements TypeListener {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(InitializableListener.class);
	private List<Initializable> contents = new ArrayList<Initializable>();

	@Override
	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		if (Initializable.class.isAssignableFrom(type.getRawType())) {
			encounter.register(new InjectionListener<I>() {
				@Override
				public void afterInjection(I injectee) {
					if (injectee instanceof Initializable) {
						log.debug("will initialize {}", injectee);
						contents.add((Initializable) injectee);
					}
				}
			});
		}
	}

	public List<Initializable> getInitializables() {
		return contents ;
	}

	public void clear() {
		contents.clear();
	}
}
