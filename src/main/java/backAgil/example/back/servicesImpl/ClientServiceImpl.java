package backAgil.example.back.servicesImpl;

import backAgil.example.back.models.Client;
import backAgil.example.back.repositories.ClientRepository;
import backAgil.example.back.services.ClientService;
import backAgil.example.back.services.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID: " + id));
    }

    @Autowired
    private GeocodingService geocodingService;

    public Client createClient(Client client) {
        if (client.getFullName() == null || client.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom complet du client est requis.");
        }

        // Géocoder si lat/lng absents
        if (client.getLatitude() == null || client.getLongitude() == null) {
            double[] coords = geocodingService.getCoordinates(client.getFullAddress());
            client.setLatitude(coords[0]);
            client.setLongitude(coords[1]);
        }

        return clientRepository.save(client);
    }


    @Override
    public List<Client> getClientsByIds(List<Long> ids) {
        return clientRepository.findAllById(ids);
    }
}