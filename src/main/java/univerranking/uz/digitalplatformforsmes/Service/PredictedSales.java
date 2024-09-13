package univerranking.uz.digitalplatformforsmes.Service;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictedSales {
    private String date;
    private double value;

    // Default constructor
    public PredictedSales() {}

    public PredictedSales(String date, double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return "PredictedSales{" +
                "date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}
