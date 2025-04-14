package backAgil.example.back.controllers;

import backAgil.example.back.models.Commande;
import backAgil.example.back.models.Produit;
import backAgil.example.back.repositories.CommandeRepository;
import backAgil.example.back.services.CommandeService;
import backAgil.example.back.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/commandes/v1")
@CrossOrigin("*")
public class CommandeController {

    @Autowired
    private CommandeService cService;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private CommandeRepository commandeRepository;

    // GET all commandes
    @GetMapping
    public List<Commande> getAll() {
        List<Commande> commandes = cService.getAllCommandes();
        commandes.forEach(c -> System.out.println("Commande ID: " + c.getId() + ", Price: " + c.getPrice()));
        return commandes;
    }

    // GET commande by ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable("id") Long id) {
        Commande commande = cService.getCommandeById(id);
        return commande != null ? ResponseEntity.ok(commande) : ResponseEntity.notFound().build();
    }

    // CHECK if codeCommande exists
    @GetMapping("/check-code")
    public ResponseEntity<Map<String, Boolean>> checkCodeCommande(@RequestParam String codeCommande) {
        boolean exists = commandeRepository.existsByCodeCommande(codeCommande);
        return ResponseEntity.ok(Map.of("exists", exists));
    }



    // ADD commande
    @PostMapping
    public ResponseEntity<?> addCommande(@RequestBody Commande commande) {
        try {
            if (commande.getCodeCommande() == null || commande.getCodeCommande().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ codeCommande est obligatoire.");
            }

            if (commandeRepository.existsByCodeCommande(commande.getCodeCommande())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce codeCommande existe déjà.");
            }

            System.out.println("🚀 Commande reçue: " + commande);
            Commande savedCommande = cService.addCommande(commande);
            System.out.println("✅ Commande sauvegardée: " + savedCommande);
            return ResponseEntity.ok(savedCommande);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur : " + e.getMessage());
        }
    }

    // EDIT commande
    @PutMapping("/{id}")
    public ResponseEntity<?> editCommande(@PathVariable("id") Long id, @RequestBody Commande c) {
        try {
            c.setId(id);

            if (c.getProduits() != null) {
                for (Produit produit : c.getProduits()) {
                    Produit existingProduit = produitService.getProduitById(produit.getId());
                    if (existingProduit != null) {
                        produit.setNomProduit(existingProduit.getNomProduit());
                        produit.setDescription(existingProduit.getDescription());
                        produit.setPrix(existingProduit.getPrix());
                        produit.setDate(existingProduit.getDate());
                    }
                }
            }

            Commande updatedCommande = cService.editCommande(c);
            return ResponseEntity.ok(updatedCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur serveur : " + e.getMessage());
        }
    }

    // DELETE commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable("id") Long id) {
        cService.deleteCommandeById(id);
        return ResponseEntity.noContent().build();
    }
}
