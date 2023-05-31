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

public class Optimum_five {

    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(Optimum_five.class);
        job.setMapperClass(Optimum_five.MyMapper.class);
        job.setReducerClass(Optimum_five.MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/zzti/project/piaofang"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/Optimum_five"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
        public void map(
                LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] toks = value.toString().trim().split(",");
            int year = Integer.parseInt(toks[2]);
            int nowyear = 2022;
            Map<String, String> yaerCount = new IdentityHashMap<>();
            for (int i = 0; i < 100; i++) {
                if (year <= nowyear && year > nowyear - 5) {
                    yaerCount.put(((nowyear - 5) + "-" + nowyear), toks[5].substring(0, toks[5].length() - 1)
                            + "\t" + toks[1] + "\t" + toks[4]);
                }
                nowyear -= 5;
            }
            for (Map.Entry<String, String> entry : yaerCount.entrySet()) {
                context.write(new Text(entry.getKey()), new Text(String.valueOf(entry.getValue())));
            }
        }
    }

    private static class MyReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(
                Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            long max = 0;
            String c = "";
            for (Text value : values) {
                String[] a = value.toString().trim().split("\t");
                long b = Long.parseLong(a[0]);
                if (max < b) {
                    max = b;
                    c = max + ":" + a[1] + "," + a[2];
                }
            }
            context.write(key, new Text(c));
        }
    }
}
