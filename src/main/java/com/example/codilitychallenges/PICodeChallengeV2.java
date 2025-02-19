package com.example.codilitychallenges;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

        Character [] result = new Character[P.length()];
        Arrays.fill(result, '$');

        // filled positions in result
        Set<Integer> filledPositions = new HashSet<>();
        for (int i = 0; i < charsToConsider; i++) {
            for (int j = 0; j < P.length(); j++) {
                if ((charsToConsiderArray[i] == P.charAt(j) || charsToConsiderArray[i] == Q.charAt(j)) && (!filledPositions.contains(j))) {
                    result[j] = charsToConsiderArray[i];
                    filledPositions.add(j);
                }
            }
        }
        return Arrays.stream(result).collect(Collectors.toSet()).size();
    }

    // bacad abada
    // hsuledusla jfudisoala
    // axxz yzwy
    // axxz yzwy
    // "adabca", "cbdcdb"
    public static void main(String[] args) {
        System.out.println(solution("adabca", "cbdcdb"));// ccaa
    }
}
