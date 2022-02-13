package br.com.jhegnerlabs.estudante

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/management/api/v1/students")
class GerenciaEstudanteController {

    companion object {
        val ESTUDANTES: List<Estudante> = listOf(
            Estudante(id = 1, nome = "Paulo"),
            Estudante(id = 2, nome = "Maria"),
            Estudante(id = 3, nome = "Joao"),
        )
    }

    @GetMapping(path = ["/{estudanteId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getEstudante(@PathVariable("estudanteId") estudanteId: Int): Estudante? {
        return ESTUDANTES
            .find { it.id == (estudanteId) } ?:
            throw IllegalStateException("Estudante $estudanteId nao encontrado")
    }

    @GetMapping
    fun getTodosEstudantes(): ResponseEntity<Any> {
        val response = object {
            val data = EstudanteController.ESTUDANTES
        }
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun postNovoEstudante(@RequestBody estudante: Estudante) {
        println("Incluindo um novo estudante $estudante")
    }

    @DeleteMapping(path = ["/{estudanteId}"])
    fun deleteEstudante(estudanteId: Int) {
        println("Removendo o estudante $estudanteId")
    }

    @PutMapping(path = ["estudanteId"])
    fun putEstudante(@PathVariable estudanteId: Int, @RequestBody estudante: Estudante) {
        println("Atualizando o estudante $estudanteId com as informacoes $estudante")
    }

}