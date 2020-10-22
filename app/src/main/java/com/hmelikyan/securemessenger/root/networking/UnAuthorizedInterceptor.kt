package com.armboldmind.baggleapp.root.networking

import com.google.gson.Gson
import okhttp3.*

class UnAuthorizedInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        /**
         * UnAuthorized Response sender for test
         * */
        return Response.Builder()
            .code(401)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .message("Unauthorized")
            .body(
                ResponseBody.create(
                    MediaType.get("application/json"),
                    Gson().toJson(mapOf("cause" to "not sure"))
                )
            )
            .build()

    }
}