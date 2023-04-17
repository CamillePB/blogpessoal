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
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	@Autowired
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll()); // SELECT * FROM tb_posagens
	}

	@GetMapping("/{id}") // entre chaves: parametro é uma variavel
	public ResponseEntity<Tema> getAll(@PathVariable Long id) {
		return temaRepository.findById(id) // Selecionar atributos pelo id: SELECT * FROM tb_posagens WHERE id = id;

				.map(resposta -> ResponseEntity.ok(resposta)) // findById em resposta, se existir, mostra a resposta

				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // findById em resposta, se null, resposta
																				// por build

	}

	@GetMapping("/descricao/{descricao}") // entre chaves: parametro é uma variavel
	public ResponseEntity<List<Tema>> getByTitulo(@PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}

	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema) {// Popular tabela
		return ResponseEntity.status(HttpStatus.CREATED)// Resposta para um uso especifico do CORPO da requisição
				.body(temaRepository.save(tema));
		// INSERT INTO tb_postagens (data, ttulo, texto);
	}

	@PutMapping
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema) {// Popular tabela
		// UPTADE tb_postagens SET titulo = ?, texto = ?, data = ? WHERE id = id;

		return temaRepository.findById(tema.getId())// Resposta para um uso especifico do CORPO da requisição

				.map(resposta -> ResponseEntity.status(HttpStatus.OK) // findById em resposta, se existir, mostra a
																		// resposta
						.body(temaRepository.save(tema)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT) // Resposta para sem conteudo
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		Optional<Tema> tema = temaRepository.findById(id);

		if (tema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		temaRepository.deleteById(id);
		// DELETE * FROM tb_postagens WHERE id = id;
	}
}
