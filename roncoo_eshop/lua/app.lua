-- 见178-商品详情页动态渲染系统-高可用架构优化之读链路多级降级思路介绍
--
-- 读链路:Nginx local cache -> 本机房Redis从集群 -> 数据直连服务的JVM堆缓存 -> Redis主集群 -> 依赖服务
-- 读链路的降级:本机房Redis从集群不可用，降级为访问数据直连服务，数据直连服务不可用，降级为跨机房访问Redis主集群
local DIFF_TIME = 60
local FAILURE_CNT = 10
local DATALINK_SERVICE_IP = "192.168.8.10"
local DATALINK_SERVICE_PORT = 8767
local REDIS_MASTER_IP = "192.168.8.10"
local REDIS_MASTER_PORT = 1111
local REDIS_SLAVE_IP = "192.168.8.11"
local REDIS_SLAVE_PORT = 1112
local cjson = require("cjson")
local http = require("resty.http")
local redis = require("resty.redis")
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
local nginx_local_cache = ngx.shared.my_cache
local product_cache_key = "product_"..product_id
-- 从Nginx本地Cache获取数据
local product_cache = nginx_local_cache:get(product_cache_key)

local function get_diff_time(downgrade_time)
    local cur_time = os.time()
	return os.difftime(cur_time, nginx_local_cache:get(downgrade_time))
end

local function try_upgrade_datalink_service()
	local diff_time = get_diff_time("start_data_link_downgrade_time")
    -- 降级时间超过阈值
    if diff_time > DIFF_TIME then
	    local httpc = http.new()
		-- 重连数据直连服务
	    local resp, err = httpc:request_uri("http://DATALINK_SERVICE_IP:DATALINK_SERVICE_PORT", {
            method = "GET",
    	    path = "/product?productId="..product_id
	    })
        -- 如果服务可用，设置降级标识为false
	    if resp then
	        nginx_local_cache:set("data_link_downgrade", "false")
    	end
    end
end

local function access_datalink_service()
    local httpc = http.new()
	local resp, err = httpc:request_uri("http://DATALINK_SERVICE_IP:DATALINK_SERVICE_PORT", {
	    method = "GET",
	    path = "/product?productId="..product_id
	})

	if not resp then
	    ngx.say("request error :", err)
		local data_link_failure_cnt = nginx_local_cache:get("data_link_failure_cnt")
		nginx_local_cache:set("data_link_failure_cnt", data_link_failure_cnt + 1)

		if data_link_failure_cnt > FAILURE_CNT then
		    nginx_local_cache:set("data_link_downgrade", "true")
		    nginx_local_cache:set("start_data_link_downgrade_time", os.time())
		end
	end

	product_cache = resp.body
end

local function connect_redis(ip, port)
    local red = redis:new()
	red:set_timeout(1000)
	return red:connect(ip, port), red
end

local function try_upgrade_redis_slave()
	local diff_time = get_diff_time("start_redis_slave_downgrade_time")

	if diff_time > DIFF_TIME then
	    local ok, err, red = connect_redis(REDIS_SLAVE_IP, REDIS_SLAVE_PORT)

	    if ok then
    	    nginx_local_cache:set("redis_slave_downgrade", "false")
		end
	end
end

local function access_redis_slave()
    local ok, err, red = connect_redis(REDIS_SLAVE_IP, REDIS_SLAVE_PORT)

    if not ok then
        ngx.say("connect to redis error : ", err)
    	local redis_slave_failure_cnt = nginx_local_cache:get("redis_slave_failure_cnt")
	    nginx_local_cache:set("redis_slave_failure_cnt", redis_slave_failure_cnt + 1)

		-- 达到失败次数上限进行降级
		if redis_slave_failure_cnt > FAILURE_CNT then
		    nginx_local_cache:set("redis_slave_downgrade", "true")
		    nginx_local_cache:set("start_redis_slave_downgrade_time", os.time())
		end

        return close_redis(red)
	end
    -- 通过Twemproxy从Redis从集群获取数据
    return red:get("dim_product_"..product_id)
end

local function access_redis_master()
	local ok, err, red = connect_redis((REDIS_MASTER_IP, REDIS_MASTER_PORT)
    local redis_resp, redis_err = red:get("dim_product_"..product_id)
	product_cache = redis_resp
end

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

if product_cache == "" or product_cache == nil then
    -- 获取从集群降级标识
    local redis_slave_downgrade = nginx_local_cache:get("redis_slave_downgrade")

    -- 如果要进行从集群降级，访问数据直连服务
    if redis_slave_downgrade == "true" then
        -- 获取数据直连服务降级标识
        local data_link_downgrade = nginx_local_cache:get("data_link_downgrade")

        -- 如果要进行数据直连服务降级，访问Redis主集群
		if data_link_downgrade == true then
	        access_redis_master
	        try_upgrade_datalink_service
		else
			access_datalink_service
			try_upgrade_redis_slave
		end
    else
        local redis_resp, redis_err = access_redis_slave
	  
	    if redis_resp == ngx.null or redis_resp == "" or redis_resp == nil then
	        local data_link_downgrade = nginx_local_cache:get("data_link_downgrade")

		    if data_link_downgrade == "true" then
	            access_redis_master
		        try_upgrade_datalink_service
		    else
			    access_datalink_service
	    	end
        else
		    product_cache = redis_resp
	    end
    end

    math.randomseed(tostring(os.time()):reverse():sub(1, 7))
    local expire+_time = math.random(600, 1200)
    -- 更新Nginx本地Cache
    nginx_local_cache:set(product_cache_key, product_cache, expire+_time)
end

local context = {
    product_info = product_cache,
}

-- 渲染HTML
local template = require("resty.template")
template.render("product.html", context)