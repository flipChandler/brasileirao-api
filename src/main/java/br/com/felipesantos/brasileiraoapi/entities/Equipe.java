package br.com.felipesantos.brasileiraoapi.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipe_id")
    private Long id;

    @Column(name = "nome_equipe")
    private String nomeEquipe;

    @Column(name = "url_logo_equipe")
    private String urlLogoEquipe;
}
