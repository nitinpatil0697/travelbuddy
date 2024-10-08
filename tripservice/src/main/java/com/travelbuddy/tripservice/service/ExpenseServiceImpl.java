package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.model.ExpenseEntity;
import com.travelbuddy.tripservice.repository.ExpenseRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ExpenseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepositoryInterface expenseRepositoryInterface;

    public ExpenseServiceImpl(ExpenseRepositoryInterface expenseRepositoryInterface) {
        this.expenseRepositoryInterface = expenseRepositoryInterface;
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
            ExpenseEntity tripExpense = new ExpenseEntity();
            tripExpense.setTripId(tripId);
            tripExpense.setTransportCost(transportCost);
            tripExpense.setHotelCost(hotelCost);
            tripExpense.setCityExpense(cityExpenses);
            tripExpense.setOtherCharges(otherCharges);
            expenseRepositoryInterface.save(tripExpense);
            log.info("Total estimated cost for trip {}: {}", tripId, totalEstimatedCost);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        return totalEstimatedCost;
    }
}
