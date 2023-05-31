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
import java.util.*;

public class eight {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        //配置信息
        Configuration conf = new Configuration();
        // conf.set("mapreduce.app-submission.cross-platform", "true");
        //conf.set("yarn.app.mapreduce.am.resource.mb", "256");
        Job job = Job.getInstance(conf, "Top10");
        job.setMapperClass(MovieMapper.class);
        job.setReducerClass(MovieReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.100.101:9000/zzti/project/piaofang"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/eight"));
        job.waitForCompletion(true);

    }
    static class MovieMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        /*
         * map,拼接电影与导演名字，提取票房数字
         *
         * */
        int i = 1;
        LongWritable vote = new LongWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            i--;
            if (i < 0) {


                String[] toks = value.toString().trim().split(",");
                System.out.println(toks.length);
                if(toks.length==6) {
                    vote.set(Long.parseLong(toks[5].substring(0, toks[5].indexOf("$"))));

                    context.write(new Text(toks[1] + toks[4]), new LongWritable(vote.get()));  //每一行的评分人数
                }
            }
        }

    }

    /*
     * reduce
     * 对value票房数进行排序，挑选出top4

     * */
    static class MovieReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        Map<String, Long> map = new HashMap<String, Long>();


        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long vote_number = 0;
            for (LongWritable value : values) {
                vote_number = value.get();
            }
            String year = key.toString();
            map.put(year, vote_number);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //map按照评论次数排序
            List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
                @Override
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                    return (int) (o2.getValue() - o1.getValue());
                }
            });
            for (int i = 0; i < 4; i++) {
                context.write(new Text(list.get(i).getKey()), new LongWritable(list.get(i).getValue()));
            }

        }
    }
}
