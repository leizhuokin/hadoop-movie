package org.example.compare;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Comparablekey implements WritableComparable<Comparablekey> {
    //第一个数
    private long firstNum;
    //第二个数
    private long secondNum;

    public Comparablekey(){


    }
    public Comparablekey(long firstNum, long secondNum) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
    }

    public long getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(long firstNum) {
        this.firstNum = firstNum;
    }

    public long getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(long secondNum) {
        this.secondNum = secondNum;
    }

    /**
     * 重点(如何定义比较规则)
     * @param o
     * @return
     */
    @Override
    public int compareTo(Comparablekey o) {
        // 如果第一个数相同，思考从小到大还是从大到小排列
        if(firstNum == o.getFirstNum()){
            return (int)(secondNum - o.getSecondNum());
        }else{
            return (int)(firstNum - o.getFirstNum());
        }
    }
    // 序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(firstNum);
        dataOutput.writeLong(secondNum);
    }
    //反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        firstNum = dataInput.readLong();
        secondNum = dataInput.readLong();
    }
}
