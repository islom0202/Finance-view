package univerranking.uz.digitalplatformforsmes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class PrescriptiveAction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actionName;
    private String reason;
    private LocalDateTime createdAt;

    public PrescriptiveAction(String increaseInventory, String s, LocalDateTime createdAt) {
        this.actionName = increaseInventory;
        this.reason = s;
        this.createdAt = createdAt;
    }

    public PrescriptiveAction() {

    }

    // Constructors, getters, and setters
}


