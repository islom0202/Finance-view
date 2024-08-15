package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;

import java.util.Map;

@Data
public class PredictionRequest {
    private Long dataId;                        // Reference to the dataset to be used for prediction
    private Map<String, Object> inputData;      // Input data for the prediction (key-value pairs)
    private String modelName;                   // Optional: Name of the model to be used (if multiple models are available)
    private Map<String, String> additionalParams; // Optional: Additional parameters for prediction (e.g., confidence level)

}
