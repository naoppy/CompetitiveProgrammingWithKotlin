package algorithms.math

class ModInt(val modulus: Long = (1e9 + 7).toLong(),
             initialValue: Long = 0L) {
    var value: Long = initialValue % modulus
        private set

    constructor(modulus: Number, initialValue: Number) :
            this(modulus.toLong(), initialValue.toLong()) {
    }

    operator fun plusAssign(other: ModInt) {
        this.value += other.value
        if (this.value >= this.modulus) {
            this.value -= this.modulus
        }
    }

    operator fun minusAssign(other: ModInt) {
        if (this.value < other.value) {
            this.value += this.modulus
        }
        this.value -= other.value
    }

    operator fun timesAssign(other: ModInt) {
        this.value = this.value * other.value % this.modulus
    }

    operator fun divAssign(other: ModInt) {
        this *= other.inverse()
    }

    fun inverse(): ModInt {
        var a = this.value
        var b = modulus
        var u = 1L
        var v = 0L
        while (b > 0) {
            val t = a / b
            a -= t * b
            a = b.also { b = a }
            u -= t * v
            u = v.also { v = u }
        }
        u %= modulus
        if (u < 0) u += modulus
        return ModInt(this.modulus, u)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Number -> this.value == other.toLong()
            is ModInt -> this.value == other.value
            else -> false
        }
    }

    override fun toString() = this.value.toString()
    override fun hashCode(): Int {
        var result = modulus.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}