package com.natanconstrutora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cargo;
    private BigDecimal salario;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
} 