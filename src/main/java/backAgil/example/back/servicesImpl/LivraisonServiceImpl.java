package backAgil.example.back.servicesImpl;

import backAgil.example.back.models.Livraison;
import backAgil.example.back.repositories.LivraisonRepository;
import backAgil.example.back.services.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivraisonServiceImpl implements LivraisonService {

    private final LivraisonRepository livraisonRepository;

    @Autowired
    public LivraisonServiceImpl(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    @Override
    public Livraison addLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    @Override
    public Livraison getLivraisonById(Long id) {
        return livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée avec l'ID : " + id));
    }

    @Override
    public Livraison updateLivraison(Livraison livraison) {
        if (!livraisonRepository.existsById(livraison.getId())) {
            throw new RuntimeException("Impossible de mettre à jour : Livraison non trouvée !");
        }
        return livraisonRepository.save(livraison);
    }

    @Override
    public void deleteLivraison(Long id) {
        if (!livraisonRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Livraison non trouvée !");
        }
        livraisonRepository.deleteById(id);
    }
}
