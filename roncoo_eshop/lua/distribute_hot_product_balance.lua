-- 见81-在Nginx+Lua中实现热点缓存自动降级为负载均衡流量分发策略的逻辑

math.randomseed(tostring(os.time()):reverse():sub(1, 7))
math.random(1, 2)

local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]
local shopId = uri_args["shopId"]
-- 应用Nginx服务器IP地址
local hosts = { "192.168.8.10", "192.168.8.11" }
local backend = ""
local hot_product_key = "hot_product_" .. productId
local cache_ngx = ngx.shared.my_cache
local hot_product_flag = cache_ngx:get(hot_product_key)

-- 流量分发Nginx接收到商品详情页访问请求时，判断访问的是热点缓存，进行流量分发策略的降级
-- Hash策略将同一个productId的访问请求路由到同一台应用Nginx
-- 降级成随机负载均衡发送访问请求到所有应用Nginx，避免大量请求集中到一个Nginx
if hot_product_flag == "true" then
    math.randomseed(tostring(os.time()):reverse():sub(1, 7))
    local index = math.random(1, 2)
    backend = "http://" .. hosts[index]
else
    local hash = ngx.crc32_long(productId)
    local index = (hash % 2) + 1
    backend = "http://" .. hosts[index]
end

local requestPath = uri_args["requestPath"]
requestPath = "/" .. requestPath .. "?productId=" .. productId .. "&shopId=" .. shopId
local http = require("resty.http")
local httpc = http.new()
local resp, err = httpc:request_uri(backend, {
    method = "GET",
    path = requestPath
})

if not resp then
    ngx.say("request error: ", err)
    return
end

ngx.say(resp.body)

httpc:close()
