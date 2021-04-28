package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayTools {

    public static List<Integer> generateArray(int size) { // 16
          
       List<Integer> resultingList = Stream.concat(
               IntStream.range(1, size / 2 +1 ).boxed(),
               IntStream.range(1, size / 2 +1 ).boxed())
               .collect(Collectors.toList());


       Collections.shuffle(resultingList);
     

       return resultingList;
        

    }


}
