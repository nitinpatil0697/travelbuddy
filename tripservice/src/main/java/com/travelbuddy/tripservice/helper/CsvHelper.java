package com.travelbuddy.tripservice.helper;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.travelbuddy.tripservice.model.PlacesEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CsvHelper {

    public List<PlacesEntity> csvToPlaces(String filePath) {
        log.info("Importing CSV file: {}", filePath);
        List<PlacesEntity> placesList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            csvReader.readNext(); // Skip header line if present
            while ((values = csvReader.readNext()) != null) {
                log.info("Row: {}", values);
                //id,name,description,latitude,longitude,state,city,category,rating,created_at,updated_at
                PlacesEntity place = new PlacesEntity();
                place.setName(values[1]);
                place.setDescription(values[2]);
                place.setLatitude(Double.parseDouble(values[3]));
                place.setLongitude(Double.parseDouble(values[4]));
                //place.setState(values[5]);
                //place.setEntryFee(Double.parseDouble(values[8]));
                place.setRating(Double.parseDouble(values[8]));
                place.setCity(values[6]);
                //place.setCountry(values[8]);
                place.setType(values[7]);

                placesList.add(place);
            }
        } catch (IOException | CsvException e) {
            log.info("Error in reading file: {}", e.getMessage());
        }
        return placesList;
    }
}
