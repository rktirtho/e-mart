# <p style="text-align: center;">API Gateway (Spring Cloud Gateway)</p>
[*Read official document*](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)



## Spring cloud Gateway
This project provides an API Gateway built on top of the Spring Ecosystem, including: Spring 6, Spring Boot 3 and 
Project Reactor. Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide 
cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

## Glossary
**Route**: The basic building block of the gateway. It is defined by an ID, a destination URI, a collection of 
predicates, and a collection of filters. A route is matched if the aggregate predicate is true.

**Predicate:** This is a Java 8 Function Predicate. The input type is a Spring Framework ServerWebExchange. 
This lets you 
match on anything from the HTTP request, such as headers or parameters.

**Filter:** These are instances of GatewayFilter that have been constructed with a specific factory.
Here, you can modify requests and responses before or after sending the downstream request.

## Route Predicate Factories

Spring Cloud Gateway matches routes as part of the Spring WebFlux HandlerMapping infrastructure. Spring Cloud Gateway 
includes many built-in route predicate factories. All of these predicates match on different attributes of the
HTTP request. You can combine multiple route predicate factories with logical and statements.

### Before, After  Route Predicate Factory
**The Before and After route predicate factory** takes one parameter(Java `ZonedDateTIme`). The predicate matches request 
that happen before and after the specific time respectively. On the other hand Between takes two `ZonedDateTIme` type 
parameters. It matches the request between the start time and end time. Subsequently, the url will not be available 
except the given time.

### The Header and Host Route Predicate Factory
**The Header route predicate factory** takes two parameters, the header and a regexp (which is a Java regular expression). 
This predicate matches with a header that has the given name whose value matches the regular expression. 
This route matches if the request has a header named X-Request-Id whose value matches the \d+ regular expression 
(that is, it has a value of one or more digits).

**The Host route predicate factory** takes one parameter: a list of host name patterns. The pattern is an Ant-style 
pattern with . as the separator. This predicates matches the Host header that matches the pattern.

```yaml
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: inventory-service
          uri: lb://INVENTORY-SERVICE
          predicates:
            - Path=/api/v1/inventory/**   # if any path start with api/products then it will be redirected to the inventory service with the given url (lb://INVENTORY-SERVICE//api/v1/inventory/**)
            - Before=2017-01-20T17:42:47.789-07:00[America/Denver]
            - After=2017-01-20T17:42:47.789-07:00[America/Denver]
            - Between=2017-01-20T17:42:47.789-07:00[America/Denver]
            - Cookie=chocolate, ch.p
            - Header=X-Request-Id, \d+
            - Host=**.somehost.org,**.anotherhost.org #This route matches if the request has a Host header with a value of www.somehost.org or beta.somehost.org or www.anotherhost.org.
            - Method=GET,POST
            - Query=green, gree #The preceding route matches if the request contained a red query parameter whose value matched the gree. regexp, so green and greet would match.
            - RemoteAddr=192.168.1.1/24 #this route matches if the remote address of the request was, for example, 192.168.1.10.
```

### The Method and Path Route Predicate Factory

**The Path Route Predicate** Factory takes two parameters: a list of Spring `PathMatcher` patterns and 
an optional flag called `matchTrailingSlash` (defaults to true). If `matchTrailingSlash` is set to false, 
then request path /red/1/ will not be matched.

**The Query route predicate factory** takes two parameters: a required param and an optional regexp (which is a Java regular expression).

### The RemoteAddr Route Predicate Factory
The RemoteAddr route predicate factory takes a list (min size 1) of sources, which are CIDR-notation (IPv4 or IPv6) 
strings, such as 192.168.0.1/16 (where 192.168.0.1 is an IP address and 16 is a subnet mask). 

### The Weight Route Predicate Factory
The Weight route predicate factory takes two arguments: group and weight (an int). The weights are calculated per group.
The following example configures a weight route predicate:
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: weight_high
        uri: https://weighthigh.org
        predicates:
        - Weight=group1, 8
      - id: weight_low
        uri: https://weightlow.org
        predicates:
        - Weight=group1, 2
