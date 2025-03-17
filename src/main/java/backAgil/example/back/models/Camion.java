package backAgil.example.back.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camions")
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Camion_ID")
    private Long id;
    private String marque;
    private String modele;
    private String immatriculation;
    private int kilometrage;
    private String statut;
    @OneToMany(mappedBy = "camion") // Assurez-vous que "camion" existe bien dans Livraison
    private List<Livraison> livraisons;

    public Camion() {

    }

    public Camion(String marque, String modele, String immatriculation, int kilometrage, String statut, List<Livraison> livraisons) {
        this.marque = marque;
        this.modele = modele;
        this.immatriculation = immatriculation;
        this.kilometrage = kilometrage;
        this.statut = statut;
        this.livraisons = livraisons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", immatriculation='" + immatriculation + '\'' +
                ", kilometrage=" + kilometrage +
                ", statut='" + statut + '\'' +
                ", livraisons=" + livraisons +
                '}';
    }
}
