package co.edu.ucentral.UiPersonalization.repositories;

import co.edu.ucentral.UiPersonalization.model.ConfiguracionInterfaz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionInterfazRepository extends JpaRepository<ConfiguracionInterfaz, Long> {
    // Custom method to find configuration by userId
    Optional<ConfiguracionInterfaz> findByUsuarioId(Long usuarioId);
}
