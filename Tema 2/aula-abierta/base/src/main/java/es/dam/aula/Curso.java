package es.dam.aula;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Curso {

  @Id
  private String id;

  private String titulo;
  private String nivel;
  private int horas;

  @ManyToOne
  @JoinColumn
  Area area;

  @ManyToOne
  @JoinColumn
  Leccion leccion;
}
