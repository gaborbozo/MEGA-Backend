package hu.bozgab.megabackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MegabackendApplication

fun main(args: Array<String>) {
	runApplication<MegabackendApplication>(*args)
}
