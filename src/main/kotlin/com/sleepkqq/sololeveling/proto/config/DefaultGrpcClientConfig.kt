package com.sleepkqq.sololeveling.proto.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

abstract class DefaultGrpcClientConfig(
	private val host: String
) {

	fun createManagedChannel(port: Int): ManagedChannel =
		ManagedChannelBuilder.forAddress(host, port)
			.usePlaintext()
			.build()
}
