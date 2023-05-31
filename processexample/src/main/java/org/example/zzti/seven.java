package org.example.zzti;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;

public class seven {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        //配置信息
        JobConf conf = new JobConf(seven.class);
        conf.setJobName("more");
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(DoubleWritable.class);
        conf.setOutputKeyClass(Text.class); //指定最终的输出key
        conf.setOutputValueClass(DoubleWritable.class); //指定最终的输出value
        conf.setMapperClass(Map.class); //指定该job的map处理类

        // conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        // 文件路径信息
        FileInputFormat.setInputPaths(conf, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(conf, new Path("/zzti/result/seven"));
        // 执行
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
            //one = Integer.parseInt(toks[1].toString());
            if (toks[6].toString().contains("中国")) {
                System.out.println(toks[3].toString());
                output.collect(new Text(toks[2].toString() + "中国"), new DoubleWritable(Double.parseDouble(toks[3].toString().trim())));
            }
            if (toks[6].toString().contains("美国")) {
                output.collect(new Text(toks[2].toString() + "美国"), new DoubleWritable(Double.parseDouble(toks[3].toString().trim())));

            }

        }
    }

    public static class Reduce extends MapReduceBase implements
            Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        double one;
        @Override
        public void reduce(Text key, Iterator<DoubleWritable> values,
                           OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
            Double d=0.0;
            int count=0;
            while (values.hasNext()) {
                d+=values.next().get();
                count++;
            }
            one= d/count;
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(2);
            String  str = format.format(one);
            double two = Double.parseDouble(str);
            output.collect(key, new DoubleWritable((two)));
            System.out.println(key.toString()+two+"");

        }
    }

}
