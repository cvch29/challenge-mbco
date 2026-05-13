package com.example.challengembco

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Este test es correcto
    @Test
    fun multiplication_isCorrect() {
        assertEquals(6, 2 * 3)
    }

    // // FALLA: resultado incorrecto a propósito
    // @Test
    // fun addition_shouldFail() {
    //     assertEquals(5, 2 + 2)
    // }

    // // FALLA: comparación de strings incorrecta
    // @Test
    // fun string_shouldFail() {
    //     assertEquals("Hola mundo", "Hola" + " mundo!")
    // }

    // // // FALLA: booleano invertido
    // // @Test
    // // fun boolean_shouldFail() {
    // //     assertTrue(false)
    // // }

    // // FALLA: null inesperado
    // @Test
    // fun null_shouldFail() {
    //     val value: String? = null
    //     assertNotNull(value)
    // }
}