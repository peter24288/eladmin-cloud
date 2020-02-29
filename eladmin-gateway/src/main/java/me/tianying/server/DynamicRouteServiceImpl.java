package me.tianying.server;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import me.tianying.model.GatewayPredicateDefinition;
import me.tianying.model.GatewayRouteDefinition;
import me.tianying.utiles.JsonMapper;
import me.tianying.utiles.RedisUtil;
import me.tianying.vo.RouteFilterVo;
import me.tianying.vo.RoutePredicateVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
    private final Logger log = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);
    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;


    /**
     * 增加路由
     * @param definition
     * @return
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }


    /**
     * 更新路由
     * @param definition
     * @return
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: "+definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }


    }
    /**
     * 删除路由
     * @param definition
     * @return
     */
    public String deleteRouteDefinition(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
            return "delete success";
        } catch (Exception e) {
            return "update fail,not find route  routeId: "+definition.getId();
        }

    }
    /**
     * 删除路由
     * @param id
     * @return
     */
    public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }

    }
    /**
     * 刷新路由
     *
     * @return boolean
     */
    public boolean refresh() {
        //  删除之前所有数据
        if(RedisUtil.get("gatewayRouteDtoMapAfter")!=null){
            List<Map<String, String>>  gatewayRouteDtoMapMapAfter = ( List<Map<String, String>> )RedisUtil.get("gatewayRouteDtoMapAfter");
            gatewayRouteDtoMapMapAfter.forEach(e->{
                RouteDefinition routeDefinition = new RouteDefinition();
                // id
                routeDefinition.setId(e.get("routeId"));

                // predicates
                if (StringUtils.isNotBlank(e.get("predicates")))
                    routeDefinition.setPredicates(predicateDefinitions(e.get("predicates")));

                // filters
                if (StringUtils.isNotBlank(e.get("filters"))) {
                    routeDefinition.setFilters(filterDefinitions(e.get("filters")));
                }
                // uri
                routeDefinition.setUri(URI.create(e.get("uri")));
                this.deleteRouteDefinition(routeDefinition);
            });
        }

        //增加所有数据
        List<Map<String, String>>  gatewayRouteDtoMap = ( List<Map<String, String>> )RedisUtil.get("gatewayRouteDtoMap");
        if(gatewayRouteDtoMap!=null){
            RedisUtil.set("gatewayRouteDtoMapAfter",gatewayRouteDtoMap);
            gatewayRouteDtoMap.forEach(e->{
                RouteDefinition routeDefinition = new RouteDefinition();
                // id
                routeDefinition.setId(e.get("routeId"));

                // predicates
                if (StringUtils.isNotBlank(e.get("predicates")))
                    routeDefinition.setPredicates(predicateDefinitions(e.get("predicates")));

                // filters
                if (StringUtils.isNotBlank(e.get("filters"))) {
                    routeDefinition.setFilters(filterDefinitions(e.get("filters")));
                }
                // uri
                routeDefinition.setUri(URI.create(e.get("uri")));
                this.add(routeDefinition);
            });
        }
        return true;
    }
    /**
     * 刷新路由
     *
     * @return boolean
     */
    public boolean initRoute() {
        //  删除之前所有数据
        if (RedisUtil.get("gatewayRouteDtoMapAfter") != null) {
            List<Map<String, String>> gatewayRouteDtoMapMapAfter = (List<Map<String, String>>) RedisUtil.get("gatewayRouteDtoMapAfter");
            gatewayRouteDtoMapMapAfter.forEach(e -> {
                RouteDefinition routeDefinition = new RouteDefinition();
                // id
                routeDefinition.setId(e.get("routeId"));

                // predicates
                if (StringUtils.isNotBlank(e.get("predicates")))
                    routeDefinition.setPredicates(predicateDefinitions(e.get("predicates")));

                // filters
                if (StringUtils.isNotBlank(e.get("filters"))) {
                    routeDefinition.setFilters(filterDefinitions(e.get("filters")));
                }
                // uri
                routeDefinition.setUri(URI.create(e.get("uri")));
                this.add(routeDefinition);
            });
        }
        return true;
    }
        /**
         * @param predicate route
         * @return List
         * @author tianying
         * @date 2019/04/02 21:28
         */
    private List<PredicateDefinition> predicateDefinitions(String predicate) {
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        try {
            List<RoutePredicateVo> routePredicateVoList = JsonMapper.getInstance().fromJson(predicate,
                    JsonMapper.getInstance().createCollectionType(ArrayList.class, RoutePredicateVo.class));
            routePredicateVoList.forEach(routePredicateVo->{
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                predicateDefinition.setArgs(routePredicateVo.getArgs());
                predicateDefinition.setName(routePredicateVo.getName());
                predicateDefinitions.add(predicateDefinition);
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return predicateDefinitions;
    }
    /**
     * @param filters route
     * @return List
     * @author tangyi
     * @date 2019/04/02 21:29
     */
    private List<FilterDefinition> filterDefinitions(String filters) {
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        try {
            JavaType javaType = JsonMapper.getInstance().createCollectionType(ArrayList.class, RouteFilterVo.class);
            List<RouteFilterVo> gatewayFilterDefinitions = JsonMapper.getInstance().fromJson(filters, javaType);
            gatewayFilterDefinitions.forEach(filterDefinition->{
                FilterDefinition definition = new FilterDefinition();
                definition.setName(filterDefinition.getName());
                definition.setArgs(filterDefinition.getArgs());
                filterDefinitions.add(definition);
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return filterDefinitions;
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * spring:
     cloud:
     gateway:
     routes: #当访问http://localhost:8080/baidu,直接转发到https://www.baidu.com/
     - id: baidu_route
     uri: http://baidu.com:80/
     predicates:
     - Path=/baidu
     * @param args
     */

    public static void main(String[] args) {
        GatewayRouteDefinition definition = new GatewayRouteDefinition();
        GatewayPredicateDefinition predicate = new GatewayPredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        definition.setId("jd_route");
        predicate.setName("Path");
        predicateParams.put("pattern", "/jd");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        String uri="http://www.jd.com";
        //URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition.setUri(uri);
        System.out.println("definition:"+JSON.toJSONString(definition));


        RouteDefinition definition1 = new RouteDefinition();
        PredicateDefinition predicate1 = new PredicateDefinition();
        Map<String, String> predicateParams1 = new HashMap<>(8);
        definition1.setId("baidu_route");
        predicate1.setName("Path");
        predicateParams1.put("pattern", "/baidu");
        predicate1.setArgs(predicateParams1);
        definition1.setPredicates(Arrays.asList(predicate1));
        URI uri1 = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition1.setUri(uri1);
        System.out.println("definition1："+JSON.toJSONString(definition1));

    }


}
