package com.sleepkqq.sololeveling.proto.config

import com.sleepkqq.sololeveling.proto.constants.LocaleProperties.LOCALE_METADATA_KEY
import com.sleepkqq.sololeveling.proto.constants.LocaleProperties.SUPPORTED_LOCALES
import io.grpc.ForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale
import kotlin.text.isNullOrBlank

@Suppress("unused")
class LocaleServerInterceptor : ServerInterceptor {

	override fun <ReqT, RespT> interceptCall(
		call: ServerCall<ReqT, RespT>,
		headers: Metadata,
		next: ServerCallHandler<ReqT, RespT>
	): ServerCall.Listener<ReqT> {
		val locale = extractLocaleFromMetadata(headers)
		LocaleContextHolder.setLocale(locale)

		return object : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
			next.startCall(call, headers)
		) {
			override fun onComplete() {
				try {
					super.onComplete()
				} finally {
					LocaleContextHolder.resetLocaleContext()
				}
			}

			override fun onCancel() {
				try {
					super.onCancel()
				} finally {
					LocaleContextHolder.resetLocaleContext()
				}
			}
		}
	}

	private fun extractLocaleFromMetadata(metadata: Metadata): Locale {
		val customLocale = metadata.get(
			Metadata.Key.of(LOCALE_METADATA_KEY, Metadata.ASCII_STRING_MARSHALLER)
		)
		if (!customLocale.isNullOrBlank() && SUPPORTED_LOCALES.contains(customLocale)) {
			return Locale.forLanguageTag(customLocale)
		}

		return Locale.ENGLISH
	}
}
