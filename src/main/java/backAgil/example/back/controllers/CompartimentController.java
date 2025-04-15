package backAgil.example.back.controllers;

import backAgil.example.back.models.Citerne;
import backAgil.example.back.models.Compartiment;
import backAgil.example.back.repositories.CiterneRepository;
import backAgil.example.back.repositories.CompartimentRepository;
import backAgil.example.back.services.CiterneService;
import backAgil.example.back.services.CompartimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/compartiments")
public class CompartimentController {

    @Autowired
    private CompartimentService compartimentService;

    @Autowired
    private CiterneRepository citerneRepository;
    @Autowired
    private CompartimentRepository compartimentRepository;

    @Autowired
    private CiterneService citerneService;

    @GetMapping
    public List<Compartiment> getAllCompartiments() {
        return compartimentService.getAllCompartiments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compartiment> getCompartimentById(@PathVariable Long id) {
        Optional<Compartiment> compartiment = compartimentService.getCompartimentById(id);
        return compartiment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Récupérer les compartiments par ID de citerne
    @GetMapping("/by-id/{citerneId}")
    public ResponseEntity<List<Compartiment>> getCompartimentsByCiterneId(@PathVariable Long citerneId) {
        try {
            List<Compartiment> compartiments = compartimentService.getCompartimentsByCiterneId(citerneId);
            return ResponseEntity.ok(compartiments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ✅ Récupérer les compartiments par référence de citerne
    @GetMapping("/by-reference/{reference}")
    public ResponseEntity<?> getCompartimentsByCiterneReference(@PathVariable String reference) {
        try {
            Citerne citerne = citerneRepository.findByReference(reference)
                    .orElseThrow(() -> new RuntimeException("Citerne avec cette référence non trouvée"));

            List<Compartiment> compartiments = compartimentRepository.findByCiterneId(citerne.getId());

            if (compartiments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aucun compartiment trouvé pour cette citerne");
            }

            List<Compartiment> compartimentsInfo = compartiments.stream()
                    .map(compartiment -> new Compartiment(compartiment.getReference(),
                            compartiment.getCapaciteMax(),
                            compartiment.getStatut()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(compartimentsInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Compartiment> createCompartiment(@RequestBody Compartiment compartiment) {
        try {
            Compartiment createdCompartiment = compartimentService.addCompartiment(compartiment);
            return ResponseEntity.ok(createdCompartiment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ✅ Ajouter un compartiment à une citerne via son ID
    @PostMapping("/citerne/{id}/add")
    public ResponseEntity<Compartiment> addCompartimentToCiterne(
            @PathVariable Long id,
            @RequestBody Compartiment compartiment
    ) {
        Compartiment result = citerneService.addCompartimentToCiterne(id, compartiment);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compartiment> updateCompartiment(@PathVariable Long id, @RequestBody Compartiment compartiment) {
        try {
            Compartiment updatedCompartiment = compartimentService.updateCompartiment(id, compartiment);
            return ResponseEntity.ok(updatedCompartiment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompartiment(@PathVariable Long id) {
        try {
            compartimentService.deleteCompartiment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
