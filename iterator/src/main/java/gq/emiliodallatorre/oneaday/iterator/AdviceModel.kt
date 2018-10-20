package gq.emiliodallatorre.oneaday.iterator

/**
 * This is the class which can represent an entire advice, complete of all the data needed to handle it.
 * It contains the values that can be used by the app in every situation.
 * If you need to move an advice, you can easily use this class.
 * If you need to implement new values for an advice, you can declare new variables here.
 * @return null if the variable is not initialized.
 */
class AdviceModel {
    // TODO: Implement a way to achieve this w/o nullable values.
    var title: String? = null
    var subtitle: String? = null
    var date: Int? = null
    var month: Int? = null
    var bar: Boolean? = null
    var dayOfPath: Int? = null
}