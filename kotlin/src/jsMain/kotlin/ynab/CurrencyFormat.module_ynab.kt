@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package ynab

external interface CurrencyFormat {
    var iso_code: String
    var example_format: String
    var decimal_digits: Number
    var decimal_separator: String
    var symbol_first: Boolean
    var group_separator: String
    var currency_symbol: String
    var display_symbol: Boolean
}
