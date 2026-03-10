package br.com.wiri.ecommerce_async_api.controller;

import br.com.wiri.ecommerce_async_api.dto.PedidoRequestDTO;
import br.com.wiri.ecommerce_async_api.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<String> salvarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        String mensagemResposta = pedidoService.criarPedido(pedidoRequestDTO);
        return ResponseEntity.accepted().body(mensagemResposta);
    }
}
