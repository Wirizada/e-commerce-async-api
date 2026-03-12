package br.com.wiri.ecommerce_async_api.worker;

import br.com.wiri.ecommerce_async_api.config.RabbitMQConfig;
import br.com.wiri.ecommerce_async_api.domain.enums.StatusPedido;
import br.com.wiri.ecommerce_async_api.domain.models.Pedido;
import br.com.wiri.ecommerce_async_api.domain.models.Produto;
import br.com.wiri.ecommerce_async_api.repositories.PedidoRepository;
import br.com.wiri.ecommerce_async_api.repositories.ProdutoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PedidoWorker {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoWorker(PedidoRepository pedidoRepository,
                        ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_PEDIDOS)
    public void processarPedido(UUID pedidoId) {

        System.out.println("[WORKER] Processando pedido: " + pedidoId);

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();

            Optional<Produto> produtoOptional = produtoRepository.findById(pedido.getProdutoId());

            if (produtoOptional.isPresent()) {
                Produto produto = produtoOptional.get();
                System.out.println("[WORKER] Verificando estoque para o produto: " + produto.getNome());
                System.out.println("[WORKER] Estoque atual: " + produto.getQuantidadeEstoque() + " unidades. Quantidade solicitada: " + pedido.getQuantidadeComprada());

                if (produto.getQuantidadeEstoque() >= pedido.getQuantidadeComprada()) {
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - pedido.getQuantidadeComprada());
                    produtoRepository.save(produto);

                    pedido.setStatus(StatusPedido.APROVADO);
                    System.out.println("[WORKER] Estoque atualizado. Novo estoque: " + produto.getQuantidadeEstoque() + " unidades.");
                } else {
                    pedido.setStatus(StatusPedido.REJEITADO);
                    System.out.println("[WORKER] Estoque insuficiente para o produto: " + produto.getNome());
                }
            } else {
                pedido.setStatus(StatusPedido.REJEITADO);
                System.out.println("[WORKER] Produto não encontrado para o pedido: " + pedidoId);
            }

            pedidoRepository.save(pedido);
        } else {
            System.out.println("[WORKER] Pedido não encontrado: " + pedidoId);
        }
    }
}
