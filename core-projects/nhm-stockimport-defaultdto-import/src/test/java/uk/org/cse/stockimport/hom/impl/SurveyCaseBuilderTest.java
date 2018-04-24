package uk.org.cse.stockimport.hom.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

public class SurveyCaseBuilderTest {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSurveyCaseBuilder() {
		for (int i = 0; i<25; i++) {
			final ISurveyCaseBuildStep step1 = mock(ISurveyCaseBuildStep.class);
			final ISurveyCaseBuildStep step2 = mock(ISurveyCaseBuildStep.class);
			final ISurveyCaseBuildStep step3 = mock(ISurveyCaseBuildStep.class);

			when(step1.getIdentifier()).thenReturn("A");
			when(step2.getIdentifier()).thenReturn("B");
			when(step3.getIdentifier()).thenReturn("C");
			when(step1.getDependencies()).thenReturn(Collections.<String>emptySet());
			when(step2.getDependencies()).thenReturn(ImmutableSet.of("A"));
			when(step3.getDependencies()).thenReturn(ImmutableSet.of("B", "A"));

			final List<ISurveyCaseBuildStep> steps = new ArrayList<ISurveyCaseBuildStep>();
			steps.add(step1);
			steps.add(step2);
			steps.add(step3);

			Collections.shuffle(steps);

			final SurveyCaseBuilder builder = new SurveyCaseBuilder();
			for (final ISurveyCaseBuildStep step : steps) {
				builder.addStep(step);
			}
			builder.initialize();
			final IHouseCaseSources provider = mock(IHouseCaseSources.class);
			final SurveyCase build = builder.build(provider);

			final InOrder inOrder = Mockito.inOrder(step1, step2, step3);

			Assert.assertNotNull(build);
			inOrder.verify(step1).build(build, provider);
			inOrder.verify(step2).build(build, provider);
			inOrder.verify(step3).build(build, provider);
		}
	}
}
