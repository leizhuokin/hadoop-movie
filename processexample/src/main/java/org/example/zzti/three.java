package org.example.zzti;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;

public class three {
    /*总评分人数sum*/
    static long sum = 0;
    /*影片个数*/
    static int count = 0;
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(three.class);
        job.setMapperClass(three.Top8Mapper.class);
        job.setReducerClass(three.Top8Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/three"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    static class Top8Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        /*
         * map
         * name影片名、score评分、scorenum评分人数
         * 通过“,”合并name和score 【key】，将scorenum作为【value】
         * 计算出平均评分人数
         * 筛选出大于平均数的【key】
         * */

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] line = value.toString().trim().split(",");

            if(!"scorenum".equals(line[4])){
                //System.out.println( Long.parseLong(line[4]));
                sum += Float.parseFloat(line[4].toString());
                //System.out.println( Long.parseLong(line[4]));
                count++;
                context.write(new Text(line[1]+","+line[3]),new IntWritable(Integer.parseInt(line[4])));  //每一行的评分人数
            }

        }

    }

    /*
     * reduce
     * 传过来的key进行拆分，通过“,”
     * 通过score，比较出top20
     * */
    static class Top8Reducer extends Reducer<Text,IntWritable,Text,FloatWritable> {
        Map<String ,Float> map = null;
        /*平均评分人数*/
        long avg = 0;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            map = new HashMap<String ,Float>();
            avg = sum / count;
        }

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            if(avg<=values.iterator().next().get()){
                String[] split = key.toString().split(",");
                map.put(split[0],Float.parseFloat(split[1]));
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException,IllegalArgumentException{
            List<Map.Entry<String,Float>> list = new ArrayList<Map.Entry<String,Float>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
                @Override
                public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                    int sort = (int) (o2.getValue()-o1.getValue());
                    return sort;
                }
            });
            for (int i = 0; i < 8; i++) {
                context.write(new Text(list.get(i).getKey()),new FloatWritable(list.get(i).getValue()));
            }

        }
    }
}
