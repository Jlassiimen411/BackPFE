package backAgil.example.back.controllers;

import backAgil.example.back.models.Compartiment;
import backAgil.example.back.services.CompartimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/compartiments")
public class CompartimentController {

    @Autowired
    private CompartimentService compartimentService;

    @GetMapping
    public List<Compartiment> getAllCompartiments() {
        return compartimentService.getAllCompartiments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compartiment> getCompartimentById(@PathVariable Long id) {
        Optional<Compartiment> compartiment = compartimentService.getCompartimentById(id);
        return compartiment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/citerne/{citerneId}")
    public List<Compartiment> getCompartimentsByCiterneId(@PathVariable Long citerneId) {
        return compartimentService.getCompartimentsByCiterneId(citerneId);
    }


    @PostMapping
    public ResponseEntity<?> addCompartiment(@RequestBody Compartiment compartiment) {
        try {
            Compartiment savedCompartiment = compartimentService.addCompartiment(compartiment);
            return ResponseEntity.ok(savedCompartiment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compartiment> updateCompartiment(@PathVariable Long id, @RequestBody Compartiment newCompartiment) {
        Compartiment updatedCompartiment = compartimentService.updateCompartiment(id, newCompartiment);
        return updatedCompartiment != null ? ResponseEntity.ok(updatedCompartiment) : ResponseEntity.notFound().build();
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