#This route would forward ~80% of traffic to weighthigh.org and ~20% of traffic to weighlow.org
```

### The XForwarded Remote Addr Route Predicate Factory
The XForwarded Remote Addr route predicate factory takes a list (min size 1) of sources, which are CIDR-notation 
(IPv4 or IPv6) strings, such as 192.168.0.1/16 (where 192.168.0.1 is an IP address and 16 is a subnet mask).
This can be used with reverse proxies such as load balancers or web application firewalls where the request 
should only be allowed if it comes from a trusted list of IP addresses used by those reverse proxies.
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: xforwarded_remoteaddr_route
        uri: https://example.org
        predicates:
        - XForwardedRemoteAddr=192.168.1.1/24
#This route matches if the X-Forwarded-For header contains, for example, 192.168.1.10.
```

## GatewayFilter Factories

Route filters allow the modification of the incoming HTTP request or outgoing HTTP response in some manner.
Route filters are scoped to a particular route. Spring Cloud Gateway includes many built-in GatewayFilter Factories.

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: add_request_header_route
        uri: https://example.org
        predicates:
          - Path=/red/{segment}
        filters:
          - AddRequestHeader=X-Request-red, blue
          - AddRequestHeader=X-Request-Red, Blue-{segment}
          - AddRequestHeadersIfNotPresent=X-Request-Color-1:blue,X-Request-Color-2:green
          - AddRequestHeadersIfNotPresent=X-Request-Red:Blue-{segment}
          - AddRequestParameter=red, blue
          - AddRequestParameter=foo, bar-{segment}
          - AddResponseHeader=X-Response-Red, Blue
          - AddResponseHeader=foo, bar-{segment}
          - SetPath=/{segment}
```

### The Header GatewayFilter Factory
**The AddRequestHeader GatewayFilter factory** takes a name and value parameter. The following example configures an 
AddRequestHeader GatewayFilter:

**The AddRequestHeadersIfNotPresent GatewayFilter factory** takes a collection of name and value pairs separated by colon.
This listing adds 2 headers X-Request-Color-1:blue and X-Request-Color-2:green to the downstream request’s headers for 
all matching requests. This is similar to how AddRequestHeader works, but unlike AddRequestHeader it will do it only if 
the header is not already there. Otherwise, the original value in the client request is sent.
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: add_request_header_route
        uri: https://example.org
        filters:
          - AddRequestHeader=X-Request-red, blue
          - AddRequestHeader=X-Request-Red, Blue-{segment}
          - AddRequestHeadersIfNotPresent=X-Request-Color-1:blue,X-Request-Color-2:green
          - AddRequestHeadersIfNotPresent=X-Request-Red:Blue-{segment}
        
# This listing adds X-Request-red:blue header to the downstream request’s headers for all matching requests.
```

### The AddRequestParameter GatewayFilter Factory
The AddRequestParameter GatewayFilter Factory takes a name and value parameter. The following example configures an
AddRequestParameter GatewayFilter:
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: add_request_parameter_route
        uri: https://example.org
        filters:
        - AddRequestParameter=red, blue
# This will add red=blue to the downstream request’s query string for all matching requests.
```
AddRequestParameter is aware of the URI variables used to match a path or host. URI variables may be used in the value
and are expanded at runtime. The following example configures an AddRequestParameter GatewayFilter that uses a variable:
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: add_request_parameter_route
        uri: https://example.org
        predicates:
        - Host: {segment}.myhost.org
        filters:
        - AddRequestParameter=foo, bar-{segment}

```

### The AddResponseHeader GatewayFilter Factory

The AddResponseHeader GatewayFilter Factory takes a name and value parameter. The following example configures an 
AddResponseHeader GatewayFilter:
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: add_response_header_route
        uri: https://example.org
        filters:
        - AddResponseHeader=X-Response-Red, Blue
# This adds X-Response-Red:Blue header to the downstream response’s headers for all matching requests.
```
AddResponseHeader is aware of URI variables used to match a path or host. URI variables may be used in the value and are
expanded at runtime. 

### The SetPath GatewayFilter Factory
The SetPath GatewayFilter factory takes a path template parameter. It offers a simple way to manipulate the request 
path by allowing templated segments of the path. This uses the URI templates from Spring Framework.
Multiple matching segments are allowed. 
```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: setpath_route
        uri: https://example.org
        predicates:
        - Path=/red/{segment}
        filters:
        - SetPath=/{segment}
# For a request path of /red/blue, this sets the path to /blue before making the downstream request.
```