package com.bd.roncoo.eshop.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WordCountTopology {
    public static class RandomSentenceSpout extends BaseRichSpout {
        private static final long serialVersionUID = 3699352201538354417L;
        private static final Logger logger = LoggerFactory.getLogger(RandomSentenceSpout.class);
        private SpoutOutputCollector collector;
        private Random random;

        /**
         * 对Spout进行初始化
         * 比如创建一个线程池或者创建一个数据库连接池或者构造一个HttpClient
         */
        @Override
        @SuppressWarnings("rawtypes")
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            //SpoutOutputCollector发送数据
            this.collector = collector;
            this.random = new Random();
        }

        /**
         * Spout会运行在某个Worker进程的某个Executor线程内的某个Task
         * Task循环调用nextTuple()发送数据，形成一个数据流
         */
        @Override
        public void nextTuple() {
            Utils.sleep(100);
            String[] sentences = new String[]{"the cow jumped over the moon",
                    "an apple a day keeps the doctor away",
                    "four score and seven years ago",
                    "snow white and the seven dwarfs",
                    "i am at two with nature"};
            String sentence = sentences[random.nextInt(sentences.length)];
            logger.info("sentence=" + sentence);
            //Values构建一个Tuple，Tuple是最小的数据单位，多个Tuple组成一个Stream
            collector.emit(new Values(sentence));
        }

        /**
         * 定义每个Tuple的每个Field的名称
         */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("sentence"));
        }
    }

    public static class SplitSentence extends BaseRichBolt {
        private static final long serialVersionUID = 6604009953652729483L;
        private OutputCollector collector;

        /**
         * Bolt的第一个方法是prepare()
         * OutputCollector发送Bolt的Tuple
         */
        @Override
        @SuppressWarnings("rawtypes")
        public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        /**
         * 每次接收到一条数据后交给execute()执行
         */
        @Override
        public void execute(Tuple tuple) {
            String sentence = tuple.getStringByField("sentence");
            String[] words = sentence.split(" ");
            for (String word : words) {
                collector.emit(new Values(word));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }

    public static class WordCount extends BaseRichBolt {
        private static final long serialVersionUID = 7208077706057284643L;
        private static final Logger logger = LoggerFactory.getLogger(WordCount.class);
        private OutputCollector collector;
        private Map<String, Long> wordCounts = new HashMap<>();

        @Override
        @SuppressWarnings("rawtypes")
        public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple tuple) {
            String word = tuple.getStringByField("word");
            Long count = wordCounts.get(word);
            if (count == null) {
                count = 0L;
            }
            count++;
            wordCounts.put(word, count);
            logger.info("单词计数" + word + "出现的次数是" + count);
            collector.emit(new Values(word, count));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }

    public static void main(String[] args) {
        //将Spout和Bolt组合构建成一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //第1个参数给Spout设置名字
        //第2个参数创建一个Spout对象
        //第3个参数设置Spout的Executor数量
        builder.setSpout("RandomSentence", new RandomSentenceSpout(), 2);
        builder.setBolt("SplitSentence", new SplitSentence(), 5)
                .setNumTasks(10)
                .shuffleGrouping("RandomSentence");
        /*
            相同的单词从SplitSentence发送后会进入到下游同一个Task
		    这样才能准确统计出每个单词的数量
        */
        builder.setBolt("WordCount", new WordCount(), 10)
                .setNumTasks(20)
                .fieldsGrouping("SplitSentence", new Fields("word"));
        Config config = new Config();
        if (args != null && args.length > 0) {
            config.setNumWorkers(3);
            try {
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            config.setMaxTaskParallelism(20);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("WordCountTopology", config, builder.createTopology());
            Utils.sleep(60000);
            cluster.shutdown();
        }
    }
}
