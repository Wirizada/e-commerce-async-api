package br.com.wiri.ecommerce_async_api.repositories;

import br.com.wiri.ecommerce_async_api.entities.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
