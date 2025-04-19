package backAgil.example.back.repositories;

import backAgil.example.back.models.Commande;
import backAgil.example.back.models.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commandeProduitRepository extends JpaRepository<CommandeProduit, Long> {
}
