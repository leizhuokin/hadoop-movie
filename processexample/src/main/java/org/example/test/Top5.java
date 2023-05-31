package org.example.test;

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

import java.io.IOException;

public class Top5 {
    public static final int K = 5;

    public static class MyIntWritable extends IntWritable {
        public MyIntWritable() {
        }

        public MyIntWritable(int value) {
            super(value);
        }

        @Override
        public int compareTo(IntWritable o) {
            return -super.compareTo(o);  //重写IntWritable排序方法，默认是升序 ，
        }
    }

    public static void main(String[] args) throws Exception {
        //运行作业代码
        //配置信息
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(Top5.class);
        job.setMapperClass(Top5.MyMapper.class);
        job.setReducerClass(Top5.MyReducer.class);
        job.setOutputKeyClass(MyIntWritable.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/example/Top5/"));
        FileOutputFormat.setOutputPath(job, new Path("/output/Top5"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static class MyMapper extends Mapper<LongWritable, Text, MyIntWritable, Text> {
        public void map(
                LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] toks = value.toString().trim().split(",");
            int score = Integer.parseInt(toks[1]);
            context.write(new MyIntWritable(score), new Text(toks[0]));
        }
    }

    private static class MyReducer extends Reducer<MyIntWritable, Text, Text, MyIntWritable> {
        int num = 0;

        @Override
        protected void reduce(MyIntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            for (Text text : values) {
                if (num < K) {
                    context.write(text, key);
                }
                num++;
            }
        }
    }
}
