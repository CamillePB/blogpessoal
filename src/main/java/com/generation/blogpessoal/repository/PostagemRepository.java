package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

//Interface que herda os elementos da model que conecta o banco de dados

//Uma interface pode ser usada para definir comportamentos comuns de varias classes diferentes
//uma interface possui variaveis constantes e métodos abstratos (métodos sem implementação)

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{

    List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String Titulo); 
    //consulta,colletciton lista armazaena postagem, containig = like %f%;
    //ignorecase= ignorar letras minusculas ou maiusculas
    //SELECT * FROM tb_postagens WHERE titulo LIKE "texto que quero encontrar";
}

