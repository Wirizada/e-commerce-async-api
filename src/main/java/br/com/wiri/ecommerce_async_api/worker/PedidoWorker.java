package br.com.wiri.ecommerce_async_api.worker;

import br.com.wiri.ecommerce_async_api.config.RabbitMQConfig;
import br.com.wiri.ecommerce_async_api.services.ProcessamentoPedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PedidoWorker {

    private final ProcessamentoPedidoService processamentoPedidoService;

    public PedidoWorker(ProcessamentoPedidoService processamentoPedidoService) {
        this.processamentoPedidoService = processamentoPedidoService;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_PEDIDOS)
    public void processarPedido(UUID pedidoId) {
        System.out.println("Recebendo pedido para processamento: " + pedidoId);

        processamentoPedidoService.executar(pedidoId);
    }
}
