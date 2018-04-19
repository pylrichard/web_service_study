package com.bd.java.db;

import java.util.*;

public class ArrayListGroupByImpl {
    public static void main(String[] args) {
        DataDto dataDto1 = new DataDto(1L, "1.1");
        DataDto dataDto2 = new DataDto(1L, "1.2");
        DataDto dataDto3 = new DataDto(2L, "2.1");
        DataDto dataDto4 = new DataDto(2L, "2.2");
        DataDto dataDto5 = new DataDto(2L, "2.3");
        DataDto dataDto6 = new DataDto(3L, "3");
        DataDto[] dataDtoArray = new DataDto[]{dataDto1, dataDto2, dataDto3,
                dataDto4, dataDto5, dataDto6};
        List<DataDto> dataDtoList = Arrays.asList(dataDtoArray);
        //key是分组列，value是分组行结果集
        Map<Long, List<DataDto>> dataDtoMap = new HashMap<>();
        for (DataDto dataDto : dataDtoList) {
            Long id = dataDto.getId();
            List<DataDto> mapValue = dataDtoMap.get(id);
            if (mapValue == null) {
                //分组列对应还没有行则新建
                mapValue = new ArrayList<>();
                mapValue.add(dataDto);
                dataDtoMap.put(id, mapValue);
            } else {
                //存储行到结果集
                mapValue.add(dataDto);
            }
        }
        for (Long id : dataDtoMap.keySet()) {
            Iterator iterator = dataDtoMap.get(id).iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next().toString());
            }
            System.out.println();
        }
    }
}
