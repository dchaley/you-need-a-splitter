package ynab

import org.w3c.fetch.RequestInit
import kotlin.js.Promise

typealias InitOverrideFunction = (requestContext: `T$1`) -> Promise<RequestInit>
