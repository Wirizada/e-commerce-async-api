package br.com.wiri.ecommerce_async_api.dto;

import java.util.UUID;

public record PedidoRequestDTO(UUID ProdutoId, Integer Quantidade) {
}
