package algorithms.search

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BinarySearchKtTest {
    private val normalArray = listOf(1, 1, 2, 2, 3, 3)
    private val all1 = listOf(1, 1, 1, 1, 1, 1)
    private val arrNotExist2 = listOf(1, 1, 1, 3, 3, 3)

    @Test
    fun normalLowerBound() {
        assertEquals(0, normalArray.lowerBound(1))
        assertEquals(2, normalArray.lowerBound(2))
        assertEquals(4, normalArray.lowerBound(3))

        assertEquals(0, all1.lowerBound(-1))
        assertEquals(0, all1.lowerBound(1))
        assertEquals(6, all1.lowerBound(2))

        assertEquals(3, arrNotExist2.lowerBound(2))
    }

    @Test
    fun rangeLowerBound() {
        assertEquals(2, all1.lowerBound(element = 1, fromIndex = 2))
        assertEquals(2, all1.lowerBound(element = 1, fromIndex = 2, toIndex = 3))
        assertEquals(2, all1.lowerBound(element = 1, fromIndex = 2, toIndex = 2))
    }

    @Test
    fun normalUpperBound() {
        assertEquals(0, normalArray.upperBound(0))
        assertEquals(2, normalArray.upperBound(1))
        assertEquals(4, normalArray.upperBound(2))
        assertEquals(6, normalArray.upperBound(4))

        assertEquals(0, all1.upperBound(-1))
        assertEquals(0, all1.upperBound(0))
        assertEquals(6, all1.upperBound(1))
        assertEquals(6, all1.upperBound(2))

        assertEquals(3, arrNotExist2.upperBound(1))
        assertEquals(3, arrNotExist2.upperBound(2))
        assertEquals(6, arrNotExist2.upperBound(3))
    }

    @Test
    fun rangeUpperBound() {
        assertEquals(3, normalArray.upperBound(element = 1, fromIndex = 3))
        assertEquals(4, normalArray.upperBound(element = 1, fromIndex = 4, toIndex = 5))
        assertEquals(4, normalArray.upperBound(element = 1, fromIndex = 4, toIndex = 4))

        assertEquals(4, normalArray.upperBound(element = 2, fromIndex = 0, toIndex = 4))
    }
}