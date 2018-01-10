package com.bd.roncoo.eshop.storm;

import com.bd.roncoo.eshop.storm.bolt.LogParseBolt;
import com.bd.roncoo.eshop.storm.bolt.ProductCountBolt;
import com.bd.roncoo.eshop.storm.spout.AccessLogKafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

/**
 * 热数据统计拓扑
 *
 * 提交jar到Storm执行
 * storm jar eshop_storm-0.1.0.jar com.bd.roncoo.eshop.storm.HotProductTopology HotProductTopology
 *
 * storm kill HotProductTopology
 */
public class HotProductTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("AccessLogKafkaSpout", new AccessLogKafkaSpout(), 1);
        builder.setBolt("LogParseBolt", new LogParseBolt(), 2)
                .setNumTasks(2)
                .shuffleGrouping("AccessLogKafkaSpout");
        builder.setBolt("ProductCountBolt", new ProductCountBolt(), 2)
                .setNumTasks(2)
                //根据商品id进行分组
                .fieldsGrouping("LogParseBolt", new Fields("productId"));
        Config config = new Config();
        if (args != null && args.length > 0) {
            config.setNumWorkers(3);
            try {
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            /*
                本地模式启动
             */
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("HotProductTopology", config, builder.createTopology());
            Utils.sleep(30000);
            cluster.shutdown();
        }
    }
}
