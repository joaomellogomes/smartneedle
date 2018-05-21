package model;

import java.time.LocalDateTime;

public class Administrador extends Pessoa{

	//Construtores
	public Administrador() {
		this(0, "", "", "", "", "", "", "", "", "", "", "", "", "", null);
	}

	public Administrador(int id, String nome, String email, String telefone, String rg, String cpf, String sexo,
			String login, String senha, String uf, String endereco, String cidade, String bairro, String cep,
			LocalDateTime dataNascimento) {
		
		super(id, nome, email, telefone, rg, cpf, sexo, login, senha, uf, endereco, cidade, bairro, cep, dataNascimento);
		
	}

}
