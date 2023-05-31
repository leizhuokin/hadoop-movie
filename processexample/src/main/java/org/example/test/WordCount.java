package org.example.test;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import java.io.IOException;
import java.util.Iterator;

public class WordCount {
    public static void main(String[] args) throws Exception {
        //运行作业代码
        //配置信息
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        JobConf conf = new JobConf(WordCount.class);
        conf.setJobName("wordcount");
        conf.setOutputKeyClass(Text.class); //指定最终的输出key
        conf.setOutputValueClass(IntWritable.class); //指定最终的输出value
        conf.setMapperClass(Map.class); //指定该job的map处理类

        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        // 文件路径信息
        FileInputFormat.setInputPaths(conf, new Path("hdfs://192.168.100.101:9000/words/"));
        FileOutputFormat.setOutputPath(conf, new Path("/output/result"));
        // 执行
        JobClient.runJob(conf);
    }

    //1.map操作
    public static class Map extends MapReduceBase implements
            Mapper<LongWritable, Text, Text, IntWritable> {

        /**
         * Map处理数据
         * Map输入类型<key,value>--><longWritable,text>
         * Map输出类型为(单词，出现次数) -> (Text,InWritable)
         *
         * @param longWritable
         * @param text
         * @param outputCollector
         * @param reporter
         * @throws IOException
         */

        //每个单词出现的次数初始值为1
        private final static IntWritable one = new IntWritable(1);
        // 解析出来每个单词用Text类型存储
        private Text words = new Text();

        @Override
        public void map(LongWritable key, Text value,
                        OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

            String[] line = value.toString().split(",");
            for (String word : line) {
                words.set(word);
                output.collect(words, one);
            }

        }
    }

    //2.规约操作
    public static class Reduce extends MapReduceBase implements
            Reducer<Text, IntWritable, Text, IntWritable> {

        /**
         * Reduce处理逻辑
         * Reduce输入类型<Text,IntWritable>
         * Reduce输出类型<Text,IntWritable>
         *
         * @param key
         * @param values
         * @param output
         * @param reporter
         * @throws IOException
         */
        @Override
        public void reduce(Text key, Iterator<IntWritable> values,
                           OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

            //求和统计英语单词个数
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));

        }
    }
}
