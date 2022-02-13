package br.com.jhegnerlabs.estudante

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/students")
class EstudanteController {

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

}