package br.com.wiri.ecommerce_async_api.services;

import br.com.wiri.ecommerce_async_api.domain.enums.StatusPedido;
import br.com.wiri.ecommerce_async_api.domain.models.Pedido;
import br.com.wiri.ecommerce_async_api.domain.models.Produto;
import br.com.wiri.ecommerce_async_api.repositories.PedidoRepository;
import br.com.wiri.ecommerce_async_api.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessamentoPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ProcessamentoPedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public void executar(UUID pedidoId) {

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            Optional<Produto> produtoOptional = produtoRepository.findById(pedido.getProdutoId());

            if (produtoOptional.isPresent()) {

                Produto produto = produtoOptional.get();
                System.out.println("Processando pedido: " + pedido.getId());

                if (produto.getQuantidadeEstoque() >= pedido.getQuantidadeComprada()){

                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - pedido.getQuantidadeComprada());
                    produtoRepository.save(produto);

                    pedido.setStatus(StatusPedido.APROVADO);
                    System.out.println("Pedido aprovado: " + pedido.getId());
                } else {
                    pedido.setStatus(StatusPedido.REJEITADO);
                    System.out.println("Pedido rejeitado por falta de estoque: " + pedido.getId());
                }
            } else {
                pedido.setStatus(StatusPedido.REJEITADO);
                System.out.println("Pedido rejeitado por produto não encontrado: " + pedido.getId());
            }
            pedidoRepository.save(pedido);
        }
    }
}
