package org.example.zzti;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.example.model.DouBanLog;
import org.example.utils.Commons;

import java.io.IOException;
import java.util.*;

public class six {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(six.class);
        job.setMapperClass(six.YearMapper.class);
        job.setReducerClass(six.YearReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/six"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public class YearMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        DouBanLog doubanlog = new DouBanLog();
        Text year = new Text();
        IntWritable one = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            doubanlog = Commons.PageDouBanLog(value.toString());
            year.set(doubanlog.getYear());
            context.write(year, one);
        }
    }

    public class YearReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        //private static final int COUNT = 10;
        Map<String, Integer> map = new HashMap<String, Integer>();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            String year = key.toString();
            map.put(year, sum);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //map按照评论次数排序
            List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (int) (o2.getValue() - o1.getValue());
                }
            });

            for (int i = 0; i < 10; i++) {
                context.write(new Text(list.get(i).getKey()), new IntWritable(list.get(i).getValue()));
            }


        }
    }
}
