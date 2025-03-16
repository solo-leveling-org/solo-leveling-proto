package com.sleepkqq.sololeveling.proto.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DefaultGrpcClientConfig {

  private final String host;

  public ManagedChannel createManagedChannel(int port) {
    return ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext()
        .build();
  }
}
