package com.sleepkqq.sololeveling.proto.config

import com.sleepkqq.sololeveling.proto.constants.LocaleProperties.LOCALE_METADATA_KEY
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import org.springframework.context.i18n.LocaleContextHolder

@Suppress("unused")
class LocaleClientInterceptor : ClientInterceptor {

	override fun <ReqT, RespT> interceptCall(
		method: MethodDescriptor<ReqT, RespT>,
		callOptions: CallOptions,
		next: Channel
	): ClientCall<ReqT, RespT> {

		return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
			next.newCall(method, callOptions)
		) {
			override fun start(responseListener: Listener<RespT>, headers: Metadata) {
				val currentLocale = LocaleContextHolder.getLocale().language
				headers.put(
					Metadata.Key.of(LOCALE_METADATA_KEY, Metadata.ASCII_STRING_MARSHALLER),
					currentLocale
				)

				super.start(responseListener, headers)
			}
		}
	}
}
