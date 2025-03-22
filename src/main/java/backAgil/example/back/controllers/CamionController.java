package backAgil.example.back.controllers;

import backAgil.example.back.models.Camion;
import backAgil.example.back.services.CamionService;
import backAgil.example.back.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/camions")
@CrossOrigin("*")
@RestController
public class CamionController {
    @Autowired
    private CamionService camionService;

    @PostMapping
    public ResponseEntity<Camion> addCamion(@RequestBody Camion camion) {
        return ResponseEntity.ok(camionService.addCamion(camion));
    }

    // Obtenir tous les camions
    @GetMapping
    public List<Camion> getAllCamions() {
        return camionService.getAllCamions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camion> getCamionById(@PathVariable Long id) {
        Optional<Camion> camion = camionService.getCamionById(id);
        return camion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camion> updateCamion(@PathVariable Long id, @RequestBody Camion camionDetails) {
        return ResponseEntity.ok(camionService.updateCamion(id, camionDetails));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamion(@PathVariable Long id) {
        camionService.deleteCamion(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/marques/{marque}")
    public List<Camion> getCamionsByMarque(@PathVariable String marque) {
        return camionService.getCamionsByMarque(marque);
    }

}
