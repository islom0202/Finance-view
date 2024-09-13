package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptiveStats {

    // Getters
    private final double sum;
    private final double mean;
    private final double max;
    private final double min;
    private final double standardDeviation;

    // Constructor
    public DescriptiveStats(double sum, double mean, double max, double min, double standardDeviation) {
        this.sum = sum;
        this.mean = mean;
        this.max = max;
        this.min = min;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public String toString() {
        return "DescriptiveStats{" +
                "sum=" + sum +
                ", mean=" + mean +
                ", max=" + max +
                ", min=" + min +
                ", standardDeviation=" + standardDeviation +
                '}';
    }
}
