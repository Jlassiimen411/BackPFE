package backAgil.example.back.services;

import backAgil.example.back.models.Livraison;

import java.util.List;

public interface LivraisonService {

    Livraison addLivraison(Livraison livraison);

    List<Livraison> getAllLivraisons();

    Livraison getLivraisonById(Long id);

    Livraison updateLivraison(Livraison livraison);  // Si la livraison est mise à jour en entier, sinon updateLivraisonDetails

    void deleteLivraison(Long id);

}