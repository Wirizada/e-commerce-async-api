package br.com.wiri.ecommerce_async_api.config;

import br.com.wiri.ecommerce_async_api.domain.models.Produto;
import br.com.wiri.ecommerce_async_api.repositories.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.UUID;

@Configuration
public class DataSeeder {

    public static final UUID PRODUTO_TESTE_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Bean
    CommandLineRunner initDatabase(ProdutoRepository produtoRepository) {
        return args -> {
            produtoRepository.deleteAll();

            Produto produto = new Produto();

            produto.setNome("Teclado Mecânico");
            produto.setCategoria("Periféricos de Informática");
            produto.setPreco(new BigDecimal("250.00"));
            produto.setQuantidadeEstoque(5);

            produtoRepository.save(produto);
            System.out.println(produto.getId());
            System.out.println("[DATA SEEDER] Produto de teste criado!");
        };
    }
}
