package backAgil.example.back.servicesImpl;

import backAgil.example.back.models.Camion;
import backAgil.example.back.repositories.CamionRepository;
import backAgil.example.back.services.CamionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamionServiceImpl implements CamionService {
    @Autowired
    private CamionRepository cRep;

    // Ajouter un camion
    public Camion addCamion(Camion camion) {
        return cRep.save(camion);
    }
    public List<Camion> getAllCamions() {
        return cRep.findAll();
    }
    public Optional<Camion> getCamionById(Long id) {
        return cRep.findById(id);
    }
    public Camion updateCamion(Long id, Camion camionDetails) {
        return cRep.findById(id).map(camion -> {
            camion.setMarque(camionDetails.getMarque());
            camion.setModele(camionDetails.getModele());
            camion.setImmatriculation(camionDetails.getImmatriculation());
            camion.setKilometrage(camionDetails.getKilometrage());
            camion.setStatut(    camionDetails.getStatut());
            return cRep.save(camion);
        }).orElseThrow(() -> new RuntimeException("Camion non trouvé avec l'ID : " + id));
    }
    public void deleteCamion(Long id) {
        cRep.deleteById(id);
    }
    // Ajoutez cette méthode dans le service CamionServiceImpl

    public List<Camion> getCamionsByMarque(String marque) {
        return cRep.findByMarque(marque);  // Requête personnalisée selon la marque
    }


}
