package org.example.zzti;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import org.apache.hadoop.fs.Path;

import java.io.IOException;

import java.util.Iterator;

public class two {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        JobConf conf = new JobConf(two.class);
        conf.setJobName("more");
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(DoubleWritable.class);
        conf.setOutputKeyClass(Text.class); //指定最终的输出key
        conf.setOutputValueClass(IntWritable.class); //指定最终的输出value
        conf.setMapperClass(Map.class); //指定该job的map处理类

        // conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.addInputPath(conf, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(conf, new Path("/zzti/result/two"));
        JobClient.runJob(conf);
    }
    //1.map操作
    public static class Map extends MapReduceBase implements
            Mapper<LongWritable, Text, Text, DoubleWritable> {
        //每个单词出现的次数初始值为1

        @Override
        public void map(LongWritable key, Text value,
                        OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
            String[] toks = value.toString().split(",");
            if(!"id".equals(toks[0])){
                if(Double.parseDouble(toks[3])>8.5){
                    output.collect(new Text(toks[5].substring(0,2)), new DoubleWritable(1));
                    System.out.println(toks[5].substring(0,2));
                }}
        }
    }

    public static class Reduce extends MapReduceBase implements
            Reducer<Text, DoubleWritable, Text, IntWritable> {
        int count=0;
        @Override
        public void reduce(Text key, Iterator<DoubleWritable> values,
                           OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            while (values.hasNext()) {
                count++;
                values.next();
            }
            output.collect(key, new IntWritable(count));
            count=0;
        }
    }
}
