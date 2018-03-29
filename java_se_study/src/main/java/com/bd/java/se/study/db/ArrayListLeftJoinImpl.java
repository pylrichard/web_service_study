package com.bd.java.se.study.db;

import java.util.*;

public class ArrayListLeftJoinImpl {
    public static void main(String[] args) {
        //关联的左表
        DataDto dataDto1 = new DataDto(1L, "1");
        DataDto dataDto3 = new DataDto(3L, "3");
        DataDto dataDto6 = new DataDto(6L, "6");
        DataDto[] dataDtoArray = new DataDto[]{dataDto1, dataDto3, dataDto6};
        List<DataDto> dataDtoList = Arrays.asList(dataDtoArray);
        //利用HashMap快速定位元素的特性
        Map<Long, DataDto> dataDtoMap = new HashMap<>();
        for (int i = 0; i < dataDtoList.size(); i++) {
            DataDto dataDto = dataDtoList.get(i);
            dataDtoMap.put(dataDto.getId(), dataDto);
        }
        //关联的右表
        Long[] idArray = new Long[]{1L, 2L, 3L, 4L, 5L, 6L};
        List<Long> idList = Arrays.asList(idArray);
        //返回的结果集
        List<String> retVal = new ArrayList<>();
        for (Long id : idList) {
            DataDto dataDto = dataDtoMap.get(id);
            if (dataDto == null) {
                //左表的id在右表中没有对应值
                retVal.add("-");
            } else {
                retVal.add(dataDto.getValue());
            }
        }
        System.out.println(retVal);
    }
}
