-- 见174-商品详情页动态渲染系统-完成应用层Nginx的Lua脚本的编写与部署
local cjson = require("cjson")
local http = require("resty.http")
local redis = require("resty.redis")  

local function close_redis(red)  
	if not red then  
		return  
	end
	local pool_max_idle_time = 10000 
	local pool_size = 100 
	local ok, err = red:set_keepalive(pool_max_idle_time, pool_size)  
	if not ok then  
		ngx.say("set keepalive error : ", err)  
	end  
end 

local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]
local cache_ngx = ngx.shared.my_cache
local productCacheKey = "product_"..productId
-- 从Nginx本地Cache获取数据
local productCache = cache_ngx:get(productCacheKey)

if productCache == "" or productCache == nil then
  local red = redis:new()
  red:set_timeout(1000)  
  local ip = "192.168.8.10"
  local port = 1112  
  local ok, err = red:connect(ip, port)  
  if not ok then  
    ngx.say("connect to redis error : ", err)  
    return close_redis(red)  
  end
  -- 通过Twemproxy从Redis从集群获取数据
  local redisResp, redisErr = red:get("dim_product_"..productId)

  if redisResp == ngx.null then  
    local httpc = http.new()
    -- 调用eshop-datalink-service的API获取数据
    local resp, err = httpc:request_uri("http://192.168.8.10:8767",{
      method = "GET",
      path = "/product?productId="..productId
    })
	
    productCache = resp.body
  end

  productCache = redisResp

  math.randomseed(tostring(os.time()):reverse():sub(1, 7))
  local expireTime = math.random(600, 1200)  
  -- 更新Nginx本地Cache
  cache_ngx:set(productCacheKey, productCache, expireTime)
end

local context = {
  productInfo = productCache,
}

-- 渲染HTML
local template = require("resty.template")
template.render("product.html", context)
