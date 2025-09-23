package com.group7.grpcdemo;

import com.group7.grpcdemo.HelloWorldProto.HelloReply;
import com.group7.grpcdemo.HelloWorldProto.HelloRequest;
import com.group7.grpcdemo.HelloWorldProto.AddReply;
import com.group7.grpcdemo.HelloWorldProto.AddRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloWorldClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setCountry("Fiji").setName("World").build());
        System.out.println("Greeting: " + response.getMessage());

        AddReply addReply = stub.add(AddRequest.newBuilder().setX(5).setY(8).build());
        System.out.println("Add: " + addReply.getResult());

        channel.shutdown();
    }
}