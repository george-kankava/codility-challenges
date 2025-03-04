package com.example.codilitychallenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        Map<Character, CharCount> freqMap = new HashMap<>();
        char[] pCharArray = P.toCharArray();
        for (int i = 0; i < P.length(); i++) {
            CharCount countAndPosition = freqMap.getOrDefault(pCharArray[i], new CharCount());
            countAndPosition.setCharacter(pCharArray[i]);
            countAndPosition.setCount(countAndPosition.getCount() + 1);
            countAndPosition.getCharPositions().add(i);
            freqMap.put(pCharArray[i], countAndPosition);
        }

        char[] qCharArray = Q.toCharArray();
        for (int i = 0; i < Q.length(); i++) {
            CharCount countAndPosition = freqMap.getOrDefault(qCharArray[i], new CharCount());
            countAndPosition.setCharacter(qCharArray[i]);
            countAndPosition.setCount(countAndPosition.getCount() + 1);
            countAndPosition.getCharPositions().add(i);
            freqMap.put(qCharArray[i], countAndPosition);
        }


        List<CharCount> charCounts = freqMap.values().stream().sorted(Comparator.comparingInt(CharCount::getCount).reversed()).collect(Collectors.toList());


        List<Character[]> results = new ArrayList<>();


        for (int i = 0; i < charCounts.size(); i++) {
            List<CharCount> refinedCharCounts = refineCharCountList(i, charCounts);
            Character[] result = new Character[P.length()];
            Set<Integer> filledPosition = new HashSet<>();
            for (CharCount charCount : refinedCharCounts) {
                boolean shouldBreak = false;
                for (Integer position : charCount.getCharPositions()) {
                    if (filledPosition.contains(position)) {
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) {
                    continue;
                }
                for (int j = 0; j < P.length(); j++) {
                    if ((P.charAt(j) == charCount.getCharacter() || Q.charAt(j) == charCount.getCharacter()) && !filledPosition.contains(j)) {
                        result[j] = charCount.getCharacter();
                        filledPosition.add(j);
                    }
                }

            }
            for (int j = 0; j < result.length; j++) {
                if (result[j] == null) {
                    char fromP = P.charAt(j);
                    char fromQ = Q.charAt(j);
                    int pCharCounter = 0;
                    int qCharCounter = 0;
                    for (Character character : result) {
                        if (character != null && character == fromP) {
                            pCharCounter++;
                        }
                        if (character != null && character == fromQ) {
                            qCharCounter++;
                        }
                    }
                    if (pCharCounter > qCharCounter) {
                        result[j] = fromP;
                    } else {
                        result[j] = fromQ;
                    }
                }
            }
            results.add(result);
        }
        getRefinedResults(results, freqMap, P, Q);
        List<Set<Character>> list = results.stream().map(e -> Arrays.stream(e).collect(Collectors.toSet())).collect(Collectors.toList());
        Optional<Integer> min = list.stream().map(Set::size).min(Comparator.naturalOrder());
        return min.orElse(-1);
    }

    private static void getRefinedResults(List<Character[]> results, Map<Character, CharCount> freqMap, String P, String Q) {
        for (Character[] result : results) {
            int index = 0;
            for (int i = 0; i < result.length; i++) {
                char pChar = P.charAt(index);
                char qChar = Q.charAt(index);
                CharCount pCharCount = freqMap.get(pChar);
                CharCount qCharCount = freqMap.get(qChar);
                if (pCharCount.getCount() > qCharCount.getCount()) {
                    result[index] = pChar;
                } else if (pCharCount.getCount() < qCharCount.getCount()) {
                    result[index] = qChar;
                }
                index++;
            }
        }

    }

    private static List<CharCount> refineCharCountList(int i, List<CharCount> charCounts) {
        if (i == 0) {
            return charCounts;
        }
        List<CharCount> refinedCharCount = new ArrayList<>();
        for (int j = i; j < charCounts.size(); j++) {
            refinedCharCount.add(charCounts.get(j));
        }
        for (int j = 0; j < i; j++) {
            refinedCharCount.add(charCounts.get(j));
        }
        return refinedCharCount;
    }

    // 'aaaabddddefi', 'cghjgeefhjgj'
    // "aaaacbcddd", "bbbbacdaaa"
    // bacad abada
    // hsuledusla jfudisoala
    // axxz yzwy
    // axxz wzyy
    // "adabca", "cbdcdb"
    // "dcba" "cbad"
    // "ad" "bc"
    // acb cba
    // awwa bbwx
    // "abcdef", "bcdefa"
    public static void main(String[] args) {
        System.out.println(solution("aaaabddddefi", "cghjgeefhjgj"));// ccaa
    }
    private static class CharCount {
        Character character;
        int count;
        Set<Integer> charPositions = new HashSet<>();

        public CharCount() {
        }

        public CharCount(Character character, int count) {
            this.character = character;
            this.count = count;
        }

        public Character getCharacter() {
            return character;
        }

        public void setCharacter(Character character) {
            this.character = character;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Set<Integer> getCharPositions() {
            return charPositions;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof CharCount)) return false;
            CharCount charCount = (CharCount) o;

            return count == charCount.count && Objects.equals(character, charCount.character) && Objects.equals(charPositions, charCount.charPositions);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(character);
            result = 31 * result + count;
            result = 31 * result + Objects.hashCode(charPositions);
            return result;
        }

        @Override
        public String toString() {
            return "CharCount{" +
                    "character=" + character +
                    ", count=" + count +
                    ", charPositions=" + charPositions +
                    '}';
        }
    }

}
