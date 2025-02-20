package com.example.codilitychallenges;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PICodeChallengeV2 {

    public static int solution(String P, String Q) {
        if (Objects.equals(P, Q)) {
            Set<Character> uniqueLetters = P.chars()
                    .mapToObj(c -> (char) c) // Convert int to Character
                    .collect(Collectors.toSet());
            return uniqueLetters.size();
        }
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : (P + Q).toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        freqMap = freqMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);

        int charsToConsider = 0;
        int counter = 0;
        for (Integer count : freqMap.values()) {
            if (counter >= P.length()) {
                break;
            }
            counter += count;
            charsToConsider++;
        }

        Character [] charsToConsiderArray = new Character[charsToConsider];

        int index = 0;
        for (Character c : freqMap.keySet()) {
            if (index >= charsToConsider) {
                break;
            }
            charsToConsiderArray[index] = c;
            index++;
        }

        List<Character> characterLeft = freqMap.keySet()
                .stream()
                .filter(e1 -> Arrays.stream(charsToConsiderArray).noneMatch(e2 -> e2 == e1)).collect(Collectors.toList());


        Character [] charsToConsiderArrayReversed = Arrays.stream(charsToConsiderArray).sorted(Comparator.reverseOrder()).distinct().toArray(Character[]::new);

        Character [] result = new Character[P.length()];
        Character [] resultWithReversed = new Character[P.length()];

        // filled positions in result
        Set<Integer> filledPositions = new HashSet<>();
        Set<Integer> filledPositionsForReversed = new HashSet<>();
        for (int i = 0; i < charsToConsider; i++) {
            for (int j = 0; j < P.length(); j++) {
                if ((charsToConsiderArray[i] == P.charAt(j) || charsToConsiderArray[i] == Q.charAt(j)) && (!filledPositions.contains(j))) {
                    result[j] = charsToConsiderArray[i];
                    filledPositions.add(j);
                }
                if ((charsToConsiderArrayReversed[i] == P.charAt(j) || charsToConsiderArrayReversed[i] == Q.charAt(j)) && (!filledPositionsForReversed.contains(j))) {
                    resultWithReversed[j] = charsToConsiderArrayReversed[i];
                    filledPositionsForReversed.add(j);
                }
            }
        }
        for (int i = 0; i < P.length(); i++) {
            if (result[i] == null) {
                for (Character c : characterLeft) {
                    if (c.equals(P.charAt(i)) || c.equals(Q.charAt(i))) {
                        result[i] = c;
                        resultWithReversed[i] = c;
                    }
                }
            }
        }



        int size = Arrays.stream(result).filter(Objects::nonNull).collect(Collectors.toSet()).size();
        int sizeOfReversed = Arrays.stream(resultWithReversed).filter(Objects::nonNull).collect(Collectors.toSet()).size();
        return Math.min(size, sizeOfReversed);
    }

    // bacad abada
    // hsuledusla jfudisoala
    // axxz yzwy
    // axxz wzyy
    // "adabca", "cbdcdb"
    // "dcba" "cbad"
    // "ad" "bc"
    // acb cba
    // awwa bbwx
    public static void main(String[] args) {
        System.out.println(solution("dcba", "cbad"));// ccaa
    }
}
