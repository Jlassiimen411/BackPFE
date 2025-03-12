package backAgil.example.back.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Commande_ID")
    private Long id;

    private int numero;
    private Float quantite;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCommande;

    private Float price;

    @ManyToMany
    @JoinTable(name = "commande_produits",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private List<Produit> produits;




    public Commande() {
    }

    public Commande(int numero, Float quantite, Date dateCommande, Float price, List<Produit> produits) {
        this.numero = numero;
        this.quantite = quantite;
        this.dateCommande = dateCommande;
        this.price = price;
        this.produits = produits;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}