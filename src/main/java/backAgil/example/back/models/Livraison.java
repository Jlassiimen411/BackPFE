package backAgil.example.back.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Livraisons")
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String codeLivraison;
    @ManyToOne
    @JoinColumn(name = "camion_id", referencedColumnName = "Camion_ID") // Correspond au mappedBy de Camion
    private Camion camion;
    @ManyToMany
    @JoinTable(
            name = "livraison_commandes",
            joinColumns = @JoinColumn(name = "livraison_id"),
            inverseJoinColumns = @JoinColumn(name = "commande_id")
    )
    private List<Commande> commandes;

    public String getCodeLivraison() {
        return codeLivraison;
    }

    public void setCodeLivraison(String codeLivraison) {
        this.codeLivraison = codeLivraison;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @Temporal(TemporalType.DATE)
    private Date dateLivraison;

    @Enumerated(EnumType.STRING)
    private StatutLivraison statut;

    public enum StatutLivraison {
        EN_ATTENTE,
        LIVRE,
        ANNULE
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public StatutLivraison getStatut() {
        return statut;
    }

    public void setStatut(StatutLivraison statut) {
        this.statut = statut;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", codeLivraison='" + codeLivraison + '\'' +
                ", camion=" + camion +
                ", commandes=" + commandes +
                ", dateLivraison=" + dateLivraison +
                ", statut=" + statut +
                '}';
    }
}