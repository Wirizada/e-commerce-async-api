package br.com.wiri.ecommerce_async_api.services;

import br.com.wiri.ecommerce_async_api.config.RabbitMQConfig;
import br.com.wiri.ecommerce_async_api.dto.PedidoRequestDTO;
import br.com.wiri.ecommerce_async_api.domain.enums.StatusPedido;
import br.com.wiri.ecommerce_async_api.domain.models.Pedido;
import br.com.wiri.ecommerce_async_api.repositories.PedidoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RabbitTemplate rabbitTemplate;

    public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String criarPedido(PedidoRequestDTO pedidoRequestDTO) {

        Pedido novoPedido = new Pedido();
        novoPedido.setProdutoId(pedidoRequestDTO.produtoId());
        novoPedido.setQuantidadeComprada(pedidoRequestDTO.quantidade());
        novoPedido.setStatus(StatusPedido.PROCESSANDO);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_PEDIDOS, pedidoSalvo.getId());

        System.out.println("Pedido " + pedidoSalvo.getId() + " salvo e enviado para fila!");

        return "Pedido recebido com sucesso! ID: " + pedidoSalvo.getId();
    }
}
