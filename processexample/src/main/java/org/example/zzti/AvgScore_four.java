package org.example.zzti;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

public class AvgScore_four {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(AvgScore_four.class);
        job.setMapperClass(AvgScore_four.MyMapper.class);
        job.setReducerClass(AvgScore_four.MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/AvgScore_four"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
        public void map(
                LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] toks = value.toString().trim().split(",");
            String[] country = toks[6].trim().split("/");
            int year = Integer.parseInt(toks[2]);
            double sum = 0;
            int nowyear = 2022;
            Map<String, Double> yaerCount = new IdentityHashMap<>();
            if (country[0].equals("中国大陆") || country[0].equals("香港") || country[0].equals("台湾")) {
                for (int i = 0; i < 100; i++) {
                    if (year <= nowyear && year > nowyear - 10) {
                        sum = Double.parseDouble(String.valueOf(toks[3]));
                        yaerCount.put(((nowyear - 10) + "-" + nowyear), sum);
                    }
                    nowyear -= 10;
                }
            }
            for (Map.Entry<String, Double> entry : yaerCount.entrySet()) {
                context.write(new Text(entry.getKey()), new Text(String.valueOf(entry.getValue())));
            }
        }
    }

    private static class MyReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(
                Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            double sum = 0;
            for (Text value : values) {
                count++;
                sum += Double.parseDouble(String.valueOf(value));
            }
            double avgScore = sum / count;
            context.write(key, new Text(avgScore + ""));
        }
    }
}
