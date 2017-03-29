package sample.spark;

import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.SQLContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eclair on 29/03/2017.
 */
public class SimpleTest implements Serializable {
  static JavaSparkContext sc;
  @BeforeClass
  public static void initContext(){

    SparkConf conf = new SparkConf();
    conf.setMaster("local[2]");
    conf.setAppName("simple spark rdd test");
    sc = new JavaSparkContext(conf);
    sc.setLogLevel("WARN");
  }

  @Test
  public void createTest(){
    List<Integer> list = new ArrayList<Integer>();
    list.add(1);
    list.add(2);
    list.add(3);
    // SQLContext.getOrCreate(sc.sc()).read().json("pr.json");
    JavaRDD rdd = sc.parallelize(list);
    rdd = rdd.map(new Function() {
      public Object call(Object v1) throws Exception {
        return new Integer[]{(Integer)v1 * 10, 1};
//        return (Integer)v1 * 10;
      }
    });
    Integer[] result = (Integer[]) rdd.reduce(new Function2() {
      public Object call(Object v1, Object v2) throws Exception {
        Integer[] v1a = (Integer[])v1;
        Integer[] v2a = (Integer[])v2;
        return new Integer[]{v1a[0] + v2a[0], v1a[1] + v2a[1]};
      }
    });

    System.out.println("avg === " + result[0] / result[1]);
    System.out.println(rdd.collect());

  }
}
