package com.travelbuddy.tripservice.service;

import com.travelbuddy.hotelservice.model.HotelEntity;
import com.travelbuddy.tripservice.model.ExpenseEntity;
import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.repository.ExpenseRepositoryInterface;
import com.travelbuddy.tripservice.repository.PlacesRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ExpenseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepositoryInterface expenseRepositoryInterface;
    private final PlacesRepositoryInterface placesRepositoryInterface;
    private final WebClient webClient;

    public ExpenseServiceImpl(
            ExpenseRepositoryInterface expenseRepositoryInterface,
            PlacesRepositoryInterface placesRepositoryInterface,
            WebClient.Builder webClientBuilder
            )
    {
        this.expenseRepositoryInterface = expenseRepositoryInterface;
        this.placesRepositoryInterface = placesRepositoryInterface;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    /**
     * Calculate total Estimate Cost
     *
     * @param allExpenses
     * @param tripId
     * @return
     */
    public Double calculateTotalEstimateCost(HashMap<String,Double> allExpenses, Long tripId) {
        log.info("Calculating total estimate cost for trip {}", tripId);
        double totalEstimatedCost = 0.0;
        try {
            Double transportCost = allExpenses.get("transport_cost");
            Double hotelCost = allExpenses.get("hotel_cost");
            Double cityExpenses = allExpenses.get("city_expenses"); // (e.g., food, local)
            Double otherCharges = allExpenses.get("other_charges");  // (e.g., tickets, miscellaneous)
            totalEstimatedCost = transportCost + hotelCost + cityExpenses + otherCharges;

            // Saving in Expense table DB
            ExpenseEntity tripExpense = ExpenseEntity.builder()
                    .tripId(tripId)
                    .transportCost(transportCost)
                    .hotelCost(hotelCost)
                    .cityExpense(cityExpenses)
                    .otherCharges(otherCharges)
                    .build();
            expenseRepositoryInterface.save(tripExpense);
            log.info("Total estimated cost for trip {}: {}", tripId, totalEstimatedCost);
        } catch (Exception e) {
            log.error("calculate Total EstimateCost {}", e.getMessage());
        }

        return totalEstimatedCost;
    }

    public HashMap<String, Double> prepareAllExpenses(HashMap<String, String> prepareExpenseReq) {
        HashMap<String,Double> allExpensesMap = new HashMap<>();
        try {
            // Destination City, origin city, trip type, number of days,
            // transport -> transport id { includes all the routes and possible data}
            // get transport Cost - get it from Transport Service
            // get Hotel Cost - get it from Hotel Service
            String hotelCodes = "";
            // get City expenses
            PlacesEntity destinationCity = placesRepositoryInterface.getByPlaceId(prepareExpenseReq.get("destination"));
            double cityExpenses = destinationCity.getEstimatedCharges();
            // get Other charges - includes service charges. get it from table
            Double transportCost = 0.0;
            Double hotelCost = getHotelCost(hotelCodes);
            Double otherCharges = 0.0;

            allExpensesMap.put("transport_cost", transportCost);
            allExpensesMap.put("hotel_cost", hotelCost);
            allExpensesMap.put("city_expenses", cityExpenses);
            allExpensesMap.put("other_charges", otherCharges);
            double totalEstimatedCost = calculateTotalEstimateCost(allExpensesMap, destinationCity.getId());
            allExpensesMap.put("total_estimated_cost", totalEstimatedCost);
        } catch (Exception e) {
            log.error("prepareAllExpenses {}", e.getMessage());
        }
        return allExpensesMap;
    }

    public Double getHotelCost(String hotelCodes) throws UnsupportedEncodingException {
        String encodedCodes = URLEncoder.encode(hotelCodes, StandardCharsets.UTF_8);

        // TODO : Work : get list of hotels in result node
        Mono<HotelEntity> hotelData =  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel/codes")
                        .queryParam("code", encodedCodes)  // This will handle the proper insertion of the encoded parameter
                        .build())
                .retrieve()
                .bodyToMono(HotelEntity.class);

        return Objects.requireNonNull(hotelData.block()).getCost();
    }
}
