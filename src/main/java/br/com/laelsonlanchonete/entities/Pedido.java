package br.com.laelsonlanchonete.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import br.com.laelsonlanchonete.enums.TipoEntraga;

public class Pedido extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @OneToMany
    public List<ItemCardapio> itensPedido;
    public Cliente cliente;
    @Enumerated(EnumType.STRING)
    public TipoEntraga tipoEntrega;
    public BigDecimal valorTotal;

}