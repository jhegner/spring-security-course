package br.com.jhegnerlabs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppBoot

fun main(args: Array<String>) {
	runApplication<AppBoot>(*args)
}
