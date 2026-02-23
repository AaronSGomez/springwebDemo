package org.levelup42.springwebdemo.common.mediator;

// clase  T es referente a la entrada, la clase R es para la respuesta
public interface RequestHandler<T extends Request<R>, R> {

    R handle(T request);
    Class<T> getRequestType();
}
