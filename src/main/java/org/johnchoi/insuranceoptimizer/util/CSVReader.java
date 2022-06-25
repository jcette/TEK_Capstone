package org.johnchoi.insuranceoptimizer.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.johnchoi.insuranceoptimizer.models.HealthCSV;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * This method is the first step to converting the data from the CSV file to a list of objects I use internally to ferry the data to the backend.
     * @param file
     * @return
     */
    public static List<HealthCSV> readCSV(MultipartFile file){
        List<HealthCSV> healthCSVs = new ArrayList<>(  );
        try (Reader reader = new BufferedReader( new InputStreamReader( file.getInputStream()))) {
            // create csv bean reader
            CsvToBean<HealthCSV> csvToBean = new CsvToBeanBuilder( reader )
                    .withType( HealthCSV.class )
                    .withIgnoreLeadingWhiteSpace( true )
                    .build( );

            // convert `CsvToBean` object to list of healthCSVs
            healthCSVs = csvToBean.parse( );
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return healthCSVs;
    }
}
