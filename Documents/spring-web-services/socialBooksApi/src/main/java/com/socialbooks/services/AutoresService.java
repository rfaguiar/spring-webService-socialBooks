package com.socialbooks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialbooks.domain.Autor;
import com.socialbooks.repository.AutoresRepository;
import com.socialbooks.services.exceptions.AutorExistenteException;
import com.socialbooks.services.exceptions.AutorNaoEncontradoException;

@Service
public class AutoresService {

	@Autowired
	private AutoresRepository autoresRepository;
	
	public List<Autor> listar(){
		return this.autoresRepository.findAll();
	}
	
	public Autor salvar(Autor autor) throws AutorExistenteException{
		if(autor.getId() != null){
			Autor a = this.autoresRepository.findOne(autor.getId());
			
			if(a != null){
				throw new AutorExistenteException("O autor já existe");
			}
		}
		
		return this.autoresRepository.save(autor);
	}
	
	public Autor buscar(Long id) throws AutorNaoEncontradoException{
		Autor autor = this.autoresRepository.findOne(id);
		if(autor == null){
			throw new AutorNaoEncontradoException("Autor não pode ser encontrado");
		}
		return autor;
	}
}
