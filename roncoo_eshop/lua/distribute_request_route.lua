-- 见173-商品详情页动态渲染系统-在分发层Nginx部署流量分发的Lua脚本
-- 获取请求参数
local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]
local hosts = {"192.168.8.10:8000", "192.168.8.11:8000"}
-- 根据商品id分发请求到hosts中的服务器
local hash = ngx.crc32_long(productId)
-- 得到服务器索引
local index = (hash % 2) + 1
local backend = "http://"..hosts[index]
local requestPath = uri_args["requestPath"]
requestPath = "/"..requestPath.."?productId="..productId
local http = require("resty.http")
local httpc = http.new()
-- 发送请求到相应服务器的服务
local resp, err = httpc:request_uri(backend,{
  method = "GET",
  path = requestPath
})

if not resp then
  ngx.say("request error: ", err)
  return
end

-- 返回响应到前端
ngx.say(resp.body)

httpc:close()
