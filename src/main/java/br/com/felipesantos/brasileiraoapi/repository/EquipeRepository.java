package br.com.felipesantos.brasileiraoapi.repository;

import br.com.felipesantos.brasileiraoapi.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    Optional<Equipe> findByNomeEquipe(String nomeEquipe);

    Boolean existsByNomeEquipe(String nomeEquipe);
}
