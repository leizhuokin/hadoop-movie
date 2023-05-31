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
import org.example.model.Movie;

import java.io.IOException;
import java.util.*;

public class one {
    public static void main(String[] args) throws Exception {
        String hadoopHome = "D:\\hadoop\\hadoop-3.2.2";
        System.setProperty("hadoop.home.dir", hadoopHome);
        System.load(hadoopHome + "\\bin\\hadoop.dll");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MyJob");
        job.setJarByClass(one.class);
        job.setMapperClass(one.WEBMovieMapper.class);
        job.setReducerClass(one.WEBMovieReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Movie.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://192.168.100.101:9000/zzti/project/douban"));
        FileOutputFormat.setOutputPath(job, new Path("/zzti/result/one"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public class WEBMovieMapper extends Mapper<LongWritable, Text, Text, Movie> {
        private Text text = new Text();
        private Movie movie = new Movie();
        private Double maxGrade = -1.0;
        Movie movieMax = new Movie();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 1获取一行转为String
            String line = value.toString();
            //2 按照逗号分割
            String[] csvComments = line.split(",");
            //3 获取需要的值
            String id = csvComments[0];
            String name = csvComments[1];
            String year = csvComments[2];
            String grade = csvComments[3];
            String number = csvComments[4];
            String type = csvComments[5];
            String country = csvComments[6];
            movie.setId(Integer.valueOf(id));
            movie.setName(name);
            movie.setYear(Integer.valueOf(year));
            movie.setGrade(Double.valueOf(grade));
            movie.setNumber(Integer.valueOf(number));
            movie.setType(type);
            movie.setCountry(country);
            text.set(country);
            context.write(text, movie);
        }
    }


    public class WEBMovieReduce extends Reducer<Text, Movie, String, Double> {
        Map<String, Double> movieMap = new HashMap<>();
        double max = -1;
        private Movie movies = null;
        Double grade = 0.0;

        protected void reduce(Text key, Iterable<Movie> values, Context context) throws IOException, InterruptedException {
            Movie m = new Movie();
            for (Movie va : values
            ) {
                if (va.getGrade() > max) {
                    max = va.getGrade();
                    movies = va;
                    grade = max;
                }
            }

            movieMap.put(key.toString(), grade);
            max = -1;
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            String maxKey = "";
            Double maxValue = 0.0;
            Double max = 0.0;

            for (Map.Entry<String, Double> m : movieMap.entrySet()
            ) {
                if (m.getValue() > max) {
                    maxKey = m.getKey();
                    maxValue = m.getValue();
                    max = m.getValue();
                }
                //  context.write(m.getKey(),m.getValue());
            }
            List<Map.Entry<String, Double>> entrys = new ArrayList<Map.Entry<String, Double>>(movieMap.entrySet());
            Collections.sort(entrys, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return o1.getValue() > o2.getValue() ? -1 : 1;
                }
            });
            for (Map.Entry<String, Double> str : entrys
            ) {
                System.out.println(str.getKey() + "---------------" + str.getValue());
            }
            for (int i = 0; i < 10; i++) {
                Map.Entry<String, Double> movieTop = entrys.get(i);
                context.write(movieTop.getKey(), movieTop.getValue());
            }


            //context.write("最大值在下面",0.0);
            //context.write(maxKey,maxValue);
        }
    }
}
