//package me.tianying.filters;
//
//import java.lang.annotation.Annotation;
//import java.util.Collections;
//import java.util.List;
//
//import jdk.nashorn.internal.objects.Global;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.ResolvableType;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.HttpMessageReader;
//import org.springframework.http.codec.multipart.FormFieldPart;
//import org.springframework.http.codec.multipart.Part;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.reactive.function.server.HandlerStrategies;
//import org.springframework.web.server.ServerWebExchange;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
///**
// * 获取请求内容.
// *
// * @author zyb
// */
//@Component
//public class GatewayContextFilter implements GlobalFilter, Ordered {
//
//    /**
//     * default HttpMessageReader.
//     */
//    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        final ServerHttpRequest request = exchange.getRequest();
//        final String path = request.getPath().pathWithinApplication().value();
//        return chain.filter(exchange);
//        final GatewayContext gatewayContext = new GatewayContext();
//        gatewayContext.setPath(path);
//        exchange.getAttributes().put(GatewayContext.CACHE_GATEWAY_CONTEXT, gatewayContext);
//        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
//            DataBufferUtils.retain(dataBuffer);
//            final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
//            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return cachedFlux;
//                }
//            };
//            final ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
//            return cacheBody(mutatedExchange, chain);
//        });
//    }
//
//    @SuppressWarnings("unchecked")
//    private Mono<Void> cacheBody(ServerWebExchange exchange, GatewayFilterChain chain) {
//        final HttpHeaders headers = exchange.getRequest().getHeaders();
//        if (headers.getContentLength() == 0) {
//            return chain.filter(exchange);
//        }
//        final ResolvableType resolvableType;
//        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(headers.getContentType())) {
//            resolvableType = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);
//        } else {
//            resolvableType = ResolvableType.forClass(String.class);
//        }
//        return MESSAGE_READERS.stream().filter(reader -> reader.canRead(resolvableType, exchange.getRequest().getHeaders().getContentType())).findFirst()
//                .orElseThrow(() -> new IllegalStateException("no suitable HttpMessageReader.")).readMono(resolvableType, exchange.getRequest(), Collections.emptyMap()).flatMap(resolvedBody -> {
//                    if (resolvedBody instanceof MultiValueMap) {
//                        final Part partInfo = (Part) ((MultiValueMap) resolvedBody).getFirst("info");
//                        if (partInfo instanceof FormFieldPart) {
////                            gatewayContext.setRequestBody(((FormFieldPart) partInfo).value());
//                        }
//                    } else {
////                        gatewayContext.setRequestBody((String) resolvedBody);
//                    }
//                    return chain.filter(exchange);
//                });
//    }
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//
//}