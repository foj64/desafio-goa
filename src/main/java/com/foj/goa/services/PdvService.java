package com.foj.goa.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.foj.goa.domain.Pdv;
import com.foj.goa.repositories.PdvRepository;

@Service
public class PdvService {
	
	@Autowired
	private PdvRepository repo;
	
	private static final String VIRGULA = ",";
	
	public Optional<Pdv> findById(Integer id) {
		Optional<Pdv> obj = repo.findById(id);
		return obj;
	}
	
	public Pdv findByNome(String nome) {
		Pdv obj = repo.findByNome(nome);
		return obj;
	}
	
	@Transactional
	public Pdv insert(Pdv obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		}
		catch (RuntimeException e) {
			throw new RuntimeException("Não é possível excluir!");
		}
	}
	
	public List<Pdv> findAll() {
		return repo.findAll();
	}
	

	public Page<Pdv> search(String nome, String cidade, String cep, 
							Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByNomeContainingAndCidadeContainingAndCepContaining(nome, cidade, cep, pageRequest);	
	}
	
	public void processa(MultipartFile uploadedFile) {
		try {
        
        InputStream is = uploadedFile.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        String linha = null;
        //nome cidade endereco cep
        while ((linha = reader.readLine()) != null) {
        	String[] dados = linha.split(VIRGULA);
        	if (dados.length > 3) {
        		Pdv pdv = new Pdv(null, limparNomeCidade(dados[0]), 
        								limparNomeCidade(dados[1]), 
        								limpar(dados[2]), 
        								limparCep(dados[3]));
        		insert(pdv);
        	}
        }
        reader.close();
		} catch (Exception e) {
			System.out.print("Ocorreu um erro: " + e.getMessage() + e.toString());
		}
    }
	
	public String limpar(String str) {
		return str.replace("\"", "").trim();
	}
	
	public String limparNomeCidade(String str) {
		if (str.equals("") || str == null) {
			str = "NOME DESCONHECIDO";
		}
		return str.replace("\"", "").trim();
	}
	
	public String limparCep(String str) {
		if (str.length() != 8 || str == null) {
			str = "00000000";
		}
		return str.replace("\"", "").trim();
	}

}
