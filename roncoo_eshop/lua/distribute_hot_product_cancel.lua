-- 见82-在Storm拓扑中加入热点缓存消失的实时自动识别和感知的代码逻辑
-- 部署在流量分发Nginx
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
local cache_ngx = ngx.shared.my_cache
local hot_product_cache_key = "hot_product_" .. product_id

-- 60s后清除热点缓存
cache_ngx:set(hot_product_cache_key, "false", 60)