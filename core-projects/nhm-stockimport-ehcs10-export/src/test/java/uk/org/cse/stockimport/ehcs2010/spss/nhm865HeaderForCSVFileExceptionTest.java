package uk.org.cse.stockimport.ehcs2010.spss;

import static org.junit.Assert.fail;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class nhm865HeaderForCSVFileExceptionTest {
	
	@Mock OutputStream outputStream;

	@Test
	public void IfDTOListHasNoElementsThenHeaderShouldNotBeCreated() throws Exception {
		TestHomElementBuilder builder = new TestHomElementBuilder();
		List<Object> elements = new ArrayList<>();
		
		try{
			builder.writeChunk(outputStream, elements, true);
		} catch (Exception e){
			fail("Should not have failed building header");
		}
	}
	
	class TestHomElementBuilder extends AbstractCSVHomElementBuilder<Object>{

		@Override
		public String getFileName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] buildHeader(Object exampleElement) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] buildRow(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getBuiltClassName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
