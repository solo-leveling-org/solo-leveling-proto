package com.sleepkqq.sololeveling.proto.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

abstract class DefaultGrpcClientConfig {

	fun createManagedChannel(properties: GrpcServiceProperties): ManagedChannel =
		ManagedChannelBuilder.forAddress(properties.host, properties.port)
			.usePlaintext()
			.build()
}
