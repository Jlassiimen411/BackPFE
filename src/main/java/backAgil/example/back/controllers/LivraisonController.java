package backAgil.example.back.controllers;

import backAgil.example.back.models.Livraison;
import backAgil.example.back.repositories.LivraisonRepository;
import backAgil.example.back.services.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;
    @Autowired
    private LivraisonRepository livraisonRepository;

    @PostMapping
    public ResponseEntity<Livraison> addLivraison(@RequestBody Livraison livraison) {

        Livraison newLivraison = livraisonService.addLivraison(livraison);
        return ResponseEntity.ok(newLivraison);
    }

    @GetMapping
    public List<Livraison> getAllLivraisons() {
        return livraisonService.getAllLivraisons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable("id") Long id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        if (livraison != null) {
            return ResponseEntity.ok(livraison);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Livraison> updateLivraison(
            @PathVariable Long id,
            @RequestBody Livraison livraison) {
        // Vérifier si la livraison avec cet ID existe
        Optional<Livraison> existingLivraison = livraisonRepository.findById(id);
        if (existingLivraison.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Peut causer l'erreur 400
        }

        // Mise à jour de la livraison
        Livraison updatedLivraison = existingLivraison.get();
        updatedLivraison.setDateLivraison(livraison.getDateLivraison());
        updatedLivraison.setStatut(livraison.getStatut());

        livraisonRepository.save(updatedLivraison);
        return ResponseEntity.ok(updatedLivraison);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable("id") Long id) {
        try {
            livraisonService.deleteLivraison(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}