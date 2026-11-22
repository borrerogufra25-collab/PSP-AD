package es.dam.aula;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Area {


  @Id
  private String id;

  private String nombre;

  @OneToMany(mappedBy = "area_id")
  Set<Curso> cursos;
}
