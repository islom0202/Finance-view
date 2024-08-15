package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class PredictionHistory {
    private Long id;                              // Unique identifier for the prediction history entry
    private Long dataId;                          // Reference to the dataset used for the prediction
    private Map<String, Object> inputData;        // Input data that was used for the prediction
    private Map<String, Object> predictionResult; // The result of the prediction
    private String modelName;                     // Name of the model used for the prediction (if applicable)
    private LocalDateTime timestamp;              // Timestamp when the prediction was made
    private Map<String, String> additionalParams; // Additional parameters used during prediction (if any)
}
