package br.com.wiri.ecommerce_async_api.services;

import br.com.wiri.ecommerce_async_api.domain.enums.StatusPedido;
import br.com.wiri.ecommerce_async_api.domain.models.Pedido;
import br.com.wiri.ecommerce_async_api.domain.models.Produto;
import br.com.wiri.ecommerce_async_api.repositories.PedidoRepository;
import br.com.wiri.ecommerce_async_api.repositories.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProcessamentoPedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProcessamentoPedidoService processamentoPedidoService;

    @Test
    public void aprovarPedidoQuandoTiverEstoque() {

        UUID pedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();

        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setId(pedidoId);
        pedidoTeste.setProdutoId(produtoId);
        pedidoTeste.setQuantidadeComprada(2);

        Produto produtoTeste = new Produto();
        produtoTeste.setId(produtoId);
        produtoTeste.setQuantidadeEstoque(5);

        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoTeste));
        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoTeste));

        processamentoPedidoService.executar(pedidoId);

        Assertions.assertEquals(StatusPedido.APROVADO, pedidoTeste.getStatus());
        Assertions.assertEquals(3, produtoTeste.getQuantidadeEstoque());

        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedidoTeste);
        Mockito.verify(produtoRepository, Mockito.times(1)).save(produtoTeste);
    }

    @Test
    public void rejeitarPedidoQuandoEstoqueForInsuficiente() {

        UUID pedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();

        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setId(pedidoId);
        pedidoTeste.setProdutoId(produtoId);
        pedidoTeste.setQuantidadeComprada(10);

        Produto produtoTeste = new Produto();
        produtoTeste.setId(produtoId);
        produtoTeste.setQuantidadeEstoque(2);

        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoTeste));
        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoTeste));

        processamentoPedidoService.executar(pedidoId);

        Assertions.assertEquals(StatusPedido.REJEITADO, pedidoTeste.getStatus());
        Assertions.assertEquals(2, produtoTeste.getQuantidadeEstoque());

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(pedidoId);
    }
}