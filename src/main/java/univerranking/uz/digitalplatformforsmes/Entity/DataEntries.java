package univerranking.uz.digitalplatformforsmes.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class DataEntries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double value;
    private LocalDateTime recordedAt;
    @ManyToOne
    private Departments department;
    @ManyToOne
    private DataSource dataSource;
    @ManyToOne
    private Metrics metrics;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "DataEntries{" +
                "id=" + id +
                ", value=" + value +
                ", recordedAt=" + recordedAt +
                ", department=" + department +
                ", dataSource=" + dataSource +
                ", metrics=" + metrics +
                ", createdAt=" + createdAt +
                '}';
    }
}
