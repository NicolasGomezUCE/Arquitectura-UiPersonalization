package co.edu.ucentral.UiPersonalization.controller; // Adjust package name

import co.edu.ucentral.UiPersonalization.model.ConfiguracionInterfaz;
import co.edu.ucentral.UiPersonalization.service.UiPersonalizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ui") // Base path for this microservice
public class UiPersonalizationController {

    private final UiPersonalizationService uiService;

    public UiPersonalizationController(UiPersonalizationService uiService) {
        this.uiService = uiService;
    }

    // GET /ui/preferences/{userId}: Obtener configuración actual.
    @GetMapping("/preferences/{userId}")
    public ResponseEntity<ConfiguracionInterfaz> getPreferences(@PathVariable Long userId) {
        return uiService.getUiPreferences(userId)
                .map(config -> new ResponseEntity<>(config, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Or return a default empty config
    }

    // PUT /ui/preferences/{userId}: Modificar preferencias (can also create if not exists).
    // Also handles the POST /ui/preferences/{userId} (Guardar nuevas configuraciones) use case,
    // as PUT is idempotent and suitable for both creation/update for a single user's config.
    @PutMapping("/preferences/{userId}")
    public ResponseEntity<ConfiguracionInterfaz> updatePreferences(@PathVariable Long userId, @RequestBody ConfiguracionInterfaz preferences) {
        ConfiguracionInterfaz updatedConfig = uiService.saveOrUpdateUiPreferences(userId, preferences);
        return new ResponseEntity<>(updatedConfig, HttpStatus.OK);
    }

    // DELETE /ui/preferences/{userId}: Restaurar configuración predeterminada.
    @DeleteMapping("/preferences/{userId}")
    public ResponseEntity<Void> restoreDefaultPreferences(@PathVariable Long userId) {
        boolean restored = uiService.restoreDefaultPreferences(userId);
        if (restored) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted custom config
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No custom config to delete
        }
    }
}