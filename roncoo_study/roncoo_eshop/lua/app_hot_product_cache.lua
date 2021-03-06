-- 见80-在流量分发+后端应用双层Nginx中加入接收热点缓存数据的接口
-- 部署在应用Nginx
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
local product_info = uri_args["productInfo"]
local product_cache_key = "product_info_" .. product_id
local cache_ngx = ngx.shared.my_cache

cache_ngx:set(product_cache_key, product_info, 60 * 60)