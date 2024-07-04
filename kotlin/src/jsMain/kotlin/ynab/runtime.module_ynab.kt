@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

import js.collections.JsSet
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

external interface ConfigurationParameters {
  var basePath: String?
    get() = definedExternally
    set(value) = definedExternally
  var fetchApi: Any?
    get() = definedExternally
    set(value) = definedExternally
  var middleware: Array<Middleware>?
    get() = definedExternally
    set(value) = definedExternally
  var queryParamsStringify: ((params: HTTPQuery) -> String)?
    get() = definedExternally
    set(value) = definedExternally
  var username: String?
    get() = definedExternally
    set(value) = definedExternally
  var password: String?
    get() = definedExternally
    set(value) = definedExternally
  var apiKey: dynamic /* String? | ((name: String) -> String)? */
    get() = definedExternally
    set(value) = definedExternally
  var accessToken: dynamic /* String? | Promise<String>? | ((name: String, scopes: Array<String>) -> dynamic)? */
    get() = definedExternally
    set(value) = definedExternally
  var headers: HTTPHeaders?
    get() = definedExternally
    set(value) = definedExternally
  var credentials: String? /* "include" | "omit" | "same-origin" */
    get() = definedExternally
    set(value) = definedExternally
}

external open class Configuration(configuration: ConfigurationParameters = definedExternally) {
  open var configuration: Any
}

external open class BaseAPI(configuration: Configuration = definedExternally) {
  open var configuration: Configuration
  open var middleware: Any
  open fun <T : BaseAPI> withMiddleware(self: T, vararg middlewares: Middleware): T
  open fun <T : BaseAPI> withPreMiddleware(self: T, vararg preMiddlewares: Any): T
  open fun <T : BaseAPI> withPostMiddleware(self: T, vararg postMiddlewares: Any): T
  open fun isJsonMime(mime: String?): Boolean
  open fun request(context: RequestOpts, initOverrides: RequestInit = definedExternally): Promise<Response>
  open fun request(context: RequestOpts): Promise<Response>
  open fun request(context: RequestOpts, initOverrides: InitOverrideFunction = definedExternally): Promise<Response>
  open var createFetchParams: Any
  open var fetchApi: Any
  open var clone: Any

  companion object {
    var jsonRegex: Any
  }
}


external interface HTTPHeaders {
  @nativeGetter
  operator fun get(key: String): String?

  @nativeSetter
  operator fun set(key: String, value: String)
}

external interface HTTPQuery {
  @nativeGetter
  operator fun get(key: String): dynamic /* String? | Number? | Boolean? | Array<dynamic /* String? | Number? | Boolean? */>? | Set<dynamic /* String? | Number? | Boolean? */>? | HTTPQuery? */

  @nativeSetter
  operator fun set(key: String, value: String?)

  @nativeSetter
  operator fun set(key: String, value: Number?)

  @nativeSetter
  operator fun set(key: String, value: Boolean?)

  @nativeSetter
  operator fun set(key: String, value: Array<dynamic /* String? | Number? | Boolean? */>?)

  @nativeSetter
  operator fun set(key: String, value: JsSet<dynamic /* String? | Number? | Boolean? */>?)

  @nativeSetter
  operator fun set(key: String, value: HTTPQuery?)
}

external interface HTTPRequestInit {
  var headers: HTTPHeaders?
    get() = definedExternally
    set(value) = definedExternally
  var method: String /* "GET" | "POST" | "PUT" | "PATCH" | "DELETE" | "OPTIONS" | "HEAD" */
  var credentials: String? /* "include" | "omit" | "same-origin" */
    get() = definedExternally
    set(value) = definedExternally
  var body: dynamic /* Json? | FormData? | URLSearchParams? */
    get() = definedExternally
    set(value) = definedExternally
}

external interface `T$1` {
  var init: HTTPRequestInit
  var context: RequestOpts
}

external interface FetchParams {
  var url: String
  var init: RequestInit
}

external interface RequestOpts {
  var path: String
  var method: String /* "GET" | "POST" | "PUT" | "PATCH" | "DELETE" | "OPTIONS" | "HEAD" */
  var headers: HTTPHeaders
  var query: HTTPQuery?
    get() = definedExternally
    set(value) = definedExternally
  var body: dynamic /* Json? | FormData? | URLSearchParams? */
    get() = definedExternally
    set(value) = definedExternally
}

external interface RequestContext {
  var fetch: Any
  var url: String
  var init: RequestInit
}

external interface ResponseContext {
  var fetch: Any
  var url: String
  var init: RequestInit
  var response: Response
}

external interface ErrorContext {
  var fetch: Any
  var url: String
  var init: RequestInit
  var error: Any
  var response: Response?
    get() = definedExternally
    set(value) = definedExternally
}

external interface Middleware {
  val pre: ((context: RequestContext) -> Promise<dynamic /* FetchParams | Unit */>)?
  val post: ((context: ResponseContext) -> Promise<dynamic /* Response | Unit */>)?
  val onError: ((context: ErrorContext) -> Promise<dynamic /* Response | Unit */>)?
}

external interface ApiResponse<T> {
  var raw: Response
  fun value(): Promise<T>
}
