package dat3.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter

@MappedSuperclass
public abstract class AdminDetails {
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    protected LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss", shape = JsonFormat.Shape.STRING)
    @UpdateTimestamp
    protected LocalDateTime edited;
}