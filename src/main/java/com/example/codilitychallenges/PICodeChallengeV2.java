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

    // bacad abada
    // hsuledusla jfudisoala
    // axxz yzwy
    // axxz yzwy
    // "adabca", "cbdcdb"

    public static int solution(String P, String Q) {
        if (Objects.equals(P, Q)) {
            Set<Character> uniqueLetters = P.chars()
                    .mapToObj(c -> (char) c) // Convert int to Character
                    .collect(Collectors.toSet());
            return uniqueLetters.size();
        }
        Map<Character, Integer> chars = new HashMap<>();
        for (int i = 0; i < P.length(); i++) {
            chars.compute(P.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }
        for (int i = 0; i < Q.length(); i++) {
            chars.compute(Q.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }
        chars = chars.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);

        int charsToConsider = 0;
        int counter = 0;
        for (Integer count : chars.values()) {
            if (counter >= P.length()) {
                break;
            }
            counter += count;
            charsToConsider++;
        }

        Character [] charsToConsiderArray = new Character[charsToConsider];

        int index = 0;
        for (Character c : chars.keySet()) {
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

    public static void main(String[] args) {
        System.out.println(solution("dcba", "cbad"));// ccaa
    }
}
