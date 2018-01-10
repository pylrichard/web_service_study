-- 见80-在流量分发+后端应用双层Nginx中加入接收热点缓存数据的接口
-- 部署在流量分发Nginx
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
local cache_ngx = ngx.shared.my_cache
local hot_product_cache_key = "hot_product_" .. product_id

cache_ngx:set(hot_product_cache_key, "true", 60 * 60)