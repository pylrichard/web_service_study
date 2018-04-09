-- 提升缓存命中率，采用分发层+应用层双层Nginx架构，见51-基于分发层+应用层的双层Nginx架构提升缓存命中率方案分析
-- 分发层Nginx负责流量分发，定义规则比如根据productId进行hash，然后对后端应用Nginx数量进行取模
-- 将某一个商品的访问请求固定路由到一个应用Nginx，保证只会从Redis获取一次缓存数据，之后从Nginx本地缓存获取
-- 可以大幅度提升Nginx本地缓存命中率，大幅度减少Redis压力，提升性能
--
-- 引入lua http lib
-- cd lualib/resty/
-- wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http_headers.lua
-- wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http.lua
-- /usr/servers/nginx/sbin/nginx -s reload
--
-- 见173-商品详情页动态渲染系统-在分发层Nginx部署流量分发的Lua脚本
-- 获取请求参数
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
-- 应用Nginx服务器IP地址列表
local hosts = { "192.168.8.10:8000", "192.168.8.11:8000" }
-- 根据商品id分发请求到hosts中的服务器
local hash = ngx.crc32_long(product_id)
-- 对应用Nginx数量取模，得到服务器IP地址列表索引
local index = (hash % 2) + 1
local backend = "http://" .. hosts[index]
local request_path = uri_args["requestPath"]
request_path = "/" .. request_path .. "?productId=" .. product_id
local http = require("resty.http")
local httpc = http.new()
-- 发送请求到相应的应用Nginx
local resp, err = httpc:request_uri(backend, {
    method = "GET",
    path = request_path
})

if not resp then
    ngx.say("request error: ", err)
    return
end

-- 返回响应到前端
ngx.say(resp.body)

httpc:close()
