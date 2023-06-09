package br.unibh.sdm.appnobald.entidades;

public class Servico {

    private String id;
    private String barbeiroId;
    private String clienteId;
    private String descricao;
    private Double preco;

    public Servico() {
    }

    public Servico(String barbeiroId, String clienteId, String descricao, Double preco) {
        // this.id = id;
        this.barbeiroId = barbeiroId;
        this.clienteId = clienteId;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(String barbeiroId) {
        this.barbeiroId = barbeiroId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    @Override
    public String toString() {
        return "Servico [barbeiroId=" + barbeiroId + ", clienteId=" + clienteId + ", descricao=" + descricao + ", preco="
                + preco + ", servicoId=" + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((barbeiroId == null) ? 0 : barbeiroId.hashCode());
        result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((preco == null) ? 0 : preco.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Servico other = (Servico) obj;
        if (barbeiroId == null) {
            if (other.barbeiroId != null)
                return false;
        } else if (!barbeiroId.equals(other.barbeiroId))
            return false;
        if (clienteId == null) {
            if (other.clienteId != null)
                return false;
        } else if (!clienteId.equals(other.clienteId))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (preco == null) {
            if (other.preco != null)
                return false;
        } else if (!preco.equals(other.preco))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
