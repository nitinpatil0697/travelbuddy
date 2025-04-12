package com.travelbuddy.tripservice.service;

import com.travelbuddy.hotelservice.model.HotelEntity;
import com.travelbuddy.tripservice.api.request.CalculateExpenseRequest;
import com.travelbuddy.tripservice.api.request.Place;
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

import static java.util.Objects.isNull;

@Service
public class ExpenseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepositoryInterface expenseRepositoryInterface;
    private final PlacesRepositoryInterface placesRepositoryInterface;
    private final WebClient webClient;
    private final PlacesServiceImpl placesServiceImpl;

    public ExpenseServiceImpl(
            ExpenseRepositoryInterface expenseRepositoryInterface,
            PlacesRepositoryInterface placesRepositoryInterface,
            WebClient.Builder webClientBuilder,
            PlacesServiceImpl placesServiceImpl)
    {
        this.expenseRepositoryInterface = expenseRepositoryInterface;
        this.placesRepositoryInterface = placesRepositoryInterface;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.placesServiceImpl = placesServiceImpl;
    }

    /**
     * Calculate total Estimate Cost
     *
     * @return
     */
    public Double calculateTotalEstimateCost(CalculateExpenseRequest expenseRequest) {
        log.info("Calculating total estimate cost for trip {}", expenseRequest);
        double totalEstimatedCost = 0.0;
        try {
            Double cityExpense = 0.0;
            for (Place place : expenseRequest.getPlaces()) {
                if (!isNull(place.getEstimatedCost())) {
                    cityExpense += place.getEstimatedCost();
                }
            }

            Double accomodationCost = expenseRequest.getAccommodationPerNight() * expenseRequest.getNumberOfDays();
            totalEstimatedCost = expenseRequest.getTransportationCost() + accomodationCost
                     + cityExpense;

            // Saving in Expense table DB
            ExpenseEntity tripExpense = ExpenseEntity.builder()
                    .tripId(expenseRequest.getTripId())
                    .transportCost(expenseRequest.getTransportationCost())
                    .hotelCost(expenseRequest.getAccommodationPerNight() * expenseRequest.getNumberOfDays())
                    .cityExpense(cityExpense)
                    .otherCharges(0.0)
                    .build();
            expenseRepositoryInterface.save(tripExpense);
            log.info("Total estimated cost for trip {}: {}", expenseRequest.getTripId(), totalEstimatedCost);
        } catch (Exception e) {
            log.error("calculate Total EstimateCost {}", e.getMessage());
        }
        return totalEstimatedCost;
    }

    /**
     * Prepare all Expense
     *
     * @param expenseRequest
     * @return
     */
    public double prepareAllExpenses(CalculateExpenseRequest expenseRequest) {
        HashMap<String,Double> allExpensesMap = new HashMap<>();
        Double totalEstimatedCost = 0.0;
        try {
            totalEstimatedCost = calculateTotalEstimateCost(expenseRequest);
        } catch (Exception e) {
            log.error("prepareAllExpenses {}", e.getMessage());
        }
        return totalEstimatedCost;
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

    /**
     * To get City expense
     *
     * @param cityCode
     * @return
     */
    public Double getCityExpenses(String cityCode) {
        PlacesEntity place = placesServiceImpl.getPlaceByCode(cityCode);
        return place.getEstimatedCharges();
    }
}
