package com.example.codilitychallenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
        Map<Character, CharCountAndPosition> freqMap = new HashMap<>();
        char[] pCharArray = P.toCharArray();
        for (int i = 0; i < P.length(); i++) {
            CharCountAndPosition countAndPosition = freqMap.getOrDefault(pCharArray[i], new CharCountAndPosition());
            countAndPosition.setCount(countAndPosition.getCount() + 1);
            countAndPosition.getPositions().add(i);
            freqMap.put(pCharArray[i], countAndPosition);
        }

        char[] qCharArray = Q.toCharArray();
        for (int i = 0; i < Q.length(); i++) {
            CharCountAndPosition countAndPosition = freqMap.getOrDefault(qCharArray[i], new CharCountAndPosition());
            countAndPosition.setCount(countAndPosition.getCount() + 1);
            countAndPosition.getPositions().add(i);
            freqMap.put(qCharArray[i], countAndPosition);
        }

        List<CharPair> charPairs = new ArrayList<>();
        for (Map.Entry<Character, CharCountAndPosition> positionEntry : freqMap.entrySet()) {
            if (positionEntry.getValue().getCount() == P.length()) {
                // all positions are filled with single characters
                return 1;
            }
            for (Map.Entry<Character, CharCountAndPosition> characterCharCountAndPositionEntry : freqMap.entrySet()) {
                Character key1 = positionEntry.getKey();
                CharCountAndPosition value1 = positionEntry.getValue();
                Character key2 = characterCharCountAndPositionEntry.getKey();
                CharCountAndPosition value2 = characterCharCountAndPositionEntry.getValue();

                if (!(charPairs.contains(new CharPair(key1, key2, value1.getCount(), value2.getCount())) || charPairs.contains(new CharPair(key2, key1, value2.getCount(), value1.getCount()))) &&
                        value1.getCount() > 1 && value2.getCount() > 1 && Collections.disjoint(value1.getPositions(), value2.getPositions())) {
                    charPairs.add(new CharPair(key1, key2, value1.getCount(), value2.getCount()));
                }
            }
        }

        if (charPairs.isEmpty()) {
            Set<Map.Entry<Character, CharCountAndPosition>> entries = freqMap.entrySet();
            for (Map.Entry<Character, CharCountAndPosition> entry : entries) {
                charPairs.add(new CharPair(entry.getKey(), entry.getKey(), entry.getValue().getCount(), entry.getValue().getCount()));
            }
        }
        charPairs.sort(Comparator.comparingInt(CharPair::getFirstCount).reversed());

        // filled positions in result
        Set<Integer> filledPositions = new HashSet<>();
        Character [] result = new Character[P.length()];
        for (CharPair pair : charPairs) {
            Character first = pair.getFirst();
            Character second = pair.getSecond();
            for (int j = 0; j < P.length(); j++) {
                if ((first ==  P.charAt(j) || first == Q.charAt(j)) && (!filledPositions.contains(j))) {
                    result[j] = first;
                    filledPositions.add(j);
                }
                if ((second == P.charAt(j) || second == Q.charAt(j)) && (!filledPositions.contains(j))) {
                    result[j] = second;
                    filledPositions.add(j);
                }
            }
        }

        return Arrays.stream(result).filter(Objects::nonNull).collect(Collectors.toSet()).size();
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
        System.out.println(solution("abcdef", "bcdefa"));// ccaa
    }
    private static class CharPair {
        Character first;
        Character second;

        int firstCount;
        int secondCount;

        public CharPair(char first, char second, int firstCount, int secondCount) {
            this.first = first;
            this.second = second;
            this.firstCount = firstCount;
            this.secondCount = secondCount;
        }

        public Character getFirst() {
            return first;
        }

        public Character getSecond() {
            return second;
        }

        public int getFirstCount() {
            return firstCount;
        }

        public void setFirstCount(int firstCount) {
            this.firstCount = firstCount;
        }

        public int getSecondCount() {
            return secondCount;
        }

        public void setSecondCount(int secondCount) {
            this.secondCount = secondCount;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            CharPair charPair = (CharPair) o;
            return Objects.equals(first, charPair.first) && Objects.equals(second, charPair.second);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(first);
            result = 31 * result + Objects.hashCode(second);
            return result;
        }
    }

    private static class CharCountAndPosition implements Comparable<CharCountAndPosition>{
        public Integer count;
        public Set<Integer> positions;

        public CharCountAndPosition() {
            this.count = 0;
            this.positions = new HashSet<>();
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Set<Integer> getPositions() {
            return positions;
        }

        public void setPositions(Set<Integer> positions) {
            this.positions = positions;
        }

        @Override
        public int compareTo(CharCountAndPosition o) {
            return o.getCount().compareTo(this.getCount());
        }
    }
}
