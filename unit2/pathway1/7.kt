open class Phone(var isScreenLightOn: Boolean = false) {
    open fun switchOn() {
        isScreenLightOn = true
    }

    fun switchOff() {
        isScreenLightOn = false
    }

    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}

class FoldablePhone : Phone() {
    var isFolded = true

    @Override
    override fun switchOn() {
        if(!isFolded) {
            isScreenLightOn = true
        }
    }

    fun fold() {
        isFolded = true
    }

    fun unfold() {
        isFolded = false
    }
}

// fun main() {
//     val myPhone = FoldablePhone()
//     myPhone.switchOn()
//     myPhone.checkPhoneScreenLight()
//     myPhone.fold()
//     myPhone.switchOn()
//     myPhone.checkPhoneScreenLight()
//     myPhone.unfold()
//     myPhone.switchOn()
//     myPhone.checkPhoneScreenLight()
// }