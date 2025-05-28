package co.edu.ucentral.UiPersonalization.model;

import jakarta.persistence.*;
import lombok.Data; // Provides @Getter, @Setter, @ToString, @EqualsAndHashCode
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate; // Use LocalDate for DATE type

@Entity
@Table(name = "configuraciones_interfaz")
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
public class ConfiguracionInterfaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId; // Foreign key to Usuarios table

    @Column(name = "tema_visual", length = 50)
    private String temaVisual;

    @Column(name = "tamano_fuente", length = 20)
    private String tamanoFuente;

    @Column(name = "orden_elementos", length = 100)
    private String ordenElementos;

    @Column(name = "configuracion_guardada")
    private Boolean configuracionGuardada;

    @Column(name = "fecha_configuracion")
    private LocalDate fechaConfiguracion;
}