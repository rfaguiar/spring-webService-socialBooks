package com.socialbooks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialbooks.domain.Livro;
import com.socialbooks.repository.LivrosRepository;
import com.socialbooks.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;
	
	public List<Livro> listar(){
		return this.livrosRepository.findAll();
	}
	
	public Livro buscar(Long id) throws LivroNaoEncontradoException{
		Livro livro = this.livrosRepository.findOne(id);
		
		if(livro == null){
			throw new LivroNaoEncontradoException("O livro não pode ser encontrado");
		}
		return livro;
	}

	public Livro salvar(Livro livro) {
		livro.setId(null);
		return this.livrosRepository.save(livro);
	}

	public void deletar(Long id) {		
		try{
			this.livrosRepository.delete(id);
		}catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro não pode ser encontrado");
		}
	}

	public void atualizar(Livro livro) throws LivroNaoEncontradoException {
		this.verificarExistencia(livro);
		this.livrosRepository.save(livro);		
	}
	
	private void verificarExistencia(Livro livro) throws LivroNaoEncontradoException{
		buscar(livro.getId());
	}
}
