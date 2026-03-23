package br.com.wiri.ecommerce_async_api.exception;


import java.time.LocalDateTime;

public class ErroPadrao {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;

    public ErroPadrao(LocalDateTime timestamp, Integer status, String erro, String mensagem) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
    }

    public ErroPadrao() {}

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
