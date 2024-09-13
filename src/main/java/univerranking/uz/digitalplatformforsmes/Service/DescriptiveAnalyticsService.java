package univerranking.uz.digitalplatformforsmes.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import univerranking.uz.digitalplatformforsmes.Dto.DescriptiveStats;
import univerranking.uz.digitalplatformforsmes.Entity.DataEntries;
import univerranking.uz.digitalplatformforsmes.Entity.DepictiveStatus;
import univerranking.uz.digitalplatformforsmes.Entity.PrescriptiveAction;
import univerranking.uz.digitalplatformforsmes.Repository.DataEntriesRepository;
import univerranking.uz.digitalplatformforsmes.Repository.DepictiveRepo;
import univerranking.uz.digitalplatformforsmes.Repository.PrecpictiveActionRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DescriptiveAnalyticsService {

    private final DataEntriesRepository dataEntriesRepository;
    private final DepictiveRepo depictiveRepo;
    private final PrecpictiveActionRepo precpictiveActionRepo;

    public List<DataEntries> getHistoricalData(Long metricId, LocalDate startDate, LocalDate endDate) {
        return dataEntriesRepository.findByRecordedAtBetweenAndAndMetrics_Id(startDate.atStartOfDay(), endDate.atStartOfDay(), metricId);
    }

    public DescriptiveStats calculateDescriptiveStats(List<DataEntries> data) {
        double sum = 0.0;
        double max = 0.0;
        double min = data.isEmpty() ? 0.0 : data.getFirst().getValue();
        double mean;
        double variance = 0.0;

        for (DataEntries entry : data) {
            double value = entry.getValue();
            sum += value;
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        mean = sum / data.size();

        for (DataEntries entry : data) {
            double diff = entry.getValue() - mean;
            variance += diff * diff;
        }

        double stddev = Math.sqrt(variance / data.size());

        DepictiveStatus status = new DepictiveStatus();
        status.setSum(sum);
        status.setMax(max);
        status.setMin(min);
        status.setMean(mean);
        status.setStandardDeviation(stddev);
        depictiveRepo.save(status);
        return new DescriptiveStats(sum, mean, max, min, stddev);
    }

    public List<PredictedSales> forecastSalesWithPython(Long metricId, LocalDate startDate, LocalDate endDate) throws IOException, JsonProcessingException {
        List<DataEntries> salesData = getHistoricalData(metricId, startDate, endDate);

        // Extend the prediction period by one month after endDate
        LocalDate predictionStartDate = endDate.plusDays(1);
        LocalDate predictionEndDate = predictionStartDate.plusMonths(1);

        // Create ObjectMapper and register the JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Write JSON data to a .json file
        File file = new File("/home/islom/DigitalPlatformForSMEs/src/main/resources/python/data1.json");
        objectMapper.writeValue(file, salesData);

        String scriptPath = "/home/islom/DigitalPlatformForSMEs/src/main/resources/python/forecast_script.py";
        String startDateStr = startDate.toString();
        String output = String.valueOf(runPythonScript(scriptPath, file, startDateStr));

        // Parse Python output and ensure predictions are for the extended period
        return parsePredictionResults(output);
    }

    private List<PredictedSales> parsePredictionResults(String output) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.readValue(output, objectMapper.getTypeFactory().constructCollectionType(List.class, PredictedSales.class));
    }

    private List<PredictedSales> filterPredictionsForNextMonth(List<PredictedSales> predictions, LocalDate predictionStartDate, LocalDate predictionEndDate) {
        // Debug: Print all predictions before filtering
        System.out.println("All predictions: " + predictions);

        // Filter or adjust predictions based on the extended period
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<PredictedSales> filtered = predictions.stream()
                .filter(prediction -> {
                    LocalDate predictionDate = LocalDate.parse(prediction.getDate(), formatter);
                    return !predictionDate.isBefore(predictionStartDate) && !predictionDate.isAfter(predictionEndDate);
                })
                .collect(Collectors.toList());

        // Debug: Print filtered predictions
        System.out.println("Filtered predictions: " + filtered);

        return filtered;
    }

    public StringBuilder runPythonScript(String scriptPath, File file, String startDate) throws IOException {
        String absoluteScriptPath = new File(scriptPath).getAbsolutePath();
        System.out.println("Absolute path to Python script: " + absoluteScriptPath);

        ProcessBuilder processBuilder = new ProcessBuilder("python3", absoluteScriptPath, file.getAbsolutePath(), startDate);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            // Read the output from the Python script
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // Wait for the process to complete
            try {
                process.waitFor(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IOException("Error while waiting for Python script to complete", e);
            }

            // Print the output for debugging
            System.out.println("Python script output: " + output);

            return output;
        } catch (IOException e) {
            throw new IOException("Error while running Python script", e);
        }
    }

    public PrescriptiveAction recommendAction(List<PredictedSales> forecastedSales) {
        // Initialize flags to determine which action to take
        boolean highSales = false;
        boolean lowSales = false;
        // Determine overall sales trend
        for (PredictedSales prediction : forecastedSales) {
            if (prediction.getValue() > 10000.0) {
                highSales = true;
            } else {
                lowSales = true;
            }
        }
        // Decide the action based on the flags
        if (highSales && lowSales) {
            // If there are both high and low sales, you might need to decide based on the majority or other criteria
            // For example, you might want to choose the action based on the average sales value
            double averageSales = forecastedSales.stream()
                    .mapToDouble(PredictedSales::getValue)
                    .average()
                    .orElse(0.0);

            if (averageSales > 10000.0) {
                PrescriptiveAction action = new PrescriptiveAction();
                action.setActionName("Increase Inventory");
                action.setReason("Average projected sales exceed 10,000 units.");
                action.setCreatedAt(LocalDateTime.now());
                precpictiveActionRepo.save(action);
                return action;
            } else {
                PrescriptiveAction action = new PrescriptiveAction();
                action.setActionName("Reduce Marketing Spend");
                action.setReason("Average projected sales are low; reduce marketing costs.");
                action.setCreatedAt(LocalDateTime.now());
                precpictiveActionRepo.save(action);
                return action;
            }
        } else if (highSales) {
            return new PrescriptiveAction("Increase Inventory", "Projected sales exceed 10,000 units.", LocalDateTime.now());
        } else {
            return new PrescriptiveAction("Reduce Marketing Spend", "Projected sales are low; reduce marketing costs.", LocalDateTime.now());
        }
    }


}
