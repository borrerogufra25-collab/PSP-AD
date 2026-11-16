package com.ejemplo.repositorio;

import com.ejemplo.modelo.*;
import com.ejemplo.dto.ResumenPedidoDTO;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  /*
   * Obtener los clientes con pedidos PENDIENTES superior a X €.
   * Debe aparece los clientes, su direccion, los pedidos,
   * las líneas de pedido, los productos y su categoria.
   *
   * Se puede usar JPQL o derivadas, y hay que usar grafos de entidad con subgrafo
   * */

  @EntityGraph(value = "Cliente.pedidosPendientes")
  @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p WHERE p.estado = 'PENDIENTE' AND p.total > :importe")
  List<Cliente> findClientesConPedidosPendientesSuperioresA(
      @Param("importe") BigDecimal importe);


  @EntityGraph(attributePaths = {"direccion"})
  Optional<Cliente> findWithDireccionByEmail(String email);

  Optional<Cliente> findByEmailAndActivoTrue(String email);

  @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.activo = true")
  Optional<Cliente> buscarPorEmailActivo(@Param("email") String email);

  @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %?1% AND c.activo = true")
  List<Cliente> buscarActivosPorNombreParcial(String parteNombre);

  @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.activo = true")
  Optional<Cliente> buscarClienteActivoPorEmail(@Param("email") String email);

  @Query("SELECT c FROM Cliente c WHERE EXISTS (" +
      "  SELECT p FROM Pedido p WHERE p.cliente = c AND p.total > :montoMinimo" +
      ")")
  List<Cliente> buscarClientesConPedidosMayoresA(@Param("montoMinimo") BigDecimal montoMinimo);
}
