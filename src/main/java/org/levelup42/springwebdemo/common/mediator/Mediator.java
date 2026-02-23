package org.levelup42.springwebdemo.common.mediator;

import org.levelup42.springwebdemo.product.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Mediator {

    Map<? extends Class<?>, RequestHandler<?,?>> requestHandlerMap;

    public Mediator(List<RequestHandler<?, ?>> requestHandlers) {
        requestHandlerMap = requestHandlers
                .stream()
                .collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));

    }

    public <R ,T extends Request<R>> R dispach(T request){
        RequestHandler<T,R> handler = (RequestHandler<T,R>) requestHandlerMap.get(request.getClass());
        if(handler == null){
            throw new RuntimeException("No handler found for request type: "+ request.getClass());
        }
        return handler.handle(request);
    }

}
