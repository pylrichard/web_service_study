package com.bd.java.db;

import java.util.*;

public class ArrayListLeftJoinImpl {
    public static void main(String[] args) {
        //要关联的左表
        DataDto dataDto1 = new DataDto(1L, "1");
        DataDto dataDto3 = new DataDto(3L, "3");
        DataDto dataDto6 = new DataDto(6L, "6");
        DataDto[] dataDtoArray = new DataDto[]{dataDto1, dataDto3, dataDto6};
        List<DataDto> dataDtoList = Arrays.asList(dataDtoArray);
        //转换左表为<关联列, 数据>形式
        Map<Long, DataDto> dataDtoMap = new HashMap<>();
        for (int i = 0; i < dataDtoList.size(); i++) {
            DataDto dataDto = dataDtoList.get(i);
            dataDtoMap.put(dataDto.getId(), dataDto);
        }
        //要关联的右表关联列
        Long[] idArray = new Long[]{1L, 2L, 3L, 4L, 5L, 6L};
        List<Long> idList = Arrays.asList(idArray);
        List<String> retVal = new ArrayList<>();
        for (Long id : idList) {
            //根据关联列获取数据
            DataDto dataDto = dataDtoMap.get(id);
            if (dataDto == null) {
                //左表关联列没有值的行用-填充
                retVal.add("-");
            } else {
                retVal.add(dataDto.getValue());
            }
        }
        System.out.println(retVal);
    }
}
