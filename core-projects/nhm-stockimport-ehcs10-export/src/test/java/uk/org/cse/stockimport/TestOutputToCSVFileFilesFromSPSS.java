package uk.org.cse.stockimport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.ehcs2010.spss.builders.HouseCaseFromSpssElementBuilder;
import uk.org.cse.stockimport.spss.ReaderBuilderPair;
import uk.org.cse.stockimport.spss.elementreader.ISpssReader;
import uk.org.cse.stockimport.util.Path;

@RunWith(MockitoJUnitRunner.class)
public class TestOutputToCSVFileFilesFromSPSS extends Mockito {

    ReaderBuilderPair<IHouseCaseDTO> builderPair;

    @Mock
    ISpssReader<IHouseCaseDTO> reader;

    IHomElementBuilder<IHouseCaseDTO> builder;

    private final String testZipFile = Path.file("src", "test", "resources", "output.zip");
    private final File zipFile = new File(testZipFile);

    List<IHouseCaseDTO> houseCaseDTOs;
    final int numOfRows = 16670;

    @Before
    public void initailiseTest() {
        builder = new HouseCaseFromSpssElementBuilder();
        builderPair = new ReaderBuilderPair<IHouseCaseDTO>(reader, builder);

        houseCaseDTOs = buildHouseCaseDTOs(numOfRows);
        when(reader.next()).thenReturn(houseCaseDTOs);
    }

    @Test
    public void HouseCaseCSVFileShouldContainCorrectNumberOfRows() throws Exception {
        final FileOutputStream fos = new FileOutputStream(zipFile);
        final ZipOutputStream zos = new ZipOutputStream(fos);

        zos.putNextEntry(new ZipEntry("housecase.csv"));
        builderPair.moveNext();
        builderPair.writeChunk(zos);

        zos.closeEntry();
        zos.close();
    }

    private List<IHouseCaseDTO> buildHouseCaseDTOs(final int number) {
        final List<IHouseCaseDTO> houseCaseDTO = new ArrayList<IHouseCaseDTO>();

        IHouseCaseDTO dto;
        for (int ct = 1; ct <= number; ct++) {
            dto = new HouseCaseDTO();
            dto.setAacode("" + ct);
            houseCaseDTO.add(dto);
        }

        return houseCaseDTO;
    }
}
