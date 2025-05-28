package co.edu.ucentral.UiPersonalization.service;

import co.edu.ucentral.UiPersonalization.model.ConfiguracionInterfaz;
import co.edu.ucentral.UiPersonalization.repositories.ConfiguracionInterfazRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UiPersonalizationService {

    private final ConfiguracionInterfazRepository configRepository;

    public UiPersonalizationService(ConfiguracionInterfazRepository configRepository) {
        this.configRepository = configRepository;
    }

    /**
     * Obtener configuración actual de la interfaz para un usuario.
     */
    public Optional<ConfiguracionInterfaz> getUiPreferences(Long userId) {
        return configRepository.findByUsuarioId(userId);
    }

    /**
     * Modificar/Guardar preferencias de la interfaz.
     * This method handles both updating existing preferences and creating new ones.
     */
    public ConfiguracionInterfaz saveOrUpdateUiPreferences(Long userId, ConfiguracionInterfaz newPreferences) {
        return configRepository.findByUsuarioId(userId)
                .map(existingConfig -> {
                    // Update fields only if they are provided in the newPreferences
                    if (newPreferences.getTemaVisual() != null) {
                        existingConfig.setTemaVisual(newPreferences.getTemaVisual());
                    }
                    if (newPreferences.getTamanoFuente() != null) {
                        existingConfig.setTamanoFuente(newPreferences.getTamanoFuente());
                    }
                    if (newPreferences.getOrdenElementos() != null) {
                        existingConfig.setOrdenElementos(newPreferences.getOrdenElementos());
                    }
                    existingConfig.setConfiguracionGuardada(true); // Mark as saved
                    existingConfig.setFechaConfiguracion(LocalDate.now());
                    return configRepository.save(existingConfig);
                })
                .orElseGet(() -> {
                    // If no existing configuration, create a new one
                    newPreferences.setUsuarioId(userId);
                    newPreferences.setConfiguracionGuardada(true); // Default to saved
                    newPreferences.setFechaConfiguracion(LocalDate.now());
                    return configRepository.save(newPreferences);
                });
    }

    /**
     * Restaurar configuración predeterminada para un usuario.
     * This effectively deletes the custom configuration, making a new one effectively "default".
     * Or you could set predefined default values. For now, we'll delete.
     */
    public boolean restoreDefaultPreferences(Long userId) {
        Optional<ConfiguracionInterfaz> config = configRepository.findByUsuarioId(userId);
        if (config.isPresent()) {
            configRepository.delete(config.get());
            return true;
        }
        return false; // No custom config to restore
    }
}