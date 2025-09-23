package com.group7.grpcdemo;

import com.group7.grpcdemo.HelloWorldProto.HelloReply;
import com.group7.grpcdemo.HelloWorldProto.HelloRequest;
import com.group7.grpcdemo.HelloWorldProto.AddReply;
import com.group7.grpcdemo.HelloWorldProto.AddRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;

public class HelloWorldServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051).addService(new GreeterImpl()).build().start();
        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName() + " from " + req.getCountry()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void add(AddRequest req, StreamObserver<AddReply> responseObserver) {
            AddReply reply = AddReply.newBuilder().setResult(req.getX() + req.getY()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
