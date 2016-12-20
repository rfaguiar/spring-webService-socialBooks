package com.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialbooks.domain.Comentario;
import com.socialbooks.domain.Livro;
import com.socialbooks.repository.ComentariosRepository;
import com.socialbooks.repository.LivrosRepository;
import com.socialbooks.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
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
	
	public Comentario salvarComentario(Long idLivro, Comentario comentario){
		Livro livro = buscar(idLivro);
		
		comentario.setLivro(livro);
		comentario.setData(new Date());
		return this.comentariosRepository.save(comentario);
	}

	public List<Comentario> listarComentarios(Long livroid) throws LivroNaoEncontradoException{
		Livro livro = buscar(livroid);				
		return livro.getComentarios();
	}
}
