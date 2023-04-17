package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

//Classe que conecta a interface do usuário com o sistema de ações dela

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository; // variavel que contem todos os atributos da model
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll()); //SELECT * FROM tb_posagens
	}
	
	@GetMapping("/{id}")// entre chaves: parametro é uma variavel
	public ResponseEntity<Postagem> getAll(@PathVariable Long id){
		return postagemRepository.findById(id) //Selecionar atributos pelo id: SELECT * FROM tb_posagens WHERE id = id;
		
				.map(resposta -> ResponseEntity.ok(resposta)) //findById em resposta, se existir, mostra a resposta
		
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //findById em resposta, se null, resposta por build
	
	}
	
	@GetMapping("/titulo/{titulo}")// entre chaves: parametro é uma variavel
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){//Popular tabela
		return ResponseEntity.status(HttpStatus.CREATED)//Resposta para um uso especifico do CORPO da requisição
				.body(postagemRepository.save(postagem));
		//INSERT INTO tb_postagens (data, ttulo, texto);
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){//Popular tabela 
		//UPTADE tb_postagens SET titulo = ?, texto = ?, data = ? WHERE id = id;
		
		return postagemRepository.findById(postagem.getId())//Resposta para um uso especifico do CORPO da requisição
		
		.map(resposta -> ResponseEntity.status(HttpStatus.OK) //findById em resposta, se existir, mostra a resposta
		.body(postagemRepository.save(postagem)))
		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)//Resposta para sem conteudo
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
		//DELETE * FROM tb_postagens WHERE id = id;
	}
	
	
	
	
	
	
}
