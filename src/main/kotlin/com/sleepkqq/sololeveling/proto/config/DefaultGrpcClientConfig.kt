package com.sleepkqq.sololeveling.proto.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

abstract class DefaultGrpcClientConfig {

	fun createManagedChannel(host: String, port: Int): ManagedChannel =
		ManagedChannelBuilder.forAddress(host, port)
			.usePlaintext()
			.build()
}
