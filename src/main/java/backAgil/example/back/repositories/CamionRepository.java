package backAgil.example.back.repositories;

import backAgil.example.back.models.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository<Camion, Long> {
}